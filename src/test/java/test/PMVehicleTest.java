package test;

import data.GeographicPoint;
import data.VehicleID;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PMVehicleTest {

    private PMVehicle pmVehicle;
    private GeographicPoint initialLocation;
    private VehicleID vehicleID;
    private BufferedImage QRImage;

    //setup of a valid PMVehicle with the related atributes
    @BeforeEach
    void setUp() {
        initialLocation = new GeographicPoint(10, -20);
        vehicleID = new VehicleID("VEH-12345");
        QRImage = new BufferedImage(10, 10, 1);
        pmVehicle = new PMVehicle(vehicleID, PMVState.Available, initialLocation, QRImage);
    }

    //Testing the correct construction of the object (correct atributes)
    @Test
    void testInitialState() {
        assertEquals(PMVState.Available, pmVehicle.getState());
        assertEquals(initialLocation, pmVehicle.getLocation());
        assertEquals(vehicleID, pmVehicle.getVehicleID());
        assertEquals(QRImage, pmVehicle.getQRImg());
    }

    //Testing the method setNotAvailable
    @Test
    void testSetNotAvailable() {
        pmVehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, pmVehicle.getState());
    }

    //Testing the method setUnderWay
    @Test
    void testSetUnderWay() {
        pmVehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, pmVehicle.getState());
    }

    //Testing the method setAvailable
    @Test
    void testSetAvailable() {
        pmVehicle.setAvailb();
        assertEquals(PMVState.Available, pmVehicle.getState());
    }

    //Testing the method setLocation
    @Test
    void testSetLocation() {
        GeographicPoint newLocation = new GeographicPoint(20, -30);
        pmVehicle.setLocation(newLocation);
        assertEquals(newLocation, pmVehicle.getLocation());
    }
}
