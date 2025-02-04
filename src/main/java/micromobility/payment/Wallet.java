<<<<<<< HEAD
package micromobility.payment;

import exceptions.NotEnoughWalletException;
import java.math.BigDecimal;

/**
 * Representa una billetera digital que gestiona un saldo y permite deducciones.
 * Esta clase garantiza que no se realicen deducciones si no hay fondos suficientes.
 */
public class Wallet {
    private BigDecimal balance;

    /**
     * Constructor de la billetera.
     *
     * @param initialBalance El saldo inicial de la billetera. No puede ser null ni negativo.
     * @throws IllegalArgumentException Si el saldo inicial es null o negativo.
     */
    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative.");
        }
        this.balance = initialBalance;
    }

    /**
     * Obtiene el saldo actual de la billetera.
     *
     * @return El saldo actual de la billetera.
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void addBalance(BigDecimal balance){
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount format invalid.");
        }
        this.balance = this.balance.add(balance);
    }

    /**
     * Realiza una deducción del saldo de la billetera.
     * Si el monto a deducir es mayor que el saldo disponible, se lanza una excepción.
     *
     * @param amount El monto a deducir de la billetera. Debe ser mayor que cero.
     * @throws IllegalArgumentException Si el monto es null o menor o igual a cero.
     * @throws NotEnoughWalletException Si no hay suficiente saldo en la billetera.
     */
    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughWalletException();
        }
        balance = balance.subtract(amount);
    }

    /**
     * Representación en cadena de la billetera.
     *
     * @return Una cadena con la información del saldo de la billetera.
     */
    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance.stripTrailingZeros().toPlainString() +
                '}';
    }
}
=======
package micromobility.payment;

import exceptions.NotEnoughWalletException;
import java.math.BigDecimal;

/**
 * Representa una billetera digital que gestiona un saldo y permite deducciones.
 * Esta clase garantiza que no se realicen deducciones si no hay fondos suficientes.
 */
public class Wallet {
    private BigDecimal balance;

    /**
     * Constructor de la billetera.
     *
     * @param initialBalance El saldo inicial de la billetera. No puede ser null ni negativo.
     * @throws IllegalArgumentException Si el saldo inicial es null o negativo.
     */
    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative.");
        }
        this.balance = initialBalance;
    }

    /**
     * Obtiene el saldo actual de la billetera.
     *
     * @return El saldo actual de la billetera.
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void addBalance(BigDecimal balance){
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount format invalid.");
        }
        this.balance = this.balance.add(balance);
    }

    /**
     * Realiza una deducción del saldo de la billetera.
     * Si el monto a deducir es mayor que el saldo disponible, se lanza una excepción.
     *
     * @param amount El monto a deducir de la billetera. Debe ser mayor que cero.
     * @throws IllegalArgumentException Si el monto es null o menor o igual a cero.
     * @throws NotEnoughWalletException Si no hay suficiente saldo en la billetera.
     */
    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughWalletException();
        }
        balance = balance.subtract(amount);
    }

    /**
     * Representación en cadena de la billetera.
     *
     * @return Una cadena con la información del saldo de la billetera.
     */
    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance.stripTrailingZeros().toPlainString() +
                '}';
    }
}
>>>>>>> origin/micromobilityV2
