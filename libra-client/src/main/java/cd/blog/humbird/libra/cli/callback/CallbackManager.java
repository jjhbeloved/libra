package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.common.zk.ZKCli;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by david on 2018/7/27.
 */
public class CallbackManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackManager.class);

    private List<Callback> callbacks;
    private static volatile CallbackManager callbackManager;

    private CallbackManager(ZKCli zkCli) {
        init(zkCli);
    }

    public static CallbackManager instance(ZKCli zkCli) {
        if (callbackManager == null) {
            synchronized (CallbackManager.class) {
                if (callbackManager == null) {
                    callbackManager = new CallbackManager(zkCli);
                }
            }
        }
        return callbackManager;
    }

    /**
     * 获取指定的类对象
     *
     * @param clazz 类
     * @param <T>   具体类
     * @return 如果不存在返回null, 存在返回对应的对象
     */
    public <T> T getClassLoader(Class<T> clazz) {
        for (Callback callback : callbacks) {
            if (clazz.isAssignableFrom(callback.getClass())) {
                return (T) callback;
            }
        }
        return null;
    }

    private void init(ZKCli zkCli) {
        callbacks = Lists.newLinkedList();
        callbacks.add(new ClientConfigVersionCallback(zkCli));
        callbacks.add(new LibraClientStatusCallback(zkCli));
    }
}
