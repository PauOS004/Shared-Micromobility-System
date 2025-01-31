package data;

/**
 * Representa un identificador único para un servicio.
 * El ID debe ser una cadena alfanumérica de entre 5 y 15 caracteres en mayúsculas.
 */
public final class ServiceID {
    private final String id;

    /**
     * Crea un nuevo identificador de servicio.
     * @param id Identificador único del servicio.
     * @throws IllegalArgumentException Si el ID es nulo, vacío o no cumple con el formato requerido.
     */
    public ServiceID(String id) {
        if (id == null || id.isEmpty() || !id.matches("[A-Z0-9]{5,15}")) {
            throw new IllegalArgumentException("Invalid ServiceID format");
        }
        this.id = id;
    }

    /**
     * Obtiene el identificador del servicio.
     * @return ID del servicio.
     */
    public String getId() {
        return id;
    }

    /**
     * Compara si dos objetos ServiceID son iguales.
     * @param o Objeto a comparar.
     * @return true si ambos objetos tienen el mismo ID, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID serviceID = (ServiceID) o;
        return id.equals(serviceID.id);
    }

    /**
     * Calcula el código hash basado en el ID del servicio.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Representación en cadena del identificador de servicio.
     * @return Cadena con el ID del servicio.
     */
    @Override
    public String toString() {
        return "ServiceID{id='" + id + "'}";
    }
}