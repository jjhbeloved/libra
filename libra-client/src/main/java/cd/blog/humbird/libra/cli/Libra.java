package cd.blog.humbird.libra.cli;

import cd.blog.humbird.libra.cli.config.Config;

/**
 * Created by david on 2018/7/26.
 */
public class Libra {

    private static Config config = Config.instance();

    static {

    }

    public static String get(String key) {
        return config.get(key);
    }

    public static String get(String key, String defaultVal) {
        String val = get(key);
        return val == null ? defaultVal : val;
    }
}
