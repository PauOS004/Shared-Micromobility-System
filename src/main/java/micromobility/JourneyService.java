package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JourneyService {
    private UserAccount user;
    private VehicleID vehicleID;
    private StationID originStation;
    private GeographicPoint originPoint;
    private boolean inProgress;
    private StationID endStation;
    private GeographicPoint endPoint;
    private float distance;
    private int duration;
    private float avgSpeed;
    private BigDecimal cost;
    private LocalDateTime initTime;
    private LocalDateTime endTime;

    /**
     * Constructor principal, requiere UserAccount y VehicleID.
     */
    public JourneyService(UserAccount user, VehicleID vehicleID) {
        this.user = user;
        this.vehicleID = vehicleID;
    }

    /**
     * Constructor adicional para pruebas, cuando se pasa null
     */
    public JourneyService(Object dummy) {
        // Este constructor se utiliza sólo en test para simular sin datos reales.
        this.user = null;
        this.vehicleID = null;
        this.initTime = null;
    }

    /**
     * Constructor adicional para pruebas, recibiendo un LocalDateTime
     */
    public JourneyService(LocalDateTime initTime) {
        this.user = null;
        this.vehicleID = null;
        this.initTime = initTime;
    }

    public void startService(StationID station, GeographicPoint point) {
        this.originStation = station;
        this.originPoint = point;
        this.inProgress = true;
        this.initTime = LocalDateTime.now();
    }

    public void finishService(StationID endStation, GeographicPoint endPoint, float distance, int duration, float avgSpeed, BigDecimal cost) {
        this.endStation = endStation;
        this.endPoint = endPoint;
        this.distance = distance;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.cost = cost;
        this.inProgress = false;
        this.endTime = LocalDateTime.now();
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public VehicleID getVehicleID() {
        return vehicleID;
    }

    public UserAccount getUser() {
        return user;
    }

    public StationID getOriginStation() {
        return originStation;
    }

    public StationID getEndStation() {
        return endStation;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public float getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDate getInitDate() {
        return initTime != null ? initTime.toLocalDate() : null;
    }
}