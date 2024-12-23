package exceptions;

/**
 * Exception thrown when the requested vehicle is not available for pairing.
 */
public class PMVNotAvailException extends Exception {
    public PMVNotAvailException(String message) {
        super(message);
    }

    public PMVNotAvailException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

    public PMVNotAvailException(Throwable cause) {
        super("The requested Personal Mobility Vehicle (PMV) is not available. It may already be paired with another user.", cause);
    }

    public PMVNotAvailException() {
        super("The requested Personal Mobility Vehicle (PMV) is not available. It may already be paired with another user.");
    }
}

