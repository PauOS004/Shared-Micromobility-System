package micromobility;

import data.GeographicPoint;

public class PMVehicle {
    private String vehicleID; // Asumiendo que puede ser un identificador único
    private PMVState state;
    private GeographicPoint location;

    public PMVehicle(String vehicleID, PMVState state, GeographicPoint location) {
        this.vehicleID = vehicleID;
        this.state = state;
        this.location = location;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public void changeState(PMVState newState) {
        // Permitir transiciones idénticas
        if (this.state == newState) {
            return;
        }

        // Validar transiciones permitidas
        if (isValidStateTransition(this.state, newState)) {
            this.state = newState;
        } else {
            throw new IllegalStateException("Transition from " + this.state + " to " + newState + " is not allowed.");
        }
    }

    // Validar transiciones válidas
    private boolean isValidStateTransition(PMVState current, PMVState target) {
        return switch (current) {
            case Available -> target == PMVState.NotAvailable | target == PMVState.UnderWay;
            case NotAvailable -> target == PMVState.UnderWay || target == PMVState.Available;
            case UnderWay -> target == PMVState.Available || target == PMVState.NotAvailable;
            case TemporaryParking -> target == PMVState.UnderWay || target == PMVState.Available;
        };
    }

    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }

    @Override
    public String toString() {
        return "PMVehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", state=" + state +
                ", location=" + location +
                '}';
    }
}