package registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import edificios.piscifactoria.Piscifactoria;

/**
 * Clase para gestionar las transcripciones de eventos en un archivo.
 */
public class Transcripciones {
    private static Transcripciones instance = null;
    private static final String PATH_TRANS = "transcripciones";
    private BufferedWriter bw;
    private String rutaArchivo;

    /**
     * Constructor privado para evitar instancias múltiples.
     */
    private Transcripciones(String nombrePartida) {
        this.rutaArchivo = PATH_TRANS + "/" + nombrePartida + ".tr";

        // Crear archivo de transcripción si no existe
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo de transcripciones: " + e.getMessage());
            }
        }
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, true), "UTF-8"));
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo de transcripciones: " + e.getMessage());
        }
    }

    /**
     * Obtiene la única instancia de la clase, asegurando que solo haya una por partida.
     * 
     * @param nombrePartida Nombre de la partida para generar el archivo.
     * @return Instancia única de Transcripciones.
     */
    public static Transcripciones getInstance(String nombrePartida) {
        if (instance == null) {
            instance = new Transcripciones(nombrePartida);
        }
        return instance;
    }

    /**
     * Escribe una línea en el archivo de transcripciones.
     * 
     * @param linea Contenido de la línea a insertar.
     */
    public void escribir(String linea) {
        try {
            bw.append(linea);
            bw.flush();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de transcripciones: " + e.getMessage());
        }
    }

    /**
     * Registra el inicio de la partida junto con la informacion principal del sistema.
     * @param nombrePartida Nombre de la partida.
     * @param monedas Dinero inicial.
     * @param pecesImplementados Lista de peces implementados.
     * @param pisciInicial Piscifactoria inicial.
     */
    public void start(String nombrePartida, int monedas, String[] pecesImplementados, String pisciInicial ) {
        escribir("=========Arranque=========\nEmpieza el sistema "+nombrePartida+
        ".\n=========Dinero=========\nDinero: "+monedas+" monedas.\n=========Peces=========\nRio:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if(propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName().equals("Río")){
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("Mar:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if(propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName().equals("Mar")){
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("Río y mar:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if(propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName().equals("Río y mar")){
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("-------------------------------------------\n");
        escribir("Piscifactoria incial: "+pisciInicial+".\n");

        //TODO implementar EXTRAS
    }

    /**
     * Registra la informacion cuando compra la comida
     * @param cantidadComida Cantidad de comida comprada
     * @param tipoComida Tipo de comida comprada
     * @param monedasGastadas Monedas gastadas
     * @param almacenado Indica si se almacena en una piscifactoria o en el almacen central
     * @param piscifactoria Piscifactoria en la que se almacena
     */
    public void comprarComdida(int cantidadComida, String tipoComida, int monedasGastadas, String almacenado, Piscifactoria piscifactoria) { 
        escribir(cantidadComida + " de comida de tipo " + tipoComida + " comprado por " + monedasGastadas + " monedas. Se almacena en la piscifactoria " + almacenado);//PISCIFACTORIA O ALMACEN 
    }

    public void comprarPeces(){
        escribir("X (M/H) comprado por Y monedas. Añadido al tanque Y de la piscifactoría Z.");
    }

    public void venderPeces(){
        escribir("Vendidos X peces de la piscifactoría Y de forma manual por Z monedas.");
    }

    /**
     * Registra la informacion de la limpieza de un tanque.
     * @param tanque tanque donde se va a limpiar
     * @param piscifactoria piscifactoria de donde es el tanque
     */
    public void limpiarTanque(int tanque , String piscifactoria){
       escribir("Limpiado el tanque "+ tanque + " de la piscifactoría " + piscifactoria + ".\n"); 
    }

    /**
     * Registra la informacion de vaciar un tanque.
     * @param tanque tanque que se va a vaciar
     * @param piscifactoria piscifactoria de donde es el tanque
     */
    public void vaciarTanque(int tanque , String piscifactoria){
        escribir("Limpiado el tanque "+ tanque + " de la piscifactoría " + piscifactoria + ".\n"); 
    }

    public void compraEdificio(){
        escribir("Comprada la piscifactoría de río/mar X por Y monedas.\r\n" + //
                        "Comprado un tanque número X de la piscifactoría Y.\r\n" + //
                        "Comprado el almacén central");
    }

    public void mejoraEdificio(){
        escribir("Mejorada la piscifactoría X aumentando su capacidad de comida hasta un total de Y por Z\r\n" + //
                        "monedas");
    }

    public void nextDay(){
        escribir("Fin del día X.\r\n" + //
                        "Peces actuales, X de río Y de mar.\r\n" + //
                        "X monedas ganadas por un total de Y.");
                        //TODO mirar la documentacion del proyecto no sAe que movie con las >>> al pasar de dio
    }

    public void opsOcultas(){
        escribir("Añadidos peces mediante la opción oculta a la piscifactoría X.\r\n" + //
                        "Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, X.");
    }

    /**
     * Cierra el BufferedWriter al final de la simulación.
     */
    public void cerrar() {
        try {
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo de transcripciones: " + e.getMessage());
        }
    }
}

