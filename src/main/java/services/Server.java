package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exceptions.InvalidPairingArgsException;
import exceptions.PMVNotAvailException;
import exceptions.PairingNotFoundException;
import micromobility.JourneyService;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

/**
 * Interfaz para manejar interacciones con el servidor.
 */
public interface Server {

    /**
     * Verifica si un vehículo está disponible.
     * @param veh ID del vehículo.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void checkPMVAvail(VehicleID veh) throws ConnectException, PMVNotAvailException;

    /**
     * Registra un emparejamiento entre un usuario y un vehículo.
     * @param user Usuario que realiza el emparejamiento.
     * @param veh ID del vehículo.
     * @param st ID de la estación.
     * @param loc Ubicación de la estación.
     * @param date Fecha y hora del emparejamiento.
     * @throws ConnectException Si ocurre un error de conexión.
     * @throws InvalidPairingArgsException Si los argumentos no son válidos.
     */
    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws ConnectException, InvalidPairingArgsException;

    /**
     * Finaliza un emparejamiento y registra un servicio de trayecto.
     * @param user Usuario que finaliza el emparejamiento.
     * @param vehicle ID del vehículo.
     * @param station ID de la estación.
     * @param location Ubicación de la estación.
     * @param date Fecha y hora de finalización.
     * @param avgSpeed Velocidad media del trayecto.
     * @param distance Distancia recorrida en el trayecto.
     * @param duration Duración del trayecto en segundos.
     * @param importValue Valor total del trayecto.
     * @return true si el emparejamiento se finalizó correctamente, false en caso contrario.
     * @throws PairingNotFoundException Si el emparejamiento no se encuentra.
     */
    void stopPairing(UserAccount user, VehicleID vehicle, StationID station,
                        GeographicPoint location, LocalDateTime date, float avgSpeed,
                        float distance, int duration, BigDecimal importValue)
            throws InvalidPairingArgsException, exceptions.ConnectException, PairingNotFoundException;

    /**
     * Asigna un emparejamiento entre un usuario y un vehículo en una estación.
     * @param user Usuario que realiza el emparejamiento.
     * @param veh ID del vehículo.
     * @param st ID de la estación.
     * @param loc Ubicación de la estación.
     * @param date Fecha y hora del emparejamiento.
     */
    void setPairing(UserAccount user, VehicleID veh, StationID st,
                    GeographicPoint loc, LocalDateTime date);

    /**
     * Desempareja un usuario de un vehículo y registra el servicio de trayecto.
     * @param s Servicio de trayecto asociado al emparejamiento.
     * @throws PairingNotFoundException Si no se encuentra un emparejamiento activo.
     */
    void unPairRegisterService(JourneyService s) throws PairingNotFoundException;

    /**
     * Registra la ubicación actual de un vehículo en una estación.
     * @param veh ID del vehículo.
     * @param st ID de la estación.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void registerLocation(VehicleID veh, StationID st) throws ConnectException;

    /**
     * Registra un pago realizado por un usuario.
     * @param sid ID del servicio.
     * @param user Usuario que realiza el pago.
     * @param amount Monto del pago.
     * @param method Método de pago.
     * @throws ConnectException Si ocurre un error de conexión.
     */
    void registerPayment(data.ServiceID sid, UserAccount user, BigDecimal amount, char method) throws ConnectException;
}

