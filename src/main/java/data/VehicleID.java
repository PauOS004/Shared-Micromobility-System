package data;

import exceptions.InvalidVehicleIDException;

/**
 * Representa un identificador único para un vehículo.
 * El ID debe ser una cadena alfanumérica de entre 5 y 15 caracteres en mayúsculas.
 */
final public class VehicleID {
    private final String id;

    /**
     * Crea un nuevo identificador de vehículo.
     * @param id Identificador único del vehículo.
     * @throws InvalidVehicleIDException Si el ID es nulo, vacío o no cumple con el formato requerido.
     */
    public VehicleID(String id) {
        if (id == null || !id.matches("VEH-[0-9]{5,15}")) {
            throw new InvalidVehicleIDException();
        }
        this.id = id;
    }

    /**
     * Obtiene el identificador del vehículo.
     * @return ID del vehículo.
     */
    public String getId() { return id; }

    /**
     * Compara si dos objetos VehicleID son iguales.
     * @param o Objeto a comparar.
     * @return true si ambos objetos tienen el mismo ID, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return id.equals(vehicleID.id);
    }

    /**
     * Calcula el código hash basado en el ID del vehículo.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Representación en cadena del identificador de vehículo.
     * @return Cadena con el ID del vehículo.
     */
    @Override
    public String toString() {
        return "VehicleID{id='" + id + "'}";
    }
}
