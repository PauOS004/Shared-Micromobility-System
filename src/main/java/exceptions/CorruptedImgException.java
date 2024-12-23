package exceptions;

/**
 * Exception indicating that the captured image is not valid.
 */


public class CorruptedImgException extends RuntimeException {
  public CorruptedImgException(String message) {
    super(message);
  }

  public CorruptedImgException(String message, Throwable cause) {
    super(message, cause);
  }

  public CorruptedImgException() {
    super("The image is invalid");
  }

  public CorruptedImgException(Throwable cause) {
    super("The image is invalid", cause);
  }
}
