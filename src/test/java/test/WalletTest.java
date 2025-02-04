package test;

import exceptions.NotEnoughWalletException;
import micromobility.payment.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WalletTest {

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(new BigDecimal("100.00"));
    }

    //Test if the initial balance is correctly assigned
    @Test
    void testInitialBalance() {
        assertEquals(new BigDecimal("100.00"), wallet.getBalance());
    }

    //Test adding balance successful case
    @Test
    void testAddBalance() {
        wallet.addBalance(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), wallet.getBalance());
    }

    //Test adding negative balance throws IllegalArgumentException
    @Test
    void testAddBalanceNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> wallet.addBalance(new BigDecimal("-100.00")));
    }

    //Test deducting balance successful test
    @Test
    void testDeductBalance() throws NotEnoughWalletException {
        wallet.deduct(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("50.00"), wallet.getBalance());
    }

    //Test if deducting too much balance throws NotEnoughWalletException
    @Test
    void testDeductInsufficientFunds() {
        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(new BigDecimal("500.00")));
    }

    //Test if deduct a negative amount throws IllegalArgumentException
    @Test
    void testDeductNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("-10.00")));
    }

    //Test if null initial balance throws illegalArgumentException
    @Test
    void testNullInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(null));
    }

    //Test if negative balance throws illegalArgumentException
    @Test
    void testNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(new BigDecimal("-10.00")));
    }
}
