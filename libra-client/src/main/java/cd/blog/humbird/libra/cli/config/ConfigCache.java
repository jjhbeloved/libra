package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.config.zk.ZkConfigLoader;
import cd.blog.humbird.libra.cli.model.ConfigEvent;
import cd.blog.humbird.libra.cli.model.ConfigValue;
import cd.blog.humbird.libra.common.constant.Parameter;
import cd.blog.humbird.libra.common.util.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public class ConfigCache implements ConfigListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigCache.class);
	private static volatile ConfigCache configCache;
	private String server;
	private ConfigLoaderManager configLoaderManager;
	private ZkConfigLoader zkConfigLoader;
	private static ConcurrentMap<String, ConfigValue> cachedConfig = new ConcurrentHashMap<>();
	/**
	 * 本地的配置信息对应的value可以是${xxx}(有可能需要读取配置中心的值)
	 */
	private Properties localProps;

	private ConfigCache(String server) {
		this.server = server;
		if (StringUtils.isBlank(server)) {
			throw new NullPointerException("zk server is null.");
		}
		System.setProperty(ConfigLoader.KEY_ZOOKEEPER_ADDRESS, server);
		this.configLoaderManager = ConfigLoaderManager.instance();
		this.configLoaderManager.addConfigListener(this);
		this.zkConfigLoader = this.configLoaderManager.getClassLoader(ZkConfigLoader.class);
	}

	public static ConfigCache instance() {
		return instance(ClientEnv.getZkserver());
	}

	public static ConfigCache instance(String server) {
		if (configCache == null) {
			synchronized (ConfigCache.class) {
				if (configCache == null) {
					configCache = new ConfigCache(server);
				}
			}
		}
		return configCache;
	}

	@Override
	public void refresh(ConfigEvent event) {
		if (event.getKey() == null) {
			return;
		}
		String key = event.getKey();
		String version = event.getVersion();
		String value = event.getValue();
		if (event.isDeleted()) {
			ConfigValue existsConfigValue = cachedConfig.remove(key);
			LOGGER.info("------------ config deleted, key:{},previous value:{},ver:{}", key, existsConfigValue.getVal(), existsConfigValue.getVersion());
			return;
		}
		ConfigValue existsConfigValue = cachedConfig.get(key);
		if (existsConfigValue == null) {
			cachedConfig.put(key, new ConfigValue(value, version));
			LOGGER.info("++++++++++++ config changed, key:{},val:{},ver:{}", key, value, version);
			return;
		}
		if (version.compareTo(existsConfigValue.getVersion()) > 0) {
			existsConfigValue.setVersion(version);
			existsConfigValue.setVal(value);
			LOGGER.info("++++++++++++ config ver changed, key:{},val:{},ver:{}", key, value, version);
		}
	}

	public Properties getLocalProps() {
		return localProps;
	}

	public void setLocalProps(Properties localProps) {
		this.localProps = localProps;
	}

	public String get(String key) {
		ConfigValue configValue = getValue(key);
		if (configValue == null) {
			return null;
		}
		return configValue.getVal();
	}

	/**
	 * 获取数据后, 更新客户端配置版本信息到服务器进行统一管理
	 * 将key保存在app对应的目录下
	 * 将app对应的key更新
	 *
	 * @param key         键
	 * @param configValue 值
	 */
	public void callClientConfigVersion(String key, ConfigValue configValue) {
		if (configValue == null) {
			zkConfigLoader.callClientConfigVersion(new ConfigEvent(key, null, null));
		} else {
			zkConfigLoader.callClientConfigVersion(new ConfigEvent(key, configValue.getVal(), configValue.getVersion()));
		}
	}

	/**
	 * 获取val, 如果存在本地配置, 优先读取本地配置
	 * 如果本地配置存在${xxx}获取value的xxx去配置中心获取具体的值
	 *
	 * @param key 键
	 * @return 值
	 */
	public ConfigValue getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		if (localProps != null) {
			String val = localProps.getProperty(key);
			if (val != null) {
				if (KeyUtils.isReferenceValue(val)) {
					key = KeyUtils.getReferencedKey(val);
				} else {
					return new ConfigValue(val, Parameter.LOCAL_VERSION);
				}
			}
		}
		return getValueFromCache(key);
	}


	/**
	 * 从缓存获取值(如果不实用缓存则不读缓存)
	 *
	 * @param key 键
	 * @return 值
	 */
	private ConfigValue getValueFromCache(String key) {
		ConfigValue configValue;
		if (ClientEnv.isReadCache()) {
			configValue = cachedConfig.get(key);
			if (configValue != null) {
				return configValue;
			}
			configValue = configLoaderManager.get(key);
			cachedConfig.put(key, configValue);
		} else {
			configValue = configLoaderManager.get(key);
		}
		return configValue;
	}
}
