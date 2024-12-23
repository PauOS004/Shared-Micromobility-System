package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class InvalidGeoCoordinatesException extends IllegalArgumentException {
    public InvalidGeoCoordinatesException (String message) {
        super(message);
    }

    public InvalidGeoCoordinatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGeoCoordinatesException() {
        super("The Coordinates provided are invalid");
    }

    public InvalidGeoCoordinatesException(Throwable cause) {
        super("The Coordinates provided are invalid", cause);
    }
}