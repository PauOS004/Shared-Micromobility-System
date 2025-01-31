package mocks;

import data.*;
import exceptions.ConnectException;
import exceptions.InvalidPairingArgsException;
import exceptions.PMVNotAvailException;
import exceptions.PairingNotFoundException;
import micromobility.JourneyService;
import services.Server;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ServerMock implements Server {

    private boolean isVehicleAvailable = true;
    private boolean isServerAvailable = true;
    private final Map<VehicleID, StationID> pairedVehicles = new HashMap<>();
    private final Map<JourneyService, Boolean> registeredJourneys = new HashMap<>();
    private final Map<ServiceID, Boolean> registeredPayment = new HashMap<>();


    public void setVehicleAvailable(boolean available) {
        this.isVehicleAvailable = available;
    }


    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException {
        if (!isVehicleAvailable || pairedVehicles.containsKey(vhID)) {
            throw new PMVNotAvailException("Vehicle not available");
        }
    }


    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {

        validateRegisterPairing(user, veh, st, loc, date);

        setPairing(user, veh, st, loc, date);
    }


    private void validateRegisterPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {

        validateCommonArgs(user, veh, st, loc, date);
        if (pairedVehicles.containsKey(veh)) {
            throw new ConnectException("Vehicle is already paired");
        }
    }


    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, PairingNotFoundException {
        validateCommonArgs(user, veh, st, loc, date);
        validateStopPairing(veh, avSp, dist, dur, imp);

        pairedVehicles.remove(veh);

        for (JourneyService service : registeredJourneys.keySet()) {
            if (service.getVehicleID().equals(veh)) {
                unPairRegisterService(service);
                break;
            }
        }

        registerLocation(veh, st);
    }


    private void validateStopPairing(VehicleID veh, float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException{

        if (avSp <= 0 || dist <= 0 || dur <= 0 || imp == null) {
            throw new InvalidPairingArgsException("Invalid unpairing arguments");
        }

        if (!pairedVehicles.containsKey(veh)) {
            throw new InvalidPairingArgsException("Vehicle not paired");
        }

    }


    private void validateCommonArgs(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("Invalid pairing arguments");
        }
    }


    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {

        pairedVehicles.put(veh, st);

        JourneyService newJourneyService = new JourneyService();
        newJourneyService.setUser(user);
        newJourneyService.setVehicleID(veh);
        newJourneyService.setOrgStatID(st);
        newJourneyService.setOriginPoint(loc);
        newJourneyService.setInitDate(date);

        registeredJourneys.put(newJourneyService, true);
        System.out.println("Pairing registered internally: " + user.getUsername() + " -> " + veh.getId());
    }


    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (s == null || !registeredJourneys.containsKey(s)) {
            throw new PairingNotFoundException("Journey service not registered");
        }
        registeredJourneys.put(s, false);
        System.out.println("Journey service unregistered: " + s);
    }


    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        System.out.println("Location registered for vehicle " + veh.getId() + " at station " + st.getId());
    }


    public void simulateRegisterJourneyService(JourneyService service, StationID st) {
        pairedVehicles.put(service.getVehicleID(), st);
        registeredJourneys.put(service, true);

    }


    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp,
                                char payMeth) throws ConnectException{
        if (servID == null || user == null || imp == null || payMeth == '\0') {
            throw new IllegalArgumentException("Invalid arguments: ServiceID, UserAccount, amount, and payment method are required.");
        }

        if (imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }


        if (payMeth != 'W' && payMeth != 'T' && payMeth != 'B' && payMeth != 'P') {
            throw new IllegalArgumentException("Invalid payment method. Accepted values: W, T, B, P.");
        }

        if (!isServerAvailable) {
            throw new ConnectException("Server is not available.");
        }

        if (!registeredPayment.containsKey(servID)){
            registeredPayment.put(servID,true);
        }else{
            throw new ConnectException("Payment already registered");
        }


        System.out.println("Payment registered:");
        System.out.println("Service ID: " + servID.getId());
        System.out.println("User: " + user.getUsername());
        System.out.println("Amount: " + imp);
        System.out.println("Payment Method: " + getPaymentMethodName(payMeth));
    }


    private String getPaymentMethodName(char payMeth) {
        return switch (payMeth) {
            case 'W' -> "Wallet";
            case 'T' -> "Credit Card";
            case 'B' -> "Bizum";
            case 'P' -> "Payment Gateway";
            default -> "Unknown";
        };
    }


    public void setServerAvailable(Boolean isServerAvailable) {
        this.isServerAvailable = isServerAvailable;
    }


    public void simulateRegisterPayment(ServiceID servID) {
        registeredPayment.put(servID,true);

    }
}
