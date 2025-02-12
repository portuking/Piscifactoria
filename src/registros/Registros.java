package registros;

import edificios.piscifactoria.Piscifactoria;

public class Registros {
    /**Instancia utilizada para delegar las transcripciones de eventos */
    private static Transcripciones transcripciones = null;
    private static Log log = null;
    /**Instancia única de la clase */
    private static Registros instance = null;
    /**
     * Obtiene la instancia única de la clase, asegurando que solo haya una por partida.
     * 
     * @return Instancia única de Registros.
     */
    public static Registros getInstance() {
        if (instance == null) {
            instance = new Registros();
        }
        return instance;
    }
    /**
     * Crea un nuevo registro de transcripciones para la partida.
     * 
     * @param nombrePartida Nombre de la partida para generar el archivo.
     */
    public void crearRegistro(String nombrePartida) {
        transcripciones = Transcripciones.getInstance(nombrePartida);
        log = Log.getInstance(nombrePartida);

    }
    /**
     * Registra el inicio de la partida.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param monedas Cantidad de monedas iniciales.
     * @param pecesImplementados Lista de peces implementados.
     * @param pisciInicial Piscifactoría inicial.
     */
    public static void registrarInicio(String nombrePartida, int monedas, String[] pecesImplementados, String pisciInicial) {
        transcripciones.start(nombrePartida, monedas, pecesImplementados, pisciInicial);
        log.start(nombrePartida, pisciInicial);
    }

        
    /**
     * Registra la compra de comida.
     * 
     * @param cantidadComida Cantidad de comida comprada.
     * @param tipoComida    Tipo de comida comprada.    
     * @param monedasGastadas Cantidad de monedas gastadas.
     * @param almacenado Almacenamiento de la comida. 
     * @param piscifactoria Piscifactoría en la que se realiza la compra.
     */
    public static void registrarCompraComida(int cantidadComida, String tipoComida, int monedasGastadas, boolean almacen, String piscifactoria){
        transcripciones.comprarComdida(cantidadComida, tipoComida, monedasGastadas, almacen, piscifactoria);
        log.comprarComida(cantidadComida, tipoComida, almacen, piscifactoria);//TODO CHEKEAR SI REGISTRA LA COMIDA LLENA
    }

    public static void registrarComprarPeces(String nombrePez, boolean tipoSex, int monedas, int tanque, String piscifactoria){
        transcripciones.comprarPeces(nombrePez, tipoSex, monedas, tanque, piscifactoria);
    }

    public static void registrarVenderPeces(){
        transcripciones.venderPeces();
    }
    /**
     * Registra la acción de limpiar un tanque.
     *
     * @param tanque        el identificador del tanque a limpiar.
     * @param piscifactoria información o nombre de la piscifactoria donde se realiza la limpieza.
     */
    public static void registrarLimpiarTanque(int tanque , String piscifactoria){
        transcripciones.limpiarTanque(tanque , piscifactoria);
    }
    /**
     * Registra la acción de vaciar un tanque.
     *
     * @param tanque        el identificador del tanque a vaciar.
     * @param piscifactoria información o nombre de la piscifactoria donde se realiza el vaciado.
     */
    public static void registrarVaciarTanque(int tanque , String piscifactoria){
        transcripciones.vaciarTanque(tanque , piscifactoria);
    }

    public static void registrarCompraEdificio(String tipoEdificio, int monedas, int tanque, String piscifactoria){
        transcripciones.compraEdificio(tipoEdificio, monedas, tanque, piscifactoria);
    }

    public static void registrarMejoraEdificio(String piscifactoria, int aumentoComida, int monedas){
        transcripciones.mejoraEdificio(piscifactoria, aumentoComida, monedas);
    }

    public static void regitrarNextDay(int dia, int pecesRio, int pecesMar, int monedas, int pecesVendidos){
        transcripciones.nextDay(dia, pecesRio, pecesMar, monedas, pecesVendidos);
    }

    public static void registraropsOcultasint(int codOp , String piscifactoria , int cantidadDolares){
        transcripciones.opsOcultas(codOp , piscifactoria , cantidadDolares);
    }
}
