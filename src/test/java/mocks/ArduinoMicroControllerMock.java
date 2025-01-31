package mocks;

import exceptions.ConnectException;
import exceptions.PMVPhisicalException;
import micromobility.JourneyRealizeHandler;
import services.smartfeatures.ArduinoMicroController;

/**
 * Mock implementation of the {@link ArduinoMicroController} interface.
 * Simulates Bluetooth connection and vehicle control behavior for testing purposes.
 */
public class ArduinoMicroControllerMock implements ArduinoMicroController {
    private boolean connectionEstablished = false;
    private JourneyRealizeHandler handler;
    private boolean physiscalCondition = true;
    private boolean batteryLow = false;
    private boolean sensorFailure = false;

    /**
     * Establishes a Bluetooth connection.
     *
     * @throws ConnectException If the connection fails.
     */
    @Override
    public void setBTconnection() throws ConnectException {
        connectionEstablished = true;
    }

    /**
     * Starts driving the vehicle.
     *
     * @throws PMVPhisicalException If the vehicle has physical issues.
     * @throws java.net.ConnectException If the Bluetooth connection is not established.
     */
    @Override
    public void startDriving() throws PMVPhisicalException, java.net.ConnectException {
        validateConnection();
        validatePhysicalState();
        handler.startDriving();
    }

    /**
     * Stops driving the vehicle.
     *
     * @throws PMVPhisicalException If the vehicle has physical issues.
     * @throws java.net.ConnectException If the Bluetooth connection is not established.
     */
    @Override
    public void stopDriving() throws PMVPhisicalException, java.net.ConnectException {
        validateConnection();
        validatePhysicalState();
        handler.stopDriving();
    }

    /**
     * Disconnects the Bluetooth connection.
     */
    @Override
    public void undoBTconnection() {
        connectionEstablished = false;
    }

    /**
     * Sets the journey handler.
     *
     * @param handler The journey realization handler.
     */
    public void setJourneyRealizeHandler(JourneyRealizeHandler handler) {
        this.handler = handler;
    }

    /**
     * Sets the physical condition of the vehicle.
     *
     * @param condition The physical condition status.
     */
    public void setPhysiscalCondition(boolean condition) {
        this.physiscalCondition = condition;
    }

    /**
     * Sets whether the vehicle has a low battery.
     *
     * @param batteryLow The battery status.
     */
    public void setBatteryLow(boolean batteryLow) {
        this.batteryLow = batteryLow;
    }

    /**
     * Sets whether the vehicle has a sensor failure.
     *
     * @param sensorFailure The sensor failure status.
     */
    public void setSensorFailure(boolean sensorFailure) {
        this.sensorFailure = sensorFailure;
    }

    /**
     * Validates if a Bluetooth connection has been established.
     *
     * @throws ConnectException If no connection is established.
     */
    private void validateConnection() throws ConnectException {
        if (!connectionEstablished) {
            throw new ConnectException("Bluetooth connection not established");
        }
    }

    /**
     * Validates the physical condition of the vehicle.
     *
     * @throws PMVPhisicalException If there is a physical issue with the vehicle.
     */
    private void validatePhysicalState() throws PMVPhisicalException {
        if (!physiscalCondition) {
            throw new PMVPhisicalException("Vehicle condition not favorable");
        }
        if (batteryLow) {
            throw new PMVPhisicalException("Battery is too low to operate");
        }
        if (sensorFailure) {
            throw new PMVPhisicalException("Sensor malfunction detected");
        }
    }
}