package registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Clase Log que implementa un sistema de registro de eventos en un archivo de logs.
 * Utiliza el patrón Singleton para asegurar que solo exista una instancia de la clase.
 */
public class Log {
    private static Log instance = null;
    private static final String PATH_LOG = "logs";
    private BufferedWriter bw;
    private String rutaArchivo;
    private static final String ERROR_LOG_FILE = PATH_LOG + "/0_errors.log";
    
    /**
     * Constructor privado de la clase Log.
     * Crea o abre un archivo de log con el nombre de la partida.
     * 
     * @param nombrePartida Nombre de la partida, usado para nombrar el archivo de log.
     */
    private Log(String nombrePartida) {
        this.rutaArchivo = PATH_LOG + "/" + nombrePartida + ".log";
        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(archivo, true));
        } catch (IOException e) {
            System.out.println("Error al abrir o crear el archivo de logs: " + e.getMessage());
        }
    }

    /**
     * Método para obtener la instancia única de la clase Log.
     * Si la instancia no existe, se crea una nueva.
     * 
     * @param nombrePartida Nombre de la partida para generar el archivo de log.
     * @return Instancia única de Log.
     */
    public static Log getInstance(String nombrePartida) {
        if (instance == null) {
            instance = new Log(nombrePartida);
        }
        return instance;
    }

    /**
     * Obtiene la fecha y hora actual en formato "yyyy-MM-dd HH:mm:ss".
     * 
     * @return Cadena con la fecha y hora actual formateada.
     */
    private String getFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Escribe una línea en el archivo de log, precedida por la fecha y hora actual.
     * 
     * @param linea Mensaje a escribir en el archivo de log.
     */
    public void write(String linea) {
        try {
            bw.write("[" + getFecha() + "] " + linea);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de logs: " + e.getMessage());
        }
    }

    /**
     * Registra el inicio de la partida y la piscifactoría inicial en el archivo de log.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param pisciInicial Nombre de la piscifactoría inicial.
     */
    public void logStart(String nombrePartida, String pisciInicial) {
        write("Inicio de la partida: " + nombrePartida + ".");
        write("Piscifactoría inicial: " + pisciInicial + ".");
    }

    /**
     * Registra la compra de comida en el archivo de log.
     * 
     * @param cantidadComida Cantidad de compida añadida
     * @param tipoComida Tipo de comida comprada
     * @param almacen Almacen central (si/no)
     * @param piscifactoria Piscifactoria en la que se realiza la compra
     */
    public void logComprarComida(int cantidadComida, String tipoComida, boolean almacen, String piscifactoria){
        write(cantidadComida + "de comida de tipo " + tipoComida + " comprada. Se almacena en" + (almacen ? "el almacén central" : "la piscifactoría " + piscifactoria + "."));
    }

    /**
     * Registra la compra de peces en el archivo de log.
     * 
     * @param nombrePez Nombre del pez comprado
     * @param tipoSex Tipo de sexo del pez (M/H)
     * @param tanque Tanque en el que se almacena el pez
     * @param piscifactoria Piscifactoría en la que se realiza la compra
     */
    public void logComprarPeces(String nombrePez, boolean tipoSex, int tanque, String piscifactoria){
        write(nombrePez + (tipoSex ? " (M)" : " (H)") + " comprado. Añadido al tanque " + tanque + " de la piscifactoria " + piscifactoria );
    }

    /**
     * Registra la venta de peces en el archivo de log.
     * 
     * @param peces Cantidad de peces vendidos  
     * @param piscifactoria Piscifactoría en la que se realiza la venta
     */
    public void logVenderPeces(int peces, String piscifactoria){
        write("Vendidos "+peces + " peces de la piscifactoria "+ piscifactoria +" de forma manual");
    }

    /**
     * Registra la limpieza de un tanque en el archivo de log.
     * 
     * @param tanque tanque en el que se realiza la limpieza 
     * @param piscifactoria piscifactoría en la que se realiza la limpieza
     */
    public void logLimpiarTanque(int tanque, String piscifactoria){
        write("Limpiado el tanque " + tanque + " de la piscifactoria " + piscifactoria);
    }

    /**
     * Registra el vaciado de un tanque en el archivo de log.
     * 
     * @param tanque tanque en el que se realiza el vaciado
     * @param piscifactoria piscifactoría en la que se realiza el vaciado
     */
    public void logVaciarTanque(int tanque, String piscifactoria){
        write("Vaciado el tanque " + tanque + " de la piscifactoria " + piscifactoria);
    }

    /**
     * Registra la compra de un edificio en el archivo de log.
     * 
     * @param tipoEdificio tipo de edificio comprado
     * @param piscifactoria piscifactoría en la que se realiza la compra
     */
    public void logCompraEdificio(String tipoEdificio, String piscifactoria){
        switch (tipoEdificio) {
            case "tanque":
                write("Comprado un tanque para la piscifactoria " + piscifactoria);
                break;
            case "almacen":
                write("Comprado el almacén cenrtal");
                break;
            case "mar":
                write("Comprado la piscifactoria de mar " + piscifactoria);
            default:
                write("Comprado la piscifactoria de rio "+ piscifactoria);
                break;
        }
    }

    /**
     * Registra la mejora de un edificio en el archivo de log.
     * 
     * @param piscifactoria piscifactoría en la que se realiza la mejora
     */
    public void logMejorarEdificio(String piscifactoria){
        write("Mejorada la piscifactoria " + piscifactoria + " aumentando su capacidad de comida");
    }

    /**
     * Registra el paso al siguiente día en el archivo de log.
     * 
     * @param day
     */
    public void logNextDay(int day){
        write("Fin del día " + day + ".");
    }

    /**
     * Registra las operaciones ocultas en el archivo de log.
     * 
     * @param codOp Código de la operación oculta
     * @param piscifactoria piscifactoría en la que se realiza la operación
     */
    public void logOpsOcultas(int codOp, String piscifactoria){
        switch (codOp) {
            case 98:
                write("Añadidos peces mediante la opcion oculta a la piscifactoria " + piscifactoria);
                break;
            case 99:  
                write("Añadidas monedas mediante la opcion oculta");          
                break;
            default:
                break;
        }
    }

    /**
     * Registra el cierre de la partida en el archivo de log.
     */
    public void logSalir(){
        write("Cierre de la partida");
    }

    /**
     * Escribe un mensaje de error en el archivo de log de errores.
     * 
     * @param mensaje Mensaje de error a registrar.
     */
    public void logError(String mensaje) {
        try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter(ERROR_LOG_FILE, true))) {
            errorWriter.write("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] " + mensaje);
            errorWriter.newLine();
        } catch (IOException e) {
            System.out.println("Error crítico al registrar errores: " + e.getMessage());
        }
    }

    /**
     * Cierra el archivo de log liberando los recursos.
     */
    public void logClose() {
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo de logs: " + e.getMessage());
            }
        }
    }
}
