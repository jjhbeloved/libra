package cd.blog.humbird.libra.exception;

/**
 * Created by david on 2018/7/18.
 */
public class UserLockedException extends UserException {

    public UserLockedException(String message) {
        super(message);
    }

    public UserLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLockedException(Throwable cause) {
        super(cause);
    }
}
