package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class InvalidVehicleIDException extends IllegalArgumentException {
    public InvalidVehicleIDException(String message) {
        super(message);
    }

    public InvalidVehicleIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVehicleIDException() {
        super("The Vehicle ID provided is invalid");
    }

    public InvalidVehicleIDException(Throwable cause) {
        super("The Vehicle ID provided is invalid", cause);
    }
}