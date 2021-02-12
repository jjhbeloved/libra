package cd.blog.humbird.libra.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author david
 * @since created by on 18/7/25 23:15
 */
public class SystemUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

    private static String pid;
    private static String host;

    public static String getPID() {
        if (StringUtils.isNoneBlank(pid)) {
            return pid;
        }
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        pid = runtimeMXBean.getName().split("@")[0];
        return pid;
    }

    /**
     * 获取当前启动程序所在的主机IPV4地址信息
     *
     * @return IPv4 host
     */
    public static String getIPv4Host() {
        if (StringUtils.isNoneBlank(host)) {
            return host;
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface anInterface = interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        String h = inetAddress.getHostAddress();
                        if (!"127.0.0.1".equals(host)) {
                            host = h;
                            return host;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("get local host error!exception:{}", e.getMessage());
        }
        return host;
    }
}
