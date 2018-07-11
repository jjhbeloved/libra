package cd.blog.humbird.libra.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by david on 2018/7/11.
 */
public class EncodeUtil {

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
