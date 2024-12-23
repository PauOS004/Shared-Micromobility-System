package exceptions;

/**
 * Exception thrown when invalid arguments are provided for pairing.
 */
public class InvalidPairingArgsException extends Exception {
    public InvalidPairingArgsException() {
        super("Pairing arguments are invalid");
    }

    public InvalidPairingArgsException(String message) {
        super(message);
    }

    public InvalidPairingArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPairingArgsException(Throwable cause) {
        super("Pairing arguments are invalid", cause);
    }

    public static InvalidPairingArgsException invalidStation() {
        return new InvalidPairingArgsException("The station ID provided does not exist or does not match the vehicle's location.");
    }

    public static InvalidPairingArgsException invalidGeographicPoint() {
        return new InvalidPairingArgsException("The geographic location provided is invalid.");
    }

    public static InvalidPairingArgsException invalidVehicleStationRelation() {
        return new InvalidPairingArgsException("The vehicle is not located in the specified station.");
    }
}
