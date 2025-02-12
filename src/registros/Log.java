package registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edificios.piscifactoria.Piscifactoria;

/**
 * Clase Log que implementa un sistema de registro de eventos en un archivo de logs.
 * Utiliza el patrón Singleton para asegurar que solo exista una instancia de la clase.
 */
public class Log {
    private static Log instance = null;
    private static final String PATH_LOG = "logs";
    private BufferedWriter bw;
    private String rutaArchivo;
    
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
    public void start(String nombrePartida, String pisciInicial) {
        write("Inicio de la partida: " + nombrePartida + ".");
        write("Piscifactoría inicial: " + pisciInicial + ".");
    }

    public void comprarComida(int cantidadComida, String tipoComida, boolean almacen, String piscifactoria){
        write(cantidadComida + "de comida de tipo " + tipoComida + " comprada. Se almacena en" + (almacen ? "el almacén central" : "la piscifactoría " + piscifactoria + "."));
    }

    public void comprarPeces(String nombrePez, boolean tipoSex, int tanque, String piscifactoria){
        write(nombrePez + (tipoSex ? " (M)" : " (H)") + " comprado. Añadido al tanque " + tanque + " de la piscifactoria " + piscifactoria );
    }

    public void mejorarEdificio(){

    }

    /**
     * Cierra el archivo de log liberando los recursos.
     */
    public void close() {
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo de logs: " + e.getMessage());
            }
        }
    }
}
