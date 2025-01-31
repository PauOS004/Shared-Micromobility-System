package services.smartfeatures;

import exceptions.ConnectException;
import exceptions.PMVPhisicalException;
/**
 * Interfaz para manejar interacciones con el controlador Arduino de los vehículos.
 */
public interface ArduinoMicroController {

    /**
     * Establece la conexión Bluetooth con el vehículo.
     * @throws ConnectException Si ocurre un error durante la conexión.
     */
    void setBTconnection() throws ConnectException;

    /**
     * Inicia la conducción del vehículo.
     * @throws PMVPhisicalException Si ocurre un fallo físico en el vehículo.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void startDriving() throws PMVPhisicalException, java.net.ConnectException;

    /**
     * Detiene la conducción del vehículo.
     * @throws PMVPhisicalException Si ocurre un fallo físico en el vehículo.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void stopDriving() throws PMVPhisicalException, java.net.ConnectException;

    /**
     * Deshace la conexión Bluetooth.
     * @throws ConnectException Si ocurre un error al desconectar.
     */
    void undoBTconnection() throws ConnectException;
}
