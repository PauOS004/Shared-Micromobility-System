package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class InvalidStationIDException extends IllegalArgumentException {
    public InvalidStationIDException(String message) {
        super(message);
    }

    public InvalidStationIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStationIDException() {
        super("The Station ID provided is invalid");
    }

    public InvalidStationIDException(Throwable cause) {
        super("The Station ID provided is invalid", cause);
    }
}