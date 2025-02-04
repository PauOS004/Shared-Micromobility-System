package micromobility;

import data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JourneyService {

    private StationID originStation;
    private GeographicPoint originPoint;
    private boolean inProgress;
    private GeographicPoint endPoint;
    private float distance;
    private int duration;
    private float avgSpeed;
    private BigDecimal cost;
    private LocalDateTime initTime;
    private LocalDateTime endTime;
    private ServiceID serviceID;

    UserAccount user;
    VehicleID vehicleID;

    /**
     * Constructor principal, requiere UserAccount y VehicleID.
     */
    public JourneyService(UserAccount user, VehicleID vehicleID) {
        this.user = user;
        this.vehicleID = vehicleID;
    }

    public JourneyService(){}

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

    public void finishService(GeographicPoint endPoint, float distance, int duration, float avgSpeed, BigDecimal cost) {
        this.endPoint = endPoint;
        this.distance = distance;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.cost = cost;
        this.inProgress = false;
        this.endTime = LocalDateTime.now();
    }

    /**
     * ALL the getters methods
     * */
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
    public ServiceID getServiceID() {
        return serviceID;
    }

    /**
     * ALL the setters methods
     * */
    public void setServiceInit () {
        this.inProgress = true;
    }
    public void setServiceFinish () {
        this.inProgress = false;
    }
    public void setCost(BigDecimal cost){
        this.cost = cost;
    }
    public void setUser(UserAccount user){
        this.user = user;
    }
    public void setVehicleID(VehicleID vehicleID){
        this.vehicleID = vehicleID;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public void setDistance(float distance){
        this.distance = distance;
    }
    public void setAvgSpeed(float avgSpeed){
        this.avgSpeed = avgSpeed;
    }
    public void setServiceID(ServiceID serviceID){
        this.serviceID = serviceID;
    }
    public void setInitDate(LocalDateTime date){
        this.initTime = date;
    }
    public void setOriginPoint(GeographicPoint originPoint){
        this.originPoint = originPoint;
    }
    public void setEndPoint(GeographicPoint endPoint){
        this.endPoint = endPoint;
    }
    public void setOrgStatID(StationID originStation){
        this.originStation = originStation;
    }
    public void setEndDate(LocalDateTime date){
        this.endTime = date;
    }
}
