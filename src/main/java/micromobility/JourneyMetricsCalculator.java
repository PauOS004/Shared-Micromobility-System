package micromobility;

import data.GeographicPoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JourneyMetricsCalculator {

    public float calculateDistance(GeographicPoint start, GeographicPoint end) {
        float latDiff = end.getLatitude() - start.getLatitude();
        float lonDiff = end.getLongitude() - start.getLongitude();
        return (float) Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    public int calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }

    public float calculateAvgSpeed(float distance, int durationInMinutes) {
        if (durationInMinutes <= 0) return 0;
        return distance / (durationInMinutes / 60.0f);
    }

    public BigDecimal calculateImport(float distance, int duration, float avgSpeed) {
        BigDecimal baseRate = new BigDecimal("0.5");
        BigDecimal distanceCost = BigDecimal.valueOf(distance).multiply(new BigDecimal("0.1"));
        BigDecimal durationCost = BigDecimal.valueOf(duration).multiply(new BigDecimal("0.05"));
        return baseRate.add(distanceCost).add(durationCost);
    }
}