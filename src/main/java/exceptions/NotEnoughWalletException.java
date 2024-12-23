package exceptions;

/**
 * Indicates there is not enough money in the app wallet.
 */


public class NotEnoughWalletException extends RuntimeException {
    public NotEnoughWalletException(String message) {
        super(message);
    }

    public NotEnoughWalletException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughWalletException(Throwable cause) {
        super("Wallet doesn't have enough money to proceed.", cause);
    }

    public NotEnoughWalletException() {
        super("Wallet doesn't have enough money to proceed.");
    }

}
