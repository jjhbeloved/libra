package cd.blog.humbird.libra.exception;

/**
 * Created by david on 2018/7/18.
 */
public class IncorrectPasswdException extends RuntimeException {
    public IncorrectPasswdException(String message) {
        super(message);
    }

    public IncorrectPasswdException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswdException(Throwable cause) {
        super(cause);
    }
}
