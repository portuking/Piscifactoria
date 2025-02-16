package registros;

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
        transcripciones.trStart(nombrePartida, monedas, pecesImplementados, pisciInicial);
        log.logStart(nombrePartida, pisciInicial);
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
        transcripciones.trComprarComdida(cantidadComida, tipoComida, monedasGastadas, almacen, piscifactoria);
        log.logComprarComida(cantidadComida, tipoComida, almacen, piscifactoria);//TODO CHEKEAR SI REGISTRA LA COMIDA LLENA
    }

    public static void registrarComprarPeces(String nombrePez, boolean tipoSex, int monedas, int tanque, String piscifactoria){
        transcripciones.trComprarPeces(nombrePez, tipoSex, monedas, tanque, piscifactoria);
        log.logComprarPeces(nombrePez, tipoSex, tanque, piscifactoria);
    }

    public static void registrarVenderPeces(int peces, String piscifactoria, int bolivares){
        transcripciones.trVenderPeces(peces, piscifactoria, bolivares);
        log.logVenderPeces(peces, piscifactoria);
    }
    /**
     * Registra la acción de limpiar un tanque.
     *
     * @param tanque        el identificador del tanque a limpiar.
     * @param piscifactoria información o nombre de la piscifactoria donde se realiza la limpieza.
     */
    public static void registrarLimpiarTanque(int tanque , String piscifactoria){
        transcripciones.trLimpiarTanque(tanque , piscifactoria);
        log.logLimpiarTanque(tanque, piscifactoria);
    }
    /**
     * Registra la acción de vaciar un tanque.
     *
     * @param tanque        el identificador del tanque a vaciar.
     * @param piscifactoria información o nombre de la piscifactoria donde se realiza el vaciado.
     */
    public static void registrarVaciarTanque(int tanque , String piscifactoria){
        transcripciones.trVaciarTanque(tanque , piscifactoria);
        log.logVaciarTanque(tanque, piscifactoria);
    }

    public static void registrarCompraEdificio(String tipoEdificio, int monedas, int tanque, String piscifactoria){
        transcripciones.trCompraEdificio(tipoEdificio, monedas, tanque, piscifactoria);
        log.logCompraEdificio(tipoEdificio, piscifactoria);
    }

    public static void registrarMejoraEdificio(String piscifactoria, int aumentoComida, int monedas){
        transcripciones.trMejoraEdificio(piscifactoria, aumentoComida, monedas);
        log.logMejorarEdificio(piscifactoria);
    }

    /**FALTA */
    public static void regitrarNextDay(int dia, int pecesRio, int pecesMar, int monedas, int pecesVendidos){
        transcripciones.trNextDay(dia, pecesRio, pecesMar, monedas, pecesVendidos);
        log.logNextDay(dia);
    }

    public static void registraropsOcultasint(int codOp , String piscifactoria , int cantidadDolares){
        transcripciones.trOpsOcultas(codOp , piscifactoria , cantidadDolares);
        log.logOpsOcultas(codOp, piscifactoria);
    }

    public static void registrarSalir(){
        log.logSalir();
    }

    public static void registrarError(String error){
        log.logError(error);
    }

    public static void cerrarRegistros() {
        if (transcripciones != null) {
            transcripciones.trCerrar();
        }
        if (log != null) {
            log.logClose();
        }
    }
}
