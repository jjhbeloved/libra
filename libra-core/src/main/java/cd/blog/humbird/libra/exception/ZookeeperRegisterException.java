package cd.blog.humbird.libra.exception;

/**
 * Created by david on 2018/7/11.
 */
public class ZookeeperRegisterException extends RegisterException {

    public ZookeeperRegisterException(String message) {
        super(message);
    }

    public ZookeeperRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZookeeperRegisterException(Throwable cause) {
        super(cause);
    }
}
