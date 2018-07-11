package cd.blog.humbird.libra.exception;

/**
 * @author david
 * @since created by on 18/7/12 03:09
 */
public class RegisterException extends RuntimeException {

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }
}
