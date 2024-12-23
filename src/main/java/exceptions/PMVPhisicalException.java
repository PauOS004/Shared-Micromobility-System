package exceptions;

/**
 * Indicates an error whenever the car cannot be started due to technical issues.
 */


public class PMVPhisicalException extends Exception {
    public PMVPhisicalException(String message) {
        super(message);
    }

    public PMVPhisicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PMVPhisicalException(Throwable cause) {
        super("There was a technical issue that prevented the vehicle from starting", cause);
    }

    public PMVPhisicalException() {
        super("There was a technical issue that prevented the vehicle from starting");
    }
}
