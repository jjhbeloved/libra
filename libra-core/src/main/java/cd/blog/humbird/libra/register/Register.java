package cd.blog.humbird.libra.register;

import java.util.List;

/**
 * Created by david on 2018/7/11.
 */
public interface Register {

    void registerContextValue(String key, String value);

    void registerAndPushContextValue(String key, String value);

    void registerDefaultValue(String key, String value);

    void registerAndPushDefaultValue(String key, String value);

    void registerGroupValue(String key, String group, String value);

    void registerAndPushGroupValue(String key, String group, String value);

    void unregister(String key);

    void unregister(String key, String group);

    String get(String key);

    String get(String key, String group);

    String getAddresses();

    String getRemoteDataVersion(String key);

    String getValueWithNoBasePath(String path) throws Exception;

    List<String> getChildrenValueWithNoBasePath(String path) throws Exception;

    List<String> getChildrenWithNoBasePath(String path) throws Exception;

    void destroy();
}
