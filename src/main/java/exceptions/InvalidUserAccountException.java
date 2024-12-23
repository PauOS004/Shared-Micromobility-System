package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class InvalidUserAccountException extends IllegalArgumentException {
    public InvalidUserAccountException(String message) {
        super(message);
    }

    public InvalidUserAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserAccountException() {
        super("The User Account provided has an invalid format");
    }

    public InvalidUserAccountException(Throwable cause) {
        super("The User Account provided has an invalid format", cause);
    }
}