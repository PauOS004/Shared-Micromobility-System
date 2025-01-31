package data;

import exceptions.InvalidStationIDException;

/**
 * Representa un identificador único para una estación.
 * El ID debe ser una cadena alfanumérica de entre 3 y 10 caracteres en mayúsculas.
 */
final public class StationID {
    private final String id;

    /**
     * Crea un nuevo identificador de estación.
     * @param id Identificador único de la estación.
     * @throws InvalidStationIDException Si el ID es nulo, vacío o no cumple con el formato requerido.
     */
    public StationID(String id) {
        if (id == null || id.isEmpty() || !id.matches("[A-Z0-9]{3,10}")) {
            throw new InvalidStationIDException();
        }
        this.id = id;
    }

    /**
     * Obtiene el identificador de la estación.
     * @return ID de la estación.
     */
    public String getId() { return id; }

    /**
     * Compara si dos objetos StationID son iguales.
     * @param o Objeto a comparar.
     * @return true si ambos objetos tienen el mismo ID, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return id.equals(stationID.id);
    }

    /**
     * Calcula el código hash basado en el ID de la estación.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Representación en cadena del identificador de estación.
     * @return Cadena con el ID de la estación.
     */
    @Override
    public String toString() {
        return "StationID{id='" + id + "'}";
    }
}
