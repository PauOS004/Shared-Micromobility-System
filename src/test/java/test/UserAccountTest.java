package test;

import data.UserAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserAccountTest {

    //Testing a valid UserAccount
    @Test
    public void testValidUseraccount(){
        UserAccount superuser = new UserAccount("super_user12");
        assertEquals("super_user12", superuser.getUsername());
    }

    //Testing a nullu UserAccount
    @Test
    public void testInvalidUserAccount_NULL(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserAccount(null);
        });
        assertEquals("The User Account provided has an invalid format", exception.getMessage());
    }

    //Testing short useraccount
    @Test
    public void testInvalidUserAccount_tooShort(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserAccount("pl");
        });
        assertEquals("The User Account provided has an invalid format", exception.getMessage());
    }

    //Testing a too long UserAccount
    @Test
    public void testInvalidUserAccount_tooLong(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserAccount("super_user12butisverylongusernameaccountsoisinvalid");
        });
        assertEquals("The User Account provided has an invalid format", exception.getMessage());
    }

    //Testing an invalid characters UserAccount
    @Test
    public void testInvalidUserAccount_invalidCharacters(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserAccount("super_¢#@ser12");
        });
        assertEquals("The User Account provided has an invalid format", exception.getMessage());
    }

    //Testing hashCode
    @Test
    public void testHashCode(){
        UserAccount superuser1 = new UserAccount("super_user12");
        UserAccount superuser2 = new UserAccount("super_user12");
        assertEquals(superuser1.hashCode(), superuser2.hashCode());
    }

    //Testing to string
    @Test
    public void testToString(){
        UserAccount superuser = new UserAccount("super_user12");
        assertEquals("UserAccount{username='super_user12'}", superuser.toString());
    }


}
