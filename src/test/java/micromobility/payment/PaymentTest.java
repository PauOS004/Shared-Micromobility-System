package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    static class TestPayment extends Payment {
        public TestPayment(UserAccount user, JourneyService journey, BigDecimal amount) {
            super(user, journey, amount);
        }

        @Override
        public void processPayment() {
            // Implementación simulada para pruebas
        }
    }

    @Test
    void testPaymentInitialization() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(LocalDateTime.now());
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertEquals(user, payment.getUser());
        assertEquals(journey, payment.getJourney());
        assertEquals(BigDecimal.valueOf(100), payment.getAmount());
    }

    @Test
    void testPaymentInitializationFailsWithInvalidArguments() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> new TestPayment(null, journey, BigDecimal.valueOf(100)));
        assertThrows(IllegalArgumentException.class, () -> new TestPayment(user, null, BigDecimal.valueOf(100)));
        assertThrows(IllegalArgumentException.class, () -> new TestPayment(user, journey, BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new TestPayment(user, journey, null));
    }

    @Test
    void testToStringWithDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(now);
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        // Construir el formato esperado solo con la fecha
        String expectedDateOnly = "Payment{user=user123, journeyStartDate=" + now.toLocalDate() + ", amount=100}";

        // Construir el formato esperado con fecha y hora
        String expectedDateTime = "Payment{user=user123, journeyStartDate=" + now.toString() + ", amount=100}";

        // Validar que cualquiera de los dos formatos es aceptado
        String actual = payment.toString();
        assertTrue(actual.equals(expectedDateOnly) || actual.equals(expectedDateTime),
                "Expected either: " + expectedDateOnly + " or: " + expectedDateTime + " but got: " + actual);
    }


    @Test
    void testProcessPayment() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(LocalDateTime.now());
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertDoesNotThrow(payment::processPayment);
    }
}