package test;

import data.UserAccount;
import exceptions.NotEnoughWalletException;
import micromobility.JourneyService;
import micromobility.payment.Wallet;
import micromobility.payment.WalletPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WalletPaymentTest {
    private WalletPayment walletPayment;
    private JourneyService journeyService;
    private UserAccount userAccount;
    private Wallet wallet;
    private BigDecimal amount;

    @BeforeEach
    public void setUp() {
        journeyService = new JourneyService();
        userAccount = new UserAccount("romera");
        wallet = new Wallet(BigDecimal.valueOf(300.0));
        amount = BigDecimal.valueOf(200.0);
        walletPayment = new WalletPayment(userAccount, journeyService, amount, wallet);
    }

    //Test valid wallet constructor
    @Test
    public void testValidConstructor() {
        assertNotNull(walletPayment);
        assertEquals(wallet, walletPayment.getWallet());
        assertEquals(amount, walletPayment.getAmount());
    }

    //Test invalid wallet constructor
    @Test
    public void testConstructorInvalidWallet() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new WalletPayment(userAccount, journeyService, amount, null));
        assertEquals("Wallet or wallet balance cannot be null.", exception.getMessage());
    }

    //Test a successful payment
    @Test
    public void testExecutePaymentSuccess() throws NotEnoughWalletException {
        walletPayment.executePayment();
        assertEquals(BigDecimal.valueOf(100.0), wallet.getBalance());
    }

    //Test payment throws NotEnoughWalletException
    @Test
    public void testExecutePaymentNotEnoughFunds() {
        Wallet smallWallet = new Wallet(BigDecimal.valueOf(10.0));
        WalletPayment smallWalletPayment = new WalletPayment(userAccount, journeyService, amount, smallWallet);

        Exception exception = assertThrows(NotEnoughWalletException.class, smallWalletPayment::executePayment);
        assertEquals("Wallet doesn't have enough money to proceed.", exception.getMessage());
    }
}
