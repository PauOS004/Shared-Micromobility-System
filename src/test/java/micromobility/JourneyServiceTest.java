package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JourneyServiceTest {

    @Test
    void startAndFinishService() {
        // Preparamos el usuario y el vehículo
        UserAccount user = new UserAccount("USER123");
        VehicleID vehicleID = new VehicleID("VEH123");

        // Creamos la instancia de JourneyService con el constructor principal
        JourneyService journey = new JourneyService(user, vehicleID);

        // Definimos la estación y ubicación de inicio
        StationID originStation = new StationID("ST123");
        GeographicPoint originPoint = new GeographicPoint(41.40338F, 2.17403F);

        // Iniciamos el servicio
        journey.startService(originStation, originPoint);
        assertTrue(journey.isInProgress());
        assertEquals(originStation, journey.getOriginStation());
        assertEquals(originPoint, journey.getOriginPoint());

        // Definimos la estación y ubicación de fin
        StationID endStation = new StationID("ST456");
        GeographicPoint endPoint = new GeographicPoint(41.40438F, 2.17503F);

        // Finalizamos el servicio con datos simulados
        journey.finishService(endStation, endPoint, 5.0f, 10, 30.0f, BigDecimal.valueOf(20));

        // Comprobamos que el servicio ya no está en progreso y que los datos se guardaron correctamente
        assertFalse(journey.isInProgress());
        assertEquals(5.0f, journey.getDistance());
        assertEquals(10, journey.getDuration());
        assertEquals(30.0f, journey.getAvgSpeed(), 0.01f);
        assertEquals(BigDecimal.valueOf(20), journey.getCost());
    }
}