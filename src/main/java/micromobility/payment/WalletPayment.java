package micromobility.payment;

import data.UserAccount;
import exceptions.NotEnoughWalletException;
import micromobility.JourneyService;

import java.math.BigDecimal;

/**
 * Representa un pago realizado a través de una billetera digital.
 * Esta clase procesa el pago deduciendo el monto correspondiente de la billetera.
 */
public class WalletPayment extends Payment {
    private final Wallet wallet;

    /**
     * Constructor de WalletPayment.
     *
     * @param user El usuario que realiza el pago.
     * @param journey El servicio de trayecto asociado al pago.
     * @param amount El monto del pago. Debe ser mayor a 0.
     * @param wallet La billetera desde la que se realiza el pago. No puede ser null.
     * @throws IllegalArgumentException Si el usuario, el trayecto, el monto o la billetera son inválidos.
     */
    public WalletPayment(UserAccount user, JourneyService journey, BigDecimal amount, Wallet wallet) {
        super(user, journey, amount);
        if (wallet == null || wallet.getBalance() == null) {
            throw new IllegalArgumentException("Wallet or wallet balance cannot be null.");
        }
        this.wallet = wallet;
    }

    public Wallet getWallet(){
        return this.wallet;
    }

    /**
     * Procesa el pago deduciendo el monto de la billetera asociada.
     * Si el saldo de la billetera es insuficiente, se lanza una excepción.
     *
     * @throws NotEnoughWalletException Si no hay suficiente saldo en la billetera.
     */
    @Override
    public void executePayment() throws NotEnoughWalletException {
        wallet.deduct(getAmount());
        System.out.println("Payment executed successfully for user: " + getUser());
    }

    /**
     * Representación en cadena del pago realizado con billetera.
     *
     * @return Una cadena con la información del pago realizado.
     */
    @Override
    public String toString() {
        return "WalletPayment{" +
                "wallet=" + wallet +
                ", amount=" + getAmount() +
                '}';
    }
}