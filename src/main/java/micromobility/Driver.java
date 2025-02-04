package micromobility;

import data.UserAccount;
<<<<<<< HEAD
=======

>>>>>>> origin/micromobilityV2
import java.util.List;

/**
 * Represents a driver in the micromobility system.
 */
public class Driver {

    private String email;
    private String name;
    private List<Character> paymMethods;
    private String telNumb;
    private String bankAcc;
    private UserAccount userAcc;

    /**
     * Constructs a new Driver object.
     *
     * @param name        The driver's name.
     * @param email       The driver's email address.
     * @param telNumb     The driver's telephone number.
     * @param paymMethods The list of payment methods.
     * @param bankAcc     The driver's bank account information.
     * @param userAcc     The user's account associated with the driver.
     */
    public Driver(String name, String email, String telNumb, List<Character> paymMethods, String bankAcc, UserAccount userAcc) {
        this.email = valEmail(email);
        this.name = name;
        this.paymMethods = paymMethods;
        this.telNumb = valTelNumber(telNumb);
        this.bankAcc = valBankAcc(bankAcc);
        this.userAcc = userAcc;
    }

    /**
     * Adds a new payment method if it does not already exist.
     *
     * @param method The payment method to add.
     * @throws IllegalArgumentException If the payment method already exists.
     */
    public void addNewPaymentMethod(char method) {
        if (!paymMethods.contains(method)) {
            paymMethods.add(method);
        } else {
            throw new IllegalArgumentException("This payment method already exists!");
        }
    }

    /**
     * Removes a payment method if it exists.
     *
     * @param method The payment method to remove.
     * @throws IllegalArgumentException If the payment method is not found.
     */
    public void remPaymentMethod(char method) {
        if (paymMethods.contains(method)) {
            paymMethods.remove((Character) method);
        } else {
            throw new IllegalArgumentException("Payment method not found!");
        }
    }

    /**
     * Validates an email address.
     *
     * @param email The email address to validate.
     * @return The validated email address.
     * @throws IllegalArgumentException If the email format is invalid.
     */
    private String valEmail(String email) {
        if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format. example@gmail.com");
        }
        return email;
    }

    /**
     * Validates a telephone number.
     *
     * @param telNumb The telephone number to validate.
     * @return The validated telephone number.
     * @throws IllegalArgumentException If the telephone number format is invalid.
     */
    private String valTelNumber(String telNumb) {
        if (telNumb == null || !telNumb.matches("\\d{10,15}")) {
            throw new IllegalArgumentException("Invalid user phone number.");
        }
        return telNumb;
    }

    /**
     * Validates a bank account format.
     *
     * @param bankAcc The bank account to validate.
     * @return The validated bank account.
     * @throws IllegalArgumentException If the bank account format is invalid.
     */
    private String valBankAcc(String bankAcc) {
        if (bankAcc == null || !bankAcc.matches("\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Invalid user bank account format. 1234");
        }
        return bankAcc;
    }

    // Setters

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = valEmail(email); }
    public void setTelNumb(String telNumb) { this.telNumb = valTelNumber(telNumb); }
    public void setPaymMethods(List<Character> paymMethods) { this.paymMethods = paymMethods; }
    public void setBankAcc(String bankAcc) { this.bankAcc = valBankAcc(bankAcc); }
    public void setUserAcc(UserAccount userAcc) { this.userAcc = userAcc; }

    // Getters

    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getTelNumb() { return this.telNumb; }
    public List<Character> getPaymMethods() { return this.paymMethods; }
    public String getBankAcc() { return this.bankAcc; }
    public UserAccount getUserAccount() { return this.userAcc; }
}