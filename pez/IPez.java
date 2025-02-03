package pez;

/**
 * Interfaz para un pez que crece, se reproduce y muere.
 *
 * @author Adri�n
 */
public interface IPez {

    /**
     * @return Nombre del pez
     */
    public String getName();

    /**
     * @return Nombre cient�fico del pez
     */
    public String getScientificName();

    /**
     * Muestra por pantalla la informaci�n b�sica del pez.
     */
    public void info();

    /**
     * Muestra por pantalla el estado del pez. Su nivel de crecimiento.
     */
    public void status();

    /**
     * Hace crecer al pez.
     */
    public void grow(int food);

    /**
     * Verifica si es lo suficientemente grande como para ser vendido.
     * @return true si puede venderse, false en caso contrario.
     */
    public boolean isMature();
    
    /**
     * Verifica si es capaz de reproducirse.
     * @return true si puede reproducirse, false en caso contrario.
     */
    public boolean isFertile();

    /**
     * Verifica si el pez est� muerto o no.
     * @return True si est� muerto, false si vive.
     */
    public boolean isDead();
    
    /**
     * Reproduce al pez
     * @return Una instancia de s� mismo.
     */
    public IPez populate();
}
