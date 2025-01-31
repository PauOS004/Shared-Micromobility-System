package test;

import data.VehicleID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    //Test valid ID
    @Test
    void testValidVehicleID() {
        VehicleID vehicleID = new VehicleID("VEH-12345");
        assertEquals("VEH-12345", vehicleID.getId());
    }

    //Test invalid formatted
    @Test
    void testInvalidVehicleIDFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("12345-VEH");
        });
        assertEquals("The Vehicle ID provided is invalid", exception.getMessage());
    }

    //Test null ID
    @Test
    void testNullVehicleID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID(null);
        });
        assertEquals("The Vehicle ID provided is invalid", exception.getMessage());
    }

    //Test invalid ID by its length
    @Test
    void testInvalidVehicleIDLength() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("VEH-12");
        });
        assertEquals("The Vehicle ID provided is invalid", exception.getMessage());
    }

    //Test hashcodes
    @Test
    void testEqualsAndHashCode() {
        VehicleID vehicleID1 = new VehicleID("VEH-12345");
        VehicleID vehicleID2 = new VehicleID("VEH-12345");
        VehicleID vehicleID3 = new VehicleID("VEH-56789");

        assertEquals(vehicleID1, vehicleID2);
        assertNotEquals(vehicleID1, vehicleID3);
        assertEquals(vehicleID1.hashCode(), vehicleID2.hashCode());
        assertNotEquals(vehicleID1.hashCode(), vehicleID3.hashCode());
    }

    //Test to string.
    @Test
    void testToString() {
        VehicleID vehicleID = new VehicleID("VEH-12345");
        assertEquals("VehicleID{id='VEH-12345'}", vehicleID.toString());
    }
}