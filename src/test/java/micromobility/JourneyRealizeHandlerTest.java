package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.VehicleID;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.Server;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JourneyRealizeHandlerTest {

    private Server serverMock;
    private QRDecoder qrDecoderMock;
    private ArduinoMicroController arduinoMock;
    private UnbondedBTSignal btSignalMock;
    private JourneyMetricsCalculator metricsCalculator;
    private JourneyRealizeHandler handler;

    @BeforeEach
    void setUp() {
        serverMock = mock(Server.class);
        qrDecoderMock = mock(QRDecoder.class);
        arduinoMock = mock(ArduinoMicroController.class);
        btSignalMock = mock(UnbondedBTSignal.class);

        // Inyectar el nuevo componente de cálculo
        metricsCalculator = new JourneyMetricsCalculator();

        // Ahora el handler recibe metricsCalculator
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, arduinoMock, btSignalMock, metricsCalculator);
    }

    @Test
    void scanQR_ShouldPairVehicleSuccessfully() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();
        doNothing().when(btSignalMock).BTbroadcast();

        handler.scanQR(qrImage, station, location);

        assertNotNull(handler.getJourney());
        assertEquals("VEH123", handler.getVehicle().getVehicleID());
        assertTrue(handler.getJourney().isInProgress());
    }

    @Test
    void scanQR_ShouldThrowExceptionWhenVehicleNotAvailable() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        VehicleID vehicleID = new VehicleID("VEH123");

        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(vehicleID);
        doThrow(new PMVNotAvailException("The requested PMV is not available."))
                .when(serverMock).checkPMVAvail(vehicleID);

        assertThrows(PMVNotAvailException.class, () -> handler.scanQR(qrImage, station, location));

        verify(qrDecoderMock, times(1)).getVehicleID(qrImage);
        verify(serverMock, times(1)).checkPMVAvail(vehicleID);
        verify(arduinoMock, never()).setBTconnection();
    }

    @Test
    void startDriving_ShouldSetVehicleUnderWay() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();

        handler.scanQR(qrImage, station, location);

        doNothing().when(arduinoMock).startDriving();

        handler.startDriving();

        assertEquals(PMVState.UnderWay, handler.getVehicle().getState());
    }

    @Test
    void startDriving_ShouldThrowExceptionWhenVehicleNotAvailable() {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.Available, new GeographicPoint(41.40338F, 2.17403F));
        handler.vehicle = vehicle; // Ajusta si tu implementación difiere

        assertThrows(ProceduralException.class, handler::startDriving);
    }

    @Test
    void stopDriving_ShouldCalculateMetricsAndUpdateState() throws Exception {
        // Simulación de un trayecto iniciado
        BufferedImage qrImage = mock(BufferedImage.class);
        StationID station = new StationID("ST123");
        GeographicPoint location = new GeographicPoint(41.40338F, 2.17403F);

        when(qrDecoderMock.getVehicleID(qrImage)).thenReturn(new VehicleID("VEH123"));
        doNothing().when(serverMock).checkPMVAvail(any(VehicleID.class));
        doNothing().when(serverMock).registerPairing(any(), any(), any(), any(), any());
        doNothing().when(arduinoMock).setBTconnection();

        handler.scanQR(qrImage, station, location);

        doNothing().when(arduinoMock).startDriving();
        handler.startDriving();

        StationID endStation = new StationID("ST456");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        doNothing().when(arduinoMock).stopDriving();
        when(serverMock.stopPairing(any(), any(), any(), any(), any(), anyFloat(), anyFloat(), anyInt(), any())).thenReturn(true);
        doNothing().when(arduinoMock).undoBTconnection();

        handler.stopDriving(endStation, endLocation);

        assertEquals(PMVState.Available, handler.getVehicle().getState());
        assertFalse(handler.getJourney().isInProgress());
    }

    @Test
    void stopDriving_ShouldThrowExceptionWhenJourneyNotInProgress() {
        PMVehicle vehicle = new PMVehicle("VEH123", PMVState.Available, new GeographicPoint(41.40338F, 2.17403F));
        handler.vehicle = vehicle;
        handler.journey = null; // Sin journey inicializado

        StationID endStation = new StationID("ST123");
        GeographicPoint endLocation = new GeographicPoint(41.40438F, 2.17503F);

        assertThrows(ProceduralException.class, () -> handler.stopDriving(endStation, endLocation));
    }
}