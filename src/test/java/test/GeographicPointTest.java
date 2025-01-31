package test;

import data.GeographicPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeographicPointTest {

    //Testing valid GeographicPoint
    @Test
    public void testvalidGeographicPoint(){
        GeographicPoint gp = new GeographicPoint(83.0f, -111.0f);
        assertEquals(83.0f, gp.getLatitude());
        assertEquals(-111.0f, gp.getLongitude());
    }

    //Testing equals
    @Test
    public void testEquals(){
        GeographicPoint gp = new GeographicPoint(83.0f, -111.0f);
        GeographicPoint gp1 = new GeographicPoint(83.0f, -111.0f);
        GeographicPoint gp2 = new GeographicPoint(-83.0f, 111.0f);
        assertTrue(gp.equals(gp1));
        assertFalse(gp.equals(gp2));

    }

    //Testing hashcode
    @Test
    public void testHashCode(){
        GeographicPoint gp = new GeographicPoint(83.0f, -111.0f);
        GeographicPoint gp1 = new GeographicPoint(83.0f, -111.0f);
        assertEquals(gp.hashCode(), gp1.hashCode());
    }

    //Testing toString
    @Test
    public void testToString(){
        GeographicPoint gp = new GeographicPoint(83.0f, -111.0f);
        assertEquals("GeographicPoint{latitude=83.0, longitude=-111.0}", gp.toString());
    }
}
