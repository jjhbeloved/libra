package cd.blog.humbird.libra.common.util;

import cd.blog.humbird.libra.common.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by david on 2018/7/26.
 */
public class KeyUtil {

    public static boolean isReferenceValue(String value) {
        if (value == null) {
            return false;
        }
        return value.startsWith(Constants.DEFAULT_PLACEHOLDER_PREFIX) &&
                value.endsWith(Constants.DEFAULT_PLACEHOLDER_SUFFIX);
    }

    public static String getReferencedKey(String value) {
        return StringUtils.substring(value, 2, value.length() - 1);
    }
}
