package test;

import data.*;
import exceptions.*;
import micromobility.*;
import micromobility.payment.Wallet;
import mocks.ArduinoMicroControllerMock;
import mocks.QRDecoderMock;
import mocks.ServerMock;
import mocks.UnbondedBTSignalMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyRealizeHandlerTest {

    private Server serverMock;
    private QRDecoder qrDecoderMock;
    private ArduinoMicroController arduinoMock;
    private JourneyRealizeHandler handler;
    private PMVehicle vehicle;
    private Driver user;
    private UnbondedBTSignal unbondedBTSignal;

    @BeforeEach
    public void setup() {
        //Create mock services
        serverMock = new ServerMock();
        qrDecoderMock = new QRDecoderMock();
        arduinoMock = new ArduinoMicroControllerMock();
        unbondedBTSignal = new UnbondedBTSignalMock();

        //Initialize mocked services
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, arduinoMock);

        //Test vehicle
        GeographicPoint location = new GeographicPoint(31.30028f, 2.18583f);
        VehicleID vehicleID = new VehicleID("VEH-12345");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        vehicle = new PMVehicle(vehicleID, PMVState.Available, location, qrImage);
        handler.setVehicle(vehicle);
        //Test driver
        UserAccount userAccount = new UserAccount("mario_romera");
        userAccount.setWallet(new Wallet(BigDecimal.valueOf(150.00)));
        List<Character> paymentMethods = List.of('W', 'T', 'P', 'B');
        user = new Driver("Mario Romera", "mario.romera@ejemplo.com", "6658487879", paymentMethods, "111-222-333", userAccount);
        handler.setDriver(user);
    }

    // Verifying all major methods in the handler execute without exceptions
    @Test
    public void testAllClassMethods() throws Exception {
        StationID initialStationID = new StationID("AA000");
        StationID endStationID = new StationID("ZZ999");

        assertDoesNotThrow(() -> handler.broadcastStationID(initialStationID));
        assertDoesNotThrow(() -> handler.scanQR());
        assertDoesNotThrow(() -> handler.startDriving());
        assertDoesNotThrow(() -> handler.stopDriving());
        assertDoesNotThrow(() -> handler.broadcastStationID(endStationID));
        assertDoesNotThrow(() -> handler.unPairVehicle());
        assertDoesNotThrow(() -> handler.selectPaymentMethod('W'));


    }

    // Ensuring successful QR scan updates vehicle state and starts journey
    @Test
    public void testScanQRSucceeds() throws Exception {
        handler.broadcastStationID(new StationID("AA000"));

        assertDoesNotThrow(() -> handler.scanQR());

        assertEquals(PMVState.NotAvailable, vehicle.getState());
        assertNotNull(handler.getJourney());
    }

    // Simulating QR scan failure due to corrupted QR image
    @Test
    public void testScanQRFailsQRDecoding() throws ConnectException {

        ((QRDecoderMock) qrDecoderMock).setState(false);
        handler.broadcastStationID(new StationID("AA000"));

        Exception exception = assertThrows(CorruptedImgException.class, () -> handler.scanQR());
        assertEquals("QR code is corrupted", exception.getMessage());
    }

    // Testing QR scan failure due to unavailable vehicle
    @Test
    public void testScanQRFailsVehicleUnavailable() throws ConnectException {
        ((ServerMock) serverMock).setVehicleAvailable(false);
        handler.broadcastStationID(new StationID("AA000"));

        Exception exception = assertThrows(PMVNotAvailException.class, () -> handler.scanQR());
        assertEquals("Vehicle not available", exception.getMessage());
    }

    // Testing QR scan failure due to null vehicle
    @Test
    public void testScanQRFailsNullVehicle() {
        handler.setVehicle(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.scanQR());
        assertEquals("Station or location not provided before scanning QR.", exception.getMessage());
    }

    // Testing successful start driving with expected postconditions
    @Test
    public void testStartDrivingSucceeds() throws Exception {
        vehicle.setNotAvailb();
        JourneyService service = inicializeJourneyService();
        handler.setJourneyService(service);
        handler.setBT(true);

        assertDoesNotThrow(() -> handler.startDriving());

        assertEquals(PMVState.UnderWay, vehicle.getState());
        assertTrue(handler.getJourney().isInProgress());
    }

    // Testing start driving failure due to invalid state
    @Test
    public void testStartDrivingFailsInvalidState() {
        vehicle.setAvailb();

        Exception exception = assertThrows(ProceduralException.class, () -> handler.startDriving());
        assertEquals("Vehicle scanQR failed.", exception.getMessage());
    }




    // Testing successful vehicle unpairing
    @Test
    public void testUnPairVehicleSucceeds() throws Exception {
        vehicle.setUnderWay();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.broadcastStationID(new StationID("AA000"));
        handler.setBT(true);
        ((ServerMock) serverMock).simulateRegisterJourneyService(service,new StationID("AA000"));

        assertDoesNotThrow(() -> handler.unPairVehicle());

        assertEquals(PMVState.Available, vehicle.getState());
        assertFalse(handler.getJourney().isInProgress());
    }



    // Testing unpair failure due to invalid state
    @Test
    public void testUnPairVehicleFailsInvalidState() {
        vehicle.setAvailb();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.setBT(true);
        Exception exception = assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
        assertEquals("Vehicle is not Underway.", exception.getMessage());
    }



    // Testing unpair failure due to null journey
    @Test
    public void testUnPairVehicleFailsNullJourney() {
        vehicle.setUnderWay();
        handler.setJourneyService(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
        assertEquals("No active journey found.", exception.getMessage());
    }

    // Testing unpair failure due to connection failure during the process
    @Test
    public void testUnPairVehicleFailsConnectionFailure() throws ConnectException {
        vehicle.setUnderWay();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.broadcastStationID(new StationID("ZZ999"));
        arduinoMock.undoBTconnection();

        Exception exception = assertThrows(ConnectException.class, () -> handler.unPairVehicle());
        assertEquals("Connection error", exception.getMessage());
    }

    // Testing unpair failure due to invalid end station
    @Test
    public void testUnPairVehicleFailsInvalidEndStation() throws ConnectException {
        vehicle.setUnderWay();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.broadcastStationID(service.getOriginStation());
        handler.setBT(true);
        Exception exception = assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
        assertEquals("End station doesn't exists.", exception.getMessage());
    }

    // Testing unpair failure due to vehicle not being in transit
    @Test
    public void testUnPairVehicleFailsVehicleNotInTransit() throws ConnectException {
        vehicle.setAvailb();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.broadcastStationID(new StationID("ZZ999"));
        handler.setBT(true);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
        assertEquals("Vehicle is not Underway.", exception.getMessage());
    }

    //Testing successful broadcast with station ID  
    @Test
    public void testBroadcastStationIDSucceeds() {
        StationID stationID = new StationID("AA000");
        assertDoesNotThrow(() -> handler.broadcastStationID(stationID));
    }



    // Testing broadcast failure due to null station ID
    @Test
    public void testBroadcastStationIDFailsNullStationID() {
        Exception exception = assertThrows(ConnectException.class, () -> handler.broadcastStationID(null));
        assertEquals("Station ID cannot be null.", exception.getMessage());
    }

    // Testing successful stop driving functionality
    @Test
    public void testStopDrivingSucceeds() throws Exception {
        vehicle.setUnderWay();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.setBT(true);

        assertDoesNotThrow(() -> handler.stopDriving());
    }

    // Testing stop driving failure due to vehicle not being in transit
    @Test
    public void testStopDrivingFailsNotInTransit() {
        vehicle.setAvailb();
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.setBT(true);
        Exception exception = assertThrows(ProceduralException.class, () -> handler.stopDriving());
        assertEquals("Vehicle is not Underway.", exception.getMessage());
    }

    //Testing stop driving fails as the journey service is null
    @Test
    public void testStopDrivingFailsNullJourney() {
        vehicle.setUnderWay();
        handler.setJourneyService(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.stopDriving());
        assertEquals("No active journey found.", exception.getMessage());
    }

    // Testing driving start failure due to no active journey
    @Test
    public void testStartDrivingFailsNoJourney() {
        vehicle.setNotAvailb();
        handler.setJourneyService(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.startDriving());
        assertEquals("No active journey already.", exception.getMessage());
    }

    // Tests successful wallet payment method selection and balance update
    @Test
    public void testSelectPaymentMethodWalletSucceeds() throws Exception {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        service.setServiceID(new ServiceID("SRV00000"));
        service.setCost(BigDecimal.valueOf(21.0));
        Wallet wallet = new Wallet(BigDecimal.valueOf(100.00));
        service.getUser().setWallet(wallet);
        handler.setJourneyService(service);
        handler.setBT(true);

        assertDoesNotThrow(() -> handler.selectPaymentMethod('W'));

        assertEquals(BigDecimal.valueOf(79.00), wallet.getBalance());
    }

    // Testing payment failure due to invalid payment method selection
    @Test
    public void testSelectPaymentMethodFailsInvalidMethod() {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.setBT(true);
        Exception exception = assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('X'));
        assertEquals("Invalid option. Valid option values: W, T, B, P.", exception.getMessage());
    }

    // Testing payment failure due to no active journey
    @Test
    public void testWalletPaymentMethodSelectionFailsNoActiveJourney() {
        handler.setJourneyService(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('W'));
        assertEquals("No active journey.", exception.getMessage());
    }

    // Testing payment failure due to wallet not found
    @Test
    public void testWalletPaymentMethodSelectionFailsWalletNotFound() {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        handler.setJourneyService(service);
        handler.setBT(true);
        user.getUserAccount().setWallet(null);

        Exception exception = assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('W'));
        assertEquals("Wallet not found for user.", exception.getMessage());
    }

    // Testing payment failure due to insufficient funds  
    @Test
    public void testWalletPaymentMethodSelectionInsufficientFunds() {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        service.setServiceID(new ServiceID("SRV00000"));
        service.setCost(BigDecimal.valueOf(21.0));
        Wallet wallet = new Wallet(BigDecimal.valueOf(10.00));
        service.getUser().setWallet(wallet);
        handler.setJourneyService(service);
        handler.setBT(true);

        Exception exception = assertThrows(NotEnoughWalletException.class, () -> handler.selectPaymentMethod('W'));
        assertEquals("Insufficient Wallet.", exception.getMessage());
    }

    // Testing payment failure due to server unavailability  
    @Test
    public void testWalletPaymentMethodSelectionServerUnavailable() throws Exception {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        service.setServiceID(new ServiceID("SER12456"));
        service.setCost(BigDecimal.valueOf(33.0));
        Wallet wallet = new Wallet(BigDecimal.valueOf(80.00));
        service.getUser().setWallet(wallet);
        handler.setJourneyService(service);
        handler.setBT(true);
        ((ServerMock) serverMock).setServerAvailable(false);

        Exception exception = assertThrows(ConnectException.class, () -> handler.selectPaymentMethod('W'));
        assertEquals("Server is not available.", exception.getMessage());
    }

    //Testing payment fails as the payment has already been registered
    @Test
    public void testSelectPaymentMethodWalletsPaymentAlreadyRegistered() throws Exception {
        JourneyService service = inicializeJourneyService();
        service.setServiceInit();
        service.setServiceID(new ServiceID("SRV00000"));
        service.setCost(BigDecimal.valueOf(21.0));
        Wallet wallet = new Wallet(BigDecimal.valueOf(100.00));
        service.getUser().setWallet(wallet);
        handler.setJourneyService(service);
        handler.setBT(true);
        ((ServerMock) serverMock).simulateRegisterPayment(new ServiceID("SRV00000"));

        Exception exception = assertThrows(ConnectException.class, () -> handler.selectPaymentMethod('W'));
        assertEquals("Payment already registered", exception.getMessage());
    }

    // Initializes a JourneyService instance for testing.
    private JourneyService inicializeJourneyService() {
        JourneyService service = new JourneyService();
        service.setInitDate(LocalDateTime.now());
        service.setOriginPoint(vehicle.getLocation());
        service.setOrgStatID(new StationID("ST003"));
        service.setUser(user.getUserAccount());
        service.setVehicleID(vehicle.getVehicleID());

        return service;
    }
}
