package exceptions;

/**
 * Indicates an error whenever the procedural order is altered
 */


public class ProceduralException extends RuntimeException {
    public ProceduralException(String message) {
        super(message);
    }

    public ProceduralException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProceduralException(Throwable cause) {
        super("There has been a procedural error", cause);
    }

    public ProceduralException() {
        super("There has been a procedural error");
    }
}
