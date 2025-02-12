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
     * Obtiene la única instancia de la clase, asegurando que solo haya una por
     * partida.
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
     * Registra el inicio de la partida junto con la informacion principal del
     * sistema.
     * 
     * @param nombrePartida      Nombre de la partida.
     * @param monedas            Dinero inicial.
     * @param pecesImplementados Lista de peces implementados.
     * @param pisciInicial       Piscifactoria inicial.
     */
    public void start(String nombrePartida, int monedas, String[] pecesImplementados, String pisciInicial) {
        escribir("=========Arranque=========\nEmpieza el sistema " + nombrePartida +
                ".\n=========Dinero=========\nDinero: " + monedas + " monedas.\n=========Peces=========\nRio:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if (propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName()
                    .equals("Río")) {
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("Mar:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if (propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName()
                    .equals("Mar")) {
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("Río y mar:\n");
        for (int i = 0; i < pecesImplementados.length; i++) {
            if (propiedades.AlmacenPropiedades.getPropByName(pecesImplementados[i]).getPiscifactoria().getName()
                    .equals("Río y mar")) {
                escribir("-" + pecesImplementados[i] + "\n");
            }
        }
        escribir("-------------------------------------------\n");
        escribir("Piscifactoria incial: " + pisciInicial + ".\n");

        // TODO implementar EXTRAS
    }

    /**
     * Registra la informacion cuando compra la comida
     * 
     * @param cantidadComida  Cantidad de comida comprada
     * @param tipoComida      Tipo de comida comprada
     * @param monedasGastadas Monedas gastadas
     * @param almacenado      Indica si se almacena en una piscifactoria o en el
     *                        almacen central
     * @param piscifactoria   Piscifactoria en la que se almacena
     */
    public void comprarComdida(int cantidadComida, String tipoComida, int monedasGastadas, boolean almacen, String piscifactoria) {//creo que hecho
        escribir(cantidadComida + " de comida de tipo " + tipoComida + " comprada por " + monedasGastadas+ " monedas. Se almacena en " + (almacen ? "el almacén central" : "la piscifactoría " + piscifactoria + ".\n"));// PISCIFACTORIA O ALMACEN
    }

    public void comprarPeces(String nombrePez, boolean tipoSex, int monedas, int tanque, String piscifactoria) {//hecho
        escribir(nombrePez + (tipoSex ? " (M)" : " (H)") + " comprado por "+monedas+" monedas. Añadido al tanque " + tanque+ " de la piscifactoría " +piscifactoria+" .\n");
    }

    public void venderPeces() {//????
        escribir("Vendidos X peces de la piscifactoría Y de forma manual por Z monedas.");
    }

    /**
     * Registra la informacion de la limpieza de un tanque.
     * 
     * @param tanque        tanque donde se va a limpiar
     * @param piscifactoria piscifactoria de donde es el tanque
     */
    public void limpiarTanque(int tanque, String piscifactoria) {//hecho
        escribir("Limpiado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".\n");
    }

    /**
     * Registra la informacion de vaciar un tanque.
     * 
     * @param tanque        tanque que se va a vaciar
     * @param piscifactoria piscifactoria de donde es el tanque
     */
    public void vaciarTanque(int tanque, String piscifactoria) {//hecho
        escribir("Limpiado el tanque " + tanque + " de la piscifactoría " + piscifactoria + ".\n");
    }
    
    /**Creo que hecho*/
    public void compraEdificio(String tipoEdificio, int monedas, int tanque, String piscifactoria) {
        switch (tipoEdificio) {
            case "tanque":
                escribir("Comprado un tanque número " + tanque + " de la piscifactoría " + piscifactoria + ".\n");
                break;
            case "almacen":
                escribir("Comprado el almacén central.\n");
                break;
            case "mar":
                escribir("Comprada la piscifactoría de río " + piscifactoria +" por "+monedas+" monedas.\n");
                break;
            default:
                escribir("Comprada la piscifactoría de río " + piscifactoria +" por "+monedas+" monedas.\n"); //
                break;
        }
    }

    public void mejoraEdificio(String piscifactoria, int aumentoComida, int monedas) {//hecho
        escribir("Mejorada la piscifactoría "+piscifactoria+" aumentando su capacidad de comida hasta un total de "+aumentoComida+" por " +monedas+
                "monedas\n");
    }
    /*FALTA IMPLEMENTAR */
    public void nextDay(int dia, int pecesRio, int pecesMar, int monedas, int pecesVendidos) {
        escribir("Fin del día " + dia + ".\n" +
                "Peces actuales , " + pecesRio + " de río " + pecesMar + "de mar.\n" +
                monedas + "monedas ganadas por un total de " + pecesVendidos + ".\n" +
                "-------------------------\n>>>Inicio del dia " + (dia + 1) + ".\n");
    }
    /**
     * Registra la información cuando utilizas las opciones ocultas en el simulador
     * 
     * @param codOp Códigos ocultos utilizados en el simulador
     * @param piscifactoria Nombre de la piscifactoria  
     * @param cantidadDolares Cantidad de monedas utilizadas
     */
    public void opsOcultas(int codOp, String piscifactoria, int cantidadDolares) {//hecho
        switch (codOp) {
            case 98:
                escribir("Añadidos peces mediante la opción oculta a la piscifactoría " + piscifactoria + ".\n");
                break;
            case 99:
                escribir("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, " + cantidadDolares
                        + ".\n");
                break;
        }
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
