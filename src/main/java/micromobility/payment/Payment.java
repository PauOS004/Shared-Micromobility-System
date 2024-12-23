package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;
import java.math.BigDecimal;

/**
 * Clase abstracta que representa un pago.
 * Las subclases deben implementar el método {@link #processPayment()}.
 */
public abstract class Payment {
    private final UserAccount user;
    private final JourneyService journey;
    private final BigDecimal amount;

    /**
     * Constructor de Payment.
     *
     * @param user El usuario que realiza el pago.
     * @param journey El servicio de trayecto asociado al pago.
     * @param amount El monto del pago. Debe ser mayor a 0.
     * @throws IllegalArgumentException Si el usuario, el servicio o el monto son inválidos.
     */
    public Payment(UserAccount user, JourneyService journey, BigDecimal amount) {
        if (user == null || journey == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid arguments: user, journey, and amount must be non-null, and amount must be greater than 0.");
        }
        this.user = user;
        this.journey = journey;
        this.amount = amount;
    }

    public UserAccount getUser() {
        return user;
    }

    public JourneyService getJourney() {
        return journey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Método abstracto para procesar el pago. Las subclases deben implementarlo.
     */
    public abstract void processPayment();

    @Override
    public String toString() {
        return "Payment{" +
                "user=" + user.getUsername() +
                ", journeyStartDate=" + (journey.getInitDate() != null ? journey.getInitDate() : "null") +
                ", amount=" + amount.stripTrailingZeros().toPlainString() +
                '}';
    }
}