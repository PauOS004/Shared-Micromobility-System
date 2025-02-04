package micromobility;

import data.*;
import micromobility.payment.*;
import exceptions.*;
import services.smartfeatures.*;
import services.*;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;


public class JourneyRealizeHandler {
    private final Server server;
    private final QRDecoder qrDecoder;
    private final ArduinoMicroController arduino;
    private Driver driver;
    private StationID stationID;
    private boolean BTconnected;
    private JourneyService journeyService;
    private PMVehicle vehicle;
    private UserAccount user;
    private Payment payment;

    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduino) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
    }

    public void scanQR() throws ConnectException, CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidPairingArgsException {
        if (vehicle == null || stationID == null) {
            throw new ProceduralException("Station or location not provided before scanning QR.");
        }
        BufferedImage qrImage = vehicle.getQRImg();
        VehicleID vhID = qrDecoder.getVehicleID(qrImage);

        server.checkPMVAvail(vhID);

        initBTConnection();

        vehicle.setNotAvailb();

        user = driver.getUserAccount();
        LocalDateTime initDate = LocalDateTime.now();
        GeographicPoint vehLoc = vehicle.getLocation();

        server.registerPairing(user, vhID, stationID, vehicle.getLocation(), initDate);

        journeyService = new JourneyService(user, vhID);
        journeyService.setUser(user);
        journeyService.setVehicleID(vhID);
        journeyService.setInitDate(initDate);
        journeyService.setOriginPoint(vehLoc);
        journeyService.setOrgStatID(stationID);


    }

    public void unPairVehicle() throws exceptions.ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (journeyService == null || !journeyService.isInProgress()) {
            throw new ProceduralException("No active journey found.");
        }

        if(!BTconnected || driver == null){
            throw new exceptions.ConnectException("Connection error");
        }

        if (vehicle.getState() != PMVState.UnderWay) {
            throw new ProceduralException("Vehicle is not Underway.");
        }

        if (stationID == null || journeyService.getOriginStation() == stationID) {
            throw new ProceduralException("End station doesn't exists.");
        }


        LocalDateTime endDate = LocalDateTime.now();
        calculateValues(vehicle.getLocation(), endDate);

        float distance = journeyService.getDistance();
        int duration = journeyService.getDuration();
        float avgSpeed = journeyService.getAvgSpeed();
        calculateImport(distance, duration, avgSpeed, endDate);

        journeyService.setEndPoint(vehicle.getLocation());
        journeyService.setEndDate(endDate);

        journeyService.setServiceID(new ServiceID("ABCDEFG1"));

        server.stopPairing(driver.getUserAccount(), vehicle.getVehicleID(), stationID, vehicle.getLocation(), endDate, avgSpeed, distance, duration, journeyService.getCost());

        vehicle.setAvailb();
        journeyService.setServiceFinish();


    }

    public void startDriving() throws ConnectException, ProceduralException {

        if (vehicle.getState() != PMVState.NotAvailable) {
            throw new ProceduralException("Vehicle scanQR failed.");
        }

        if(journeyService == null){
            throw new ProceduralException("No active journey already.");
        }

        if(!BTconnected || driver == null){
            throw new ConnectException();
        }

        vehicle.setUnderWay();
        journeyService.setServiceInit();
    }


    public void stopDriving() throws ConnectException, ProceduralException {

        if (journeyService == null || !journeyService.isInProgress()) {
            throw new ProceduralException("No active journey found.");
        }

        if(!BTconnected || driver == null){
            throw new ConnectException();
        }

        if (vehicle.getState() != PMVState.UnderWay) {
            throw new ProceduralException("Vehicle is not Underway.");
        }

    }

    public void selectPaymentMethod (char opt) throws ProceduralException,
            NotEnoughWalletException, ConnectException {

        if(journeyService == null){
            throw new ProceduralException("No active journey.");
        }

        if(!BTconnected || driver == null){
            throw new ConnectException("Connection refused");
        }

        processPayment(opt,journeyService);

        try {
            realizePayment(journeyService.getCost());
            System.out.println("Payment completed using Wallet.");
        } catch (NotEnoughWalletException e) {
            throw new NotEnoughWalletException("Insufficient Wallet.");
        }

        server.registerPayment(journeyService.getServiceID(), journeyService.getUser(), journeyService.getCost(), opt);

        arduino.undoBTconnection();
        BTconnected = false;

    }

    private void initBTConnection() throws ConnectException {
        try {
            arduino.setBTconnection();
            BTconnected = true; //Connected
        } catch (ConnectException ex) {
            BTconnected = false; //Not connected
            throw ex;
        }
    }

    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        //Calculate distance, duration, and average speed
        journeyService.setDistance(33.0f);
        journeyService.setDuration(54);
        journeyService.setAvgSpeed(8.3f);
    }
    private void calculateImport(float distance, int duration, float avgSpeed, LocalDateTime date) {
        //Calculate import
        BigDecimal cost = BigDecimal.valueOf(81.0f);
        journeyService.setCost(cost);

    }

    public void broadcastStationID(StationID stationID_) throws exceptions.ConnectException {
        //Simulate broadcasting process
        if (stationID_ == null) {
            throw new exceptions.ConnectException("Station ID cannot be null.");
        }

        stationID = stationID_;
    }

    private void processPayment(char opt, JourneyService journeyService) throws ProceduralException {
        if (opt != 'W' && opt != 'T' && opt != 'B' && opt != 'P') {
            throw new ProceduralException("Invalid option. Valid option values: W, T, B, P.");
        }
        Wallet wallet = journeyService.getUser().getWallet();

        if (wallet == null) {
            throw new ProceduralException("Wallet not found for user.");
        }

        payment = new WalletPayment(journeyService.getUser(), journeyService, journeyService.getCost(), wallet);
    }

    private void executePayment(BigDecimal amount) throws NotEnoughWalletException {
        payment.executePayment();
        System.out.println("Payment executed: " + amount + " deducted successfully.");
    }

    private void realizePayment (BigDecimal imp) throws NotEnoughWalletException{

        if (driver == null || driver.getUserAccount() == null) {
            throw new IllegalArgumentException("Driver or UserAccount cannot be null.");
        }

        executePayment(journeyService.getCost());

        System.out.println("Realize payment executed: " + imp + " deducted from wallet.");
    }

    // Métodos getters o setters adicionales.
    public PMVehicle getVehicle() {
        return vehicle;
    }

    public JourneyService getJourney() {
        return journeyService;
    }

    public UserAccount getUserAccount() {
        return user;
    }

    public void setVehicle(PMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setJourneyService(JourneyService service){
        this.journeyService = service;
    }

    public void setBT(Boolean BTconnected){
        this.BTconnected = BTconnected;
    }
}