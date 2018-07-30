package cd.blog.humbird.libra.common.util;

import com.google.gson.Gson;

/**
 * @author david
 * @since created by on 18/7/25 23:30
 */
public class JsonUtils {

    private static final Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
