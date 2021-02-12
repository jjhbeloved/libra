package cd.blog.humbird.libra.exception;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
