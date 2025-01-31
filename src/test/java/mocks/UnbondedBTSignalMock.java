package mocks;

import data.StationID;
import exceptions.ConnectException;
import micromobility.JourneyRealizeHandler;
import services.smartfeatures.UnbondedBTSignal;

import java.util.concurrent.TimeUnit;

/**
 * Mock implementation of the {@link UnbondedBTSignal} interface.
 * Simulates broadcasting Bluetooth signals from an unbonded station.
 */
public class UnbondedBTSignalMock implements UnbondedBTSignal {

    private JourneyRealizeHandler handler;
    private final long interval = 50000; // Interval in milliseconds
    private StationID stationID = new StationID("ST001");

    /**
     * Continuously broadcasts the station ID via Bluetooth.
     * Handles connection errors and thread interruptions.
     */
    @Override
    public void BTbroadcast() {
        while (true) {
            try {
                handler.broadcastStationID(stationID);
                TimeUnit.MILLISECONDS.sleep(interval);
            } catch (ConnectException e) {
                System.err.println("Connection error: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Sets the JourneyRealizeHandler instance.
     *
     * @param handler The journey realization handler.
     */
    public void setJourneyRealizeHandler(JourneyRealizeHandler handler) {
        this.handler = handler;
    }

    /**
     * Sets the station ID to be broadcasted.
     *
     * @param st The station ID.
     */
    public void setStationID(StationID st) {
        this.stationID = st;
    }
}