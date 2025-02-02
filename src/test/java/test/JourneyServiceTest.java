
package test;

import data.*;
import micromobility.Driver;
import micromobility.JourneyService;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyServiceTest {

    private JourneyService journeyService;

    //Constructing a new service
    @BeforeEach
    void setUp() {
        journeyService = new JourneyService();
    }

    //Testing set and get of init date
    @Test
    void testSetAndGetInitDate() {
        LocalDateTime initDate = LocalDateTime.of(2025, 12, 1, 10, 0);
        journeyService.setInitDate(initDate);

        assertEquals(initDate, journeyService.getInitTime());
    }

    //Testing set and get of end date
    @Test
    void testSetAndGetEndDate() {
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 1, 12, 0);
        journeyService.setEndDate(endDate);

        assertEquals(endDate, journeyService.getEndTime());
    }

    //Testing set and get of duration
    @Test
    void testSetAndGetDuration() {
        journeyService.setDuration(120);
        assertEquals(120, journeyService.getDuration());
    }

    //Testing set and get of the distance
    @Test
    void testSetAndGetDistance() {
        journeyService.setDistance(15.5f);
        assertEquals(15.5, journeyService.getDistance(), 0.01);
    }

    //Testing set and get of average speed
    @Test
    void testSetAndGetAvgSpeed() {
        journeyService.setAvgSpeed(7.75f);
        assertEquals(7.75, journeyService.getAvgSpeed(), 0.01);
    }

    //Testing set and get of origin point
    @Test
    void testSetAndGetOriginPoint() {
        GeographicPoint origin = new GeographicPoint(33.3f, 69.69f);
        journeyService.setOriginPoint(origin);

        assertEquals(origin, journeyService.getOriginPoint());
    }

    //Testing set and get of origin point
    @Test
    void testSetAndGetEndPoint() {
        GeographicPoint end = new GeographicPoint(33.3f, 69.69f);
        journeyService.setEndPoint(end);

        assertEquals(end, journeyService.getEndPoint());
    }

    //Testing set and get of import value
    @Test
    void testSetAndGetImportValue() {
        BigDecimal importValue = new BigDecimal("12.99");
        journeyService.setCost(importValue);

        assertEquals(importValue, journeyService.getCost());
    }

    //Testing set and check in progress, allows to know if the service is in progress or not
    @Test
    void testSetAndCheckInProgress() {
        journeyService.setServiceInit();
        assertTrue(journeyService.isInProgress());

        journeyService.setServiceFinish();
        assertFalse(journeyService.isInProgress());
    }

    //Testing set and get of the driver
    @Test
    void testSetAndGetDriver() {
        Driver driver = new Driver("Jose Antonio", "jose.antonio@ejemplo.com", "1234567890", null, "123-456-789", new UserAccount("jose_antonio"));
        journeyService.setUser(driver.getUserAccount());

        assertEquals(driver.getUserAccount(), journeyService.getUser());
    }

    //Testing set and get of the vehicle
    @Test
    void testSetAndGetVehicle() {
        GeographicPoint initialLocation = new GeographicPoint(10, -20);
        VehicleID vehicleID = new VehicleID("VEH-12345");
        BufferedImage QRImage = new BufferedImage(10, 10, 1);
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.Available, initialLocation, QRImage);
        journeyService.setVehicleID(vehicle.getVehicleID());

        assertEquals(vehicle.getVehicleID(), journeyService.getVehicleID());
    }

    //Testing set and get of the a service ID (OPtional)
    @Test
    void testSetAndGetServiceID() {
        ServiceID serviceID = new ServiceID("SVC20253");
        journeyService.setServiceID(serviceID);

        assertEquals(serviceID, journeyService.getServiceID());
    }

    //Testing set and get of a station ID
    @Test
    void testSetAndGetOrgStatID() {
        StationID stationID = new StationID("STAT001");
        journeyService.setOrgStatID(stationID);

        assertEquals(stationID, journeyService.getOriginStation());
    }
}
