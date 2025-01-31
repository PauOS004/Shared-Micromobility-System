package data;

import exceptions.InvalidUserAccountException;
import micromobility.payment.Wallet;

/**
 * Representa una cuenta de usuario dentro del sistema.
 * Cada cuenta de usuario tiene un nombre de usuario único y una billetera asociada.
 */
final public class UserAccount {
    private final String username;
    private Wallet wallet;

    /**
     * Crea una nueva cuenta de usuario con un nombre de usuario específico.
     * @param username Nombre de usuario único.
     * @throws exceptions.InvalidVehicleIDException Si el nombre de usuario es nulo, vacío o no cumple con el formato requerido.
     */
    public UserAccount(String username) {
        if (username == null || username.isEmpty() || !username.matches("[a-zA-Z0-9._-]{3,20}")) {
            throw new InvalidUserAccountException();
        }
        this.username = username;
    }

    /**
     * Obtiene el nombre de usuario de la cuenta.
     * @return Nombre de usuario.
     */
    public String getUsername() { return username; }

    /**
     * Obtiene la billetera asociada a la cuenta del usuario.
     * @return Objeto Wallet asociado a la cuenta.
     */
    public Wallet getWallet(){
        return this.wallet;
    }

    /**
     * Asigna una billetera a la cuenta del usuario.
     * @param wallet Objeto Wallet a asociar.
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Compara si dos objetos UserAccount son iguales.
     * @param o Objeto a comparar.
     * @return true si ambos objetos tienen el mismo nombre de usuario, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return username.equals(that.username);
    }

    /**
     * Calcula el código hash basado en el nombre de usuario.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * Representación en cadena de la cuenta de usuario.
     * @return Cadena con el nombre de usuario.
     */
    @Override
    public String toString() {
        return "UserAccount{username='" + username + "'}";
    }
}
