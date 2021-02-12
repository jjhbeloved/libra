package cd.blog.humbird.libra.common.util;

import java.io.UnsupportedEncodingException;

/**
 * @author david
 * @since created by on 2018/7/11 23:13
 */
public class EncodeUtils {

    public static final String CHARSET = "UTF-8";

    public static byte[] long2byte(long v) throws UnsupportedEncodingException {
        String str = String.valueOf(v);
        return str.getBytes(CHARSET);
    }

    public static long byte2long(byte[] v) throws UnsupportedEncodingException {
        String str = new String(v, CHARSET);
        return Long.valueOf(str);
    }
}
