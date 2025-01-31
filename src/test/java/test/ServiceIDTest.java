package test;

import data.ServiceID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceIDTest {

    //Test valid ID
    @Test
    void testValidServiceIDTest() {
        ServiceID serviceID = new ServiceID("SER12345");
        assertEquals("SER12345", serviceID.getId());
    }

    //Test invalid short ID
    @Test
    void testInvalidServiceIDTooShort() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("SER"));
    }

    //Test invalid long ID
    @Test
    void testInvalidServiceIDTooLong() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("SERVICEIDLONGERWORD"));
    }

    //Test invalid lowercase ID
    @Test
    void testInvalidServiceIDLowercase() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("SeRvIcEID"));
    }

    //Test invalid non aplha-numeric characters ID
    @Test
    void testInvalidServiceIDSpecialChars() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("S3R>1(€ID"));
    }

    //Test null ID
    @Test
    void testInvalidServiceIDIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID(null));
    }

    //Test equal IDs
    @Test
    void testValidServiceIDEquals() {
        ServiceID serviceID1 = new ServiceID("SERV1234");
        ServiceID serviceID2 = new ServiceID("SERV1234");
        assertEquals(serviceID1, serviceID2);
    }

    //Test not equal IDs
    @Test
    void testValidServiceIDNotEquals() {
        ServiceID serviceID1 = new ServiceID("SERV1234");
        ServiceID serviceID2 = new ServiceID("SERV1235");
        assertNotEquals(serviceID1, serviceID2);
    }

    //Test hashcode
    @Test
    void testHashCode() {
        ServiceID serviceID1 = new ServiceID("SERV1234");
        ServiceID serviceID2 = new ServiceID("SERV1234");
        assertEquals(serviceID1.hashCode(), serviceID2.hashCode());
    }

    //Testing to string
    @Test
    void testToString() {
        ServiceID serviceID = new ServiceID("ABCDEFGH");
        assertEquals("ServiceID{id='ABCDEFGH'}", serviceID.toString());
    }
}
