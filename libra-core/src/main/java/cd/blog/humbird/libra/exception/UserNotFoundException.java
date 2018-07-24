package cd.blog.humbird.libra.exception;

/**
 * Created by david on 2018/7/18.
 */
public class UserNotFoundException extends UserException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
