package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exceptions.*;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.Server;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class JourneyRealizeHandler {
    private final Server server;
    private final QRDecoder qrDecoder;
    private final ArduinoMicroController arduino;
    private final UnbondedBTSignal btSignal;
    private final JourneyMetricsCalculator metricsCalculator;

    // El JourneyService asociado al viaje actual
    JourneyService journey;
    // El vehículo actualmente en uso
    PMVehicle vehicle;
    // El usuario actual
    UserAccount userAccount;

    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduino,
                                 UnbondedBTSignal btSignal, JourneyMetricsCalculator metricsCalculator) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
        this.btSignal = btSignal;
        this.metricsCalculator = metricsCalculator;
    }

    public void scanQR(BufferedImage qrImage, StationID station, GeographicPoint location)
            throws CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidPairingArgsException, java.net.ConnectException {
        if (station == null || location == null) {
            throw new ProceduralException("Station or location not provided before scanning QR.");
        }

        VehicleID vhID = qrDecoder.getVehicleID(qrImage);
        server.checkPMVAvail(vhID);

        userAccount = new UserAccount("USER123");
        journey = new JourneyService(userAccount, vhID);
        journey.startService(station, location);

        // Estado del vehículo al emparejar
        vehicle = new PMVehicle(vhID.getId(), PMVState.NotAvailable, location);

        // Conectar con el arduino
        arduino.setBTconnection();

        // Registrar el emparejamiento en el servidor
        server.registerPairing(userAccount, vhID, station, location, LocalDateTime.now());

        // Emitir señal BT
        btSignal.BTbroadcast();
    }

    public void startDriving() throws ProceduralException, ConnectException, PMVPhisicalException {
        if (vehicle == null || journey == null || vehicle.getState() != PMVState.NotAvailable || !journey.isInProgress()) {
            throw new ProceduralException("Vehicle not ready to start driving.");
        }

        arduino.startDriving();
        vehicle.changeState(PMVState.UnderWay);
    }

    public void stopDriving(StationID endStation, GeographicPoint endLocation)
            throws ProceduralException, java.net.ConnectException, InvalidPairingArgsException, PMVPhisicalException {
        if (vehicle == null || journey == null || vehicle.getState() != PMVState.UnderWay || !journey.isInProgress()) {
            throw new ProceduralException("Vehicle not under way or journey not in progress.");
        }

        try {
            arduino.stopDriving();
            // Calcular métricas usando metricsCalculator
            float distance = metricsCalculator.calculateDistance(journey.getOriginPoint(), endLocation);
            int duration = metricsCalculator.calculateDuration(journey.getInitTime(), LocalDateTime.now());
            float avgSpeed = metricsCalculator.calculateAvgSpeed(distance, duration);
            BigDecimal cost = metricsCalculator.calculateImport(distance, duration, avgSpeed);

            // Finalizar el servicio en journey
            journey.finishService(endStation, endLocation, distance, duration, avgSpeed, cost);

            // Actualizar servidor
            server.stopPairing(userAccount, journey.getVehicleID(), endStation, endLocation, LocalDateTime.now(),
                    avgSpeed, distance, duration, cost);

            // Deshacer conexión BT
            arduino.undoBTconnection();
            vehicle.changeState(PMVState.Available);
        } catch (Exception e) {
            // Incluso si hay error, deshacer la conexión BT
            arduino.undoBTconnection();
            throw e;
        }
    }

    // Métodos getters o setters adicionales.
    public PMVehicle getVehicle() {
        return vehicle;
    }

    public JourneyService getJourney() {
        return journey;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}