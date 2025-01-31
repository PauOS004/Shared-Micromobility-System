package test;

import data.StationID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StationIDTest {

    //Test Valid ID
    @Test
    public void testValidID(){
        StationID stationID = new StationID("A0B1C2");
        assertEquals("A0B1C2", stationID.getId());
    }

    //Test Null ID
    @Test
    public void testInvalidIDIsNull(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationID(null);
        });
        assertEquals("The Station ID provided is invalid", exception.getMessage());
    }

    //Test lower ID
    @Test
    public void testInvalidIDNonUpper(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationID("a0b1c2");
        });
        assertEquals("The Station ID provided is invalid", exception.getMessage());
    }

    //Test short ID
    @Test
    public void testInvalidIDTooShort(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationID("A0");
        });
        assertEquals("The Station ID provided is invalid", exception.getMessage());
    }

    //Test long ID
    @Test
    public void testInvalidIDTooLong(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationID("A0B1C2D3E4F5");
        });
        assertEquals("The Station ID provided is invalid", exception.getMessage());
    }

    //Test non alphanumeric ID
    @Test
    public void testInvalidIDNonAlphanumeric(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationID("A0B1€#");
        });
        assertEquals("The Station ID provided is invalid", exception.getMessage());
    }

    //Test HashCode
    @Test
    public void testHashCode(){
        StationID st1 = new StationID("A0B1C2");
        StationID st2 = new StationID("A0B1C2");
        assertEquals(st1.hashCode(), st2.hashCode());
    }

    //Test toString
    @Test
    public void testToString(){
        StationID st1 = new StationID("A0B1C2");
        assertEquals("StationID{id='A0B1C2'}", st1.toString());
    }
}
