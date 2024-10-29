package sistema;

/**
 * Clase que representa el Sistema de monedas
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class SISMonedas {

     /**
     * Instancia única de la clase SISMonedas (implementación del patrón Singleton).
     */
    public static SISMonedas saldo = null;

    /**
     * Número de monedas disponibles en el sistema.
     */
    protected int monedas;
     
    /**
     * Constructor privado que inicializa el saldo de monedas a 100. Este constructor
     * es privado para evitar que se creen múltiples instancias de la clase.
     */
    private SISMonedas(){
       monedas = 100;
    }

    /**
     * Devuelve la única instancia de la clase SISMonedas. Si la instancia aún no ha sido creada,
     * la crea. De lo contrario, devuelve la instancia ya existente.
     *
     * @return La única instancia de la clase SISMonedas.
     */
    public static SISMonedas getInstance(){
        if(saldo == null){
            saldo = new SISMonedas();
        }
        return saldo;
    }

    /**
     * Permite realizar un pago restando la cantidad especificada de monedas del saldo actual.
     * Si el saldo de monedas es suficiente, se descuenta la cantidad solicitada.
     * Si no hay suficiente saldo, se muestra un mensaje indicando que no hay suficientes monedas.
     *
     * @param cantidad El número de monedas a pagar.
     */
    public void pagar(int cantidad){
        if (this.monedas >= cantidad) {
        this.monedas -= cantidad;
        System.out.println("Pago realizado con exito");
        } else {
        System.out.println("No tienes suficientes momendas para comprar");
        }
    }

    /**
     * Devuelve la cantidad de monedas disponibles.
     *
     * @return El saldo actual de monedas.
     */
    public int getMonedas() {
        return monedas;
    }

    /**
     * Establece una nueva cantidad de monedas en el saldo.
     *
     * @param monedas La nueva cantidad de monedas a establecer.
     */
    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    /**
     * Devuelve la instancia actual de SISMonedas (el saldo).
     *
     * @return La instancia actual de SISMonedas.
     */
    public static SISMonedas getSaldo() {
        return saldo;
    }

    /**
     * Establece una nueva instancia de SISMonedas. 
     *
     * @param saldo La nueva instancia de SISMonedas a establecer.
     */
    public static void setSaldo(SISMonedas saldo) {
        SISMonedas.saldo = saldo;
    }

}
