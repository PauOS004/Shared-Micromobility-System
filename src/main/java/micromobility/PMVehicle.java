package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import java.awt.image.BufferedImage;

/**
 * Represents a personal mobility vehicle (PMV) with attributes for identification, state, and location.
 */
public class PMVehicle {
    private VehicleID vehicleID; // Unique identifier for the vehicle
    private PMVState state; // Current state of the vehicle
    private GeographicPoint location; // Vehicle's current location
    private BufferedImage QRImg; // QR code image associated with the vehicle

    /**
     * Constructs a new PMVehicle.
     *
     * @param vehicleID The unique vehicle identifier.
     * @param state     The initial state of the vehicle.
     * @param location  The initial location of the vehicle.
     * @param QRImg     The QR code image associated with the vehicle.
     */
    public PMVehicle(VehicleID vehicleID, PMVState state, GeographicPoint location, BufferedImage QRImg) {
        this.vehicleID = vehicleID;
        this.state = state;
        this.location = location;
        this.QRImg = QRImg;
    }
    
    /**
     * Gets the vehicle's unique identifier.
     *
     * @return The vehicle's ID.
     */
    public VehicleID getVehicleID() {
        return vehicleID;
    }

    /**
     * Gets the vehicle's current state.
     *
     * @return The vehicle's state.
     */
    public PMVState getState() {
        return state;
    }

    /**
     * Gets the vehicle's current location.
     *
     * @return The geographic point of the vehicle.
     */
    public GeographicPoint getLocation() {
        return location;
    }

    /**
     * Gets the QR code image associated with the vehicle.
     *
     * @return The QR code image.
     */
    public BufferedImage getQRImg() {
        return QRImg;
    }

    /**
     * Sets the vehicle state to NotAvailable.
     */
    public void setNotAvailb() {
        state = PMVState.NotAvailable;
    }

    /**
     * Sets the vehicle state to UnderWay.
     */
    public void setUnderWay() {
        state = PMVState.UnderWay;
    }

    /**
     * Sets the vehicle state to Available.
     */
    public void setAvailb() {
        state = PMVState.Available;
    }

    /**
     * Updates the vehicle's location.
     *
     * @param gP The new location.
     */
    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }

    /**
     * Updates the vehicle's unique identifier.
     *
     * @param vehicleID The new vehicle ID.
     */
    public void setVehicleID(VehicleID vehicleID) {
        this.vehicleID = vehicleID;
    }
}