package exceptions;

/**
 * Exception that indicates an error when pairing information for the
 * server isn't found.
 */

public class PairingNotFoundException extends Exception {
    public PairingNotFoundException(String message) {
        super(message);
    }

    public PairingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PairingNotFoundException(Throwable cause) {
        super("Pairing information not found.", cause);
    }

    public PairingNotFoundException() {
        super("Pairing information not found.");
    }

}
