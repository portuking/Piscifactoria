package registros;

/**
 * Clase que gestiona los registros
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class Registros {
    /** Instancia utilizada para delegar las transcripciones de eventos */
    private static Transcripciones transcripciones = null;
    private static Log log = null;
    /** Instancia única de la clase */
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
        if (transcripciones == null) {
            transcripciones = Transcripciones.getInstance(nombrePartida);
        }
        if (log == null) {
            log = Log.getInstance(nombrePartida);
        }
    }

    /**
     * Registra el inicio de la partida.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param monedas       Cantidad de monedas iniciales.
     * @param pecesImplementados Lista de peces implementados.
     * @param pisciInicial  Piscifactoría inicial.
     */
    public static void registrarInicio(String nombrePartida, int monedas, String[] pecesImplementados, String pisciInicial) {
        if (transcripciones != null && log != null) {
            transcripciones.trStart(nombrePartida, monedas, pecesImplementados, pisciInicial);
            log.logStart(nombrePartida, pisciInicial);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la compra de comida.
     * 
     * @param cantidadComida Cantidad de comida comprada.
     * @param tipoComida     Tipo de comida comprada.
     * @param monedasGastadas Cantidad de monedas gastadas.
     * @param almacen        Almacenamiento de la comida.
     * @param piscifactoria  Piscifactoría en la que se realiza la compra.
     */
    public static void registrarCompraComida(int cantidadComida, String tipoComida, int monedasGastadas, boolean almacen, String piscifactoria) {
        if (transcripciones != null && log != null) {
            transcripciones.trComprarComdida(cantidadComida, tipoComida, monedasGastadas, almacen, piscifactoria);
            log.logComprarComida(cantidadComida, tipoComida, almacen, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la compra de peces.
     * 
     * @param nombrePez     Nombre del pez comprado.
     * @param tipoSex       Sexo del pez comprado.
     * @param monedas       Cantidad de monedas gastadas.
     * @param tanque        Tanque en el que se añade el pez.
     * @param piscifactoria Piscifactoría en la que se realiza la compra.
     */
    public static void registrarComprarPeces(String nombrePez, boolean tipoSex, int monedas, int tanque, String piscifactoria) {
        if (transcripciones != null && log != null) {
            transcripciones.trComprarPeces(nombrePez, tipoSex, monedas, tanque, piscifactoria);
            log.logComprarPeces(nombrePez, tipoSex, tanque, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la venta de peces.
     * 
     * @param peces         Cantidad de peces vendidos.
     * @param piscifactoria Piscifactoría en la que se realiza la venta.
     * @param bolivares     Cantidad de bolívares obtenidos.
     */
    public static void registrarVenderPeces(int peces, String piscifactoria, int bolivares) {
        if (transcripciones != null && log != null) {
            transcripciones.trVenderPeces(peces, piscifactoria, bolivares);
            log.logVenderPeces(peces, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la acción de limpiar un tanque.
     * 
     * @param tanque        Identificador del tanque a limpiar.
     * @param piscifactoria Piscifactoría en la que se realiza la limpieza.
     */
    public static void registrarLimpiarTanque(int tanque, String piscifactoria) {
        if (transcripciones != null && log != null) {
            transcripciones.trLimpiarTanque(tanque, piscifactoria);
            log.logLimpiarTanque(tanque, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la acción de vaciar un tanque.
     * 
     * @param tanque        Identificador del tanque a vaciar.
     * @param piscifactoria Piscifactoría en la que se realiza el vaciado.
     */
    public static void registrarVaciarTanque(int tanque, String piscifactoria) {
        if (transcripciones != null && log != null) {
            transcripciones.trVaciarTanque(tanque, piscifactoria);
            log.logVaciarTanque(tanque, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la compra de un edificio.
     * 
     * @param tipoEdificio  Tipo de edificio comprado.
     * @param monedas       Cantidad de monedas gastadas.
     * @param tanque        Tanque asociado al edificio.
     * @param piscifactoria Piscifactoría en la que se realiza la compra.
     */
    public static void registrarCompraEdificio(String tipoEdificio, int monedas, int tanque, String piscifactoria) {
        if (transcripciones != null && log != null) {
            transcripciones.trCompraEdificio(tipoEdificio, monedas, tanque, piscifactoria);
            log.logCompraEdificio(tipoEdificio, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la mejora de un edificio.
     * 
     * @param piscifactoria Piscifactoría en la que se realiza la mejora.
     * @param aumentoComida Aumento de capacidad de comida.
     * @param monedas       Cantidad de monedas gastadas.
     */
    public static void registrarMejoraEdificio(String piscifactoria, int aumentoComida, int monedas) {
        if (transcripciones != null && log != null) {
            transcripciones.trMejoraEdificio(piscifactoria, aumentoComida, monedas);
            log.logMejorarEdificio(piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra el paso de un día.
     * 
     * @param dia           Día actual.
     * @param pecesRio      Cantidad de peces en piscifactorías de río.
     * @param pecesMar      Cantidad de peces en piscifactorías de mar.
     * @param monedas       Cantidad de monedas actual.
     * @param pecesVendidos Cantidad de peces vendidos.
     */
    public static void registrarNextDay(int dia, int pecesRio, int pecesMar, int monedas, int pecesVendidos) {
        if (transcripciones != null && log != null) {
            transcripciones.trNextDay(dia, pecesRio, pecesMar, monedas, pecesVendidos);
            log.logNextDay(dia);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra operaciones ocultas.
     * 
     * @param codOp         Código de la operación.
     * @param piscifactoria Piscifactoría asociada.
     * @param cantidadDolares Cantidad de dólares involucrados.
     */
    public static void registrarOpsOcultas(int codOp, String piscifactoria, int cantidadDolares) {
        if (transcripciones != null && log != null) {
            transcripciones.trOpsOcultas(codOp, piscifactoria, cantidadDolares);
            log.logOpsOcultas(codOp, piscifactoria);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra la salida del sistema.
     */
    public static void registrarSalir() {
        if (log != null) {
            log.logSalir();
        } else {
            System.out.println("Advertencia: Log no está inicializado.");
        }
    }

    /**
     * Registra la creación de una recompensa.
     * 
     * @param recompensa    Nombre de la recompensa.
     */
    public static void registrarCreaRecompensa(String recompensa) {
        if (transcripciones != null && log != null) {
            transcripciones.trCreaRecompensas(recompensa);
            log.logCreaRecompensa(recompensa);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra el uso de una recompensa.
     * 
     * @param recompensa    Nombre de la recompensa.
     */
    public static void registrarUsoRecompensa(String recompensa) {
        if (transcripciones != null && log != null) {
            transcripciones.trUsaRecompensas(recompensa);
            log.logUsaRecompensa(recompensa);
        } else {
            System.out.println("Advertencia: Transcripciones o Log no están inicializados.");
        }
    }

    /**
     * Registra un error en el sistema.
     * 
     * @param error         Descripción del error.
     */
    public static void registrarError(String error) {
        if (log != null) {
            log.logError(error);
        } else {
            System.out.println("Advertencia: Log no está inicializado.");
        }
    }

    /**
     * Cierra los registros y libera recursos.
     */
    public static void cerrarRegistros() {
        if (transcripciones != null) {
            transcripciones.trCerrar();
        }
        if (log != null) {
            log.logClose();
        }
    }
}
