package data;

import exceptions.InvalidGeoCoordinatesException;

/**
 * Representa un punto geográfico con coordenadas de latitud y longitud.
 * Garantiza que las coordenadas sean válidas dentro de los rangos permitidos.
 */
final public class GeographicPoint {
    private final float latitude;
    private final float longitude;

    /**
     * Crea un nuevo punto geográfico con latitud y longitud específicas.
     * @param lat Latitud del punto (-90 a 90 grados).
     * @param lon Longitud del punto (-180 a 180 grados).
     * @throws InvalidGeoCoordinatesException Si las coordenadas están fuera de los rangos permitidos.
     */
    public GeographicPoint(float lat, float lon) {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new InvalidGeoCoordinatesException();
        }
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Obtiene la latitud del punto.
     * @return Latitud en grados.
     */
    public float getLatitude() { return latitude; }

    /**
     * Obtiene la longitud del punto.
     * @return Longitud en grados.
     */
    public float getLongitude() { return longitude; }

    /**
     * Compara si dos objetos GeographicPoint son iguales.
     * @param o Objeto a comparar.
     * @return true si ambos objetos tienen las mismas coordenadas, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        return Float.compare(gP.latitude, latitude) == 0 && Float.compare(gP.longitude, longitude) == 0;
    }

    /**
     * Calcula el código hash basado en las coordenadas del punto.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return 31 * Float.floatToIntBits(latitude) + Float.floatToIntBits(longitude);
    }

    /**
     * Representación en cadena del punto geográfico.
     * @return Cadena con la latitud y longitud del punto.
     */
    @Override
    public String toString() {
        return "GeographicPoint{latitude=" + latitude + ", longitude=" + longitude + "}";
    }
}

