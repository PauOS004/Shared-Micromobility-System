package units.micromobility;

import data.GeographicPoint;
import micromobility.JourneyMetricsCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyMetricsCalculatorTest {

    @Test
    void testCalculateDistance() {
        JourneyMetricsCalculator calculator = new JourneyMetricsCalculator();

        // Caso 1: Mismo punto
        GeographicPoint p1 = new GeographicPoint(41.40338F, 2.17403F);
        assertEquals(0.0f, calculator.calculateDistance(p1, p1), 0.0001f);

        // Caso 2: Distancia conocida (diferencia simple)
        GeographicPoint p2 = new GeographicPoint(0f, 0f);
        GeographicPoint p3 = new GeographicPoint(3f, 4f);
        // (3^2 + 4^2)^(1/2) = 5
        assertEquals(5.0f, calculator.calculateDistance(p2, p3), 0.0001f);

        // Caso 3: Distancias con números negativos o invertidos
        GeographicPoint p4 = new GeographicPoint(10f, 10f);
        GeographicPoint p5 = new GeographicPoint(7f, 14f);
        // diff lat = 3, diff lon = -4 (al cuadrado: 9+16=25, sqrt=5)
        assertEquals(5.0f, calculator.calculateDistance(p4, p5), 0.0001f);
    }

    @Test
    void testCalculateDuration() {
        JourneyMetricsCalculator calculator = new JourneyMetricsCalculator();

        LocalDateTime start = LocalDateTime.of(2024, 12, 20, 10, 0);
        LocalDateTime endSame = LocalDateTime.of(2024, 12, 20, 10, 0);
        LocalDateTime end30Min = LocalDateTime.of(2024, 12, 20, 10, 30);
        LocalDateTime end90Min = LocalDateTime.of(2024, 12, 20, 11, 30);

        // Caso 1: Sin diferencia de tiempo
        assertEquals(0, calculator.calculateDuration(start, endSame));

        // Caso 2: 30 minutos de diferencia
        assertEquals(30, calculator.calculateDuration(start, end30Min));

        // Caso 3: 90 minutos de diferencia
        assertEquals(90, calculator.calculateDuration(start, end90Min));
    }

    @Test
    void testCalculateAvgSpeed() {
        JourneyMetricsCalculator calculator = new JourneyMetricsCalculator();

        // Caso 1: Distancia 0 o duración 0 => velocidad =0
        assertEquals(0.0f, calculator.calculateAvgSpeed(0f, 0), 0.0001f);
        assertEquals(0.0f, calculator.calculateAvgSpeed(10f, 0), 0.0001f);

        // Caso 2: Cálculo simple: distancia=5, duración=30min
        // velocidad = dist/(dur/60) = 5/(30/60)=5/0.5=10
        assertEquals(10.0f, calculator.calculateAvgSpeed(5f, 30), 0.0001f);

        // Caso 3: distancia=10, duración=60min => 10/(60/60)=10/(1)=10
        assertEquals(10.0f, calculator.calculateAvgSpeed(10f, 60), 0.0001f);
    }

    @Test
    void testCalculateImport() {
        JourneyMetricsCalculator calculator = new JourneyMetricsCalculator();

        // Caso 1: distancia=0, duración=0 => importe base 0.5
        // cost = 0.5 + (0*0.1)+(0*0.05)=0.5
        BigDecimal cost = calculator.calculateImport(0f, 0, 0f);
        assertEquals(0, cost.compareTo(new BigDecimal("0.5")));

        // Caso 2: distancia=5, duración=30, avgSpeed no influye en el cálculo directo
        // cost = base(0.5)+(5*0.1)+(30*0.05)=0.5+0.5+(1.5)=2.5
        BigDecimal cost2 = calculator.calculateImport(5f, 30, 10f);
        assertEquals(0, cost2.compareTo(new BigDecimal("2.5")));
    }
}