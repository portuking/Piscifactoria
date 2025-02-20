package recompensas;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import edificios.almacenes.AlmacenCentral;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaMar;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
import registros.Registros;
import sistema.Helper;
import sistema.SISMonedas;
import sistema.Simulador;

public class GestorRecompensas {

    private static final String PATH_RECOMPENSAS = "rewards";

    /**
     * Recorre todos los archivos XML en la carpeta rewards, disminuye en 1 el valor
     * de <quantity>
     * y elimina el archivo si el valor es menor que 1.
     * 
     * @param nombreFichero Nombre del fichero XML a reducir.
     */
    private void reducirCantidad(String nombreFichero) {
        SAXReader reader = new SAXReader();
        File f = new File(PATH_RECOMPENSAS + "/" + nombreFichero);
        try {
            Document existingDocument = reader.read(f);
            Element existingRoot = existingDocument.getRootElement();
            for (Iterator<Element> it = existingRoot.elementIterator("quantity"); it.hasNext();) {
                Element existingQuantity = it.next();
                int existingValue = Integer.parseInt(existingQuantity.getText());
                if (--existingValue == 0) {
                    f.delete();
                } else {
                    existingQuantity.setText(String.valueOf(existingValue));
                    guardarXML(existingDocument, f);
                }
            }
        } catch (IOException e) {
            // TODO
        } catch (DocumentException e) {
            // TODO
        }
    }

    /**
     * Lee todos los archivos XML en la carpeta de recompensas y
     * construye una lista con el nombre que se mostrará en el menú.
     * Para las recompensas multipartidas se agrupan las partes y se
     * indica con una "x" si falta alguna.
     */
    public List<String> obtenerMenuRewards() {
        List<String> menuFinal = new ArrayList<>();
        File carpeta = new File(PATH_RECOMPENSAS);
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.err.println("El directorio de recompensas no existe.");
            return menuFinal;
        }

        File[] archivos = carpeta.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay recompensas disponibles.");
            return menuFinal;
        }

        List<String> recompensasSimples = new ArrayList<>();
        Map<String, Set<String>> recompensasMulti = new HashMap<>();

        SAXReader reader = new SAXReader();
        for (File archivo : archivos) {
            try {
                Document doc = reader.read(archivo);
                Element root = doc.getRootElement();
                String nombre = root.elementText("name");
                if (nombre == null) {
                    continue;
                }
                if (nombre.contains("[")) {
                    int inicio = nombre.indexOf("[");
                    int fin = nombre.indexOf("]");
                    if (inicio != -1 && fin != -1 && fin > inicio) {
                        String base = nombre.substring(0, inicio).trim(); // Ej.: "Almacén central"
                        String parte = nombre.substring(inicio + 1, fin).trim().toUpperCase(); // Ej.: "A"
                        recompensasMulti.computeIfAbsent(base, k -> new HashSet<>()).add(parte);
                    }
                } else {
                    recompensasSimples.add(nombre.trim());
                }
            } catch (DocumentException e) {
                System.err.println("Error al leer el archivo: " + archivo.getName());
                e.printStackTrace();
            }
        }
        Collections.sort(recompensasSimples);
        menuFinal.addAll(recompensasSimples);

        for (String base : recompensasMulti.keySet()) {
            Set<String> partesPresentes = recompensasMulti.get(base);
            String partesEsperadas = "";
            if (base.equalsIgnoreCase("Almacén central")) {
                partesEsperadas = "ABCD";
            } else if (base.toLowerCase().contains("piscifactoría de")) {
                partesEsperadas = "AB";
            } else {
                partesEsperadas = "";
            }
            StringBuilder partesMostrar = new StringBuilder();
            if (!partesEsperadas.isEmpty()) {
                for (int i = 0; i < partesEsperadas.length(); i++) {
                    String parte = String.valueOf(partesEsperadas.charAt(i));
                    if (partesPresentes.contains(parte)) {
                        partesMostrar.append(parte);
                    } else {
                        partesMostrar.append("x");
                    }
                }
            } else {
                List<String> listaPartes = new ArrayList<>(partesPresentes);
                Collections.sort(listaPartes);
                for (String p : listaPartes) {
                    partesMostrar.append(p);
                }
            }
            menuFinal.add(base + " [" + partesMostrar.toString() + "]");
        }
        Collections.sort(menuFinal);
        return menuFinal;
    }


    public void mostrarYCanjearReward(Simulador sim) {
        List<String> menuRewards = obtenerMenuRewards();
        if (menuRewards.isEmpty()) {
            System.out.println("No hay recompensas para canjear.");
            return;
        }
        String[] opciones = menuRewards.toArray(new String[0]);
        Helper helper = new Helper();
        int opcion = helper.mostrarMenu("Menú de Recompensas", opciones, null);
        if (opcion == opciones.length + 1) {
            System.out.println("Saliendo del menú de recompensas.");
            return;
        }
        String seleccion = opciones[opcion - 1];
        if (seleccion.contains("[") && seleccion.contains("x")) {
            System.out.println("No se puede canjear la recompensa '" + seleccion + "' porque le faltan partes.");
        } else {
            canjearReward(seleccion, sim);
        }
    }

    private void canjearReward(String seleccion, Simulador sim) {
        if (seleccion.contains("[")) {
            int indexBracket = seleccion.indexOf("[");
            String base = seleccion.substring(0, indexBracket).trim();
            String partesEsperadas = "";
            if (base.equalsIgnoreCase("Almacén central")) {
                partesEsperadas = "ABCD";
            } else if (base.toLowerCase().contains("piscifactoría de mar")) {
                partesEsperadas = "AB";
            } else if (base.toLowerCase().contains("piscifactoría de rio")) {
                partesEsperadas = "AB";
            } else {
                return;
            }
            boolean canjear = true;
            List<File> archivosAProcesar = new ArrayList<>();
            for (int i = 0; i < partesEsperadas.length(); i++) {// recorrer las partes esperadas
                String parte = String.valueOf(partesEsperadas.charAt(i));
                String nombreFichero = generarNombreFichero(base, parte);
                File f = new File(PATH_RECOMPENSAS + "/" + nombreFichero);
                if (!f.exists()) {
                    System.out.println("Falta la parte " + parte + " para la recompensa " + base);
                    canjear = false;
                    break;
                } else {
                    archivosAProcesar.add(f);
                }
            }
            if (canjear) {
                SAXReader reader = new SAXReader();
                for (File archivo : archivosAProcesar) {
                    try {
                        Document doc = reader.read(archivo);
                        procesarPremio(doc, sim);
                        reducirCantidad(archivo.getName());
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Recompensa '" + seleccion + "' canjeada exitosamente!");
                Registros.registraUsoRecompensa(seleccion);
            } else {
                System.out.println("No se puede canjear la recompensa multipartida '" + seleccion + "'.");
            }
        } else {// recompensa simple
            File carpeta = new File(PATH_RECOMPENSAS);
            File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            SAXReader reader = new SAXReader();
            boolean encontrado = false;
            for (File archivo : archivos) {
                try {
                    Document doc = reader.read(archivo);
                    String nombre = doc.getRootElement().elementText("name");
                    if (nombre != null && nombre.trim().equalsIgnoreCase(seleccion.trim())) {
                        procesarPremio(doc, sim);
                        reducirCantidad(archivo.getName());
                        encontrado = true;
                        System.out.println("Recompensa '" + seleccion + "' canjeada exitosamente!");
                        Registros.registraUsoRecompensa(seleccion);
                        break;
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró la recompensa '" + seleccion + "'.");
            }
        }
    }

    /**
     * Genera el nombre del fichero XML según el nombre base y la parte.
     *
     * @param base  Nombre base de la recompensa (por ejemplo, "Almacén central").
     * @param parte La parte de la recompensa (por ejemplo, "A").
     * @return El nombre del fichero correspondiente.
     */
    private String generarNombreFichero(String base, String parte) {
        String nombreFichero = "";
        if (base.equalsIgnoreCase("Almacén central")) {
            nombreFichero = "almacen_" + parte + ".xml";
        } else if (base.toLowerCase().contains("piscifactoría de mar")) {
            nombreFichero = "pisci_m_" + parte.toLowerCase() + ".xml";
        } else if (base.toLowerCase().contains("piscifactoría de rio")) {
            nombreFichero = "pisci_r_" + parte.toLowerCase() + ".xml";
        }
        return nombreFichero;
    }

    /**
     * Procesa el contenido de la recompensa en el XML y ejecuta la lógica de dar
     * recursos.
     */
    private void procesarPremio(Document doc, Simulador sim) {
        Element root = doc.getRootElement();
        Element give = root.element("give");

        if (give != null) {
            Element coinsElem = give.element("coins");
            if (coinsElem != null) {
                int coins = Integer.parseInt(coinsElem.getText());
                SISMonedas.getInstance().setMonedas(SISMonedas.getInstance().getMonedas() + coins);
                System.out.println("Se añaden " + coins + " monedas al jugador. Tienes: " + SISMonedas.getSaldo());
            }
            Element foodElem = give.element("food");
            if (foodElem != null) {
                int cantidadFood = Integer.parseInt(foodElem.getText());
                String tipoFood = foodElem.attributeValue("type");
                if (tipoFood != null) {
                    switch (tipoFood.toLowerCase()) {
                        case "algae":
                            
                            break;
                        case "animal":

                            break;
                        case "general":

                            break;
                        default:
                            System.out.println("Ni idea de ke comida es");
                            break;
                    }
                }
            }
            Element buildingElem = give.element("building");
            if (buildingElem != null) {
                String nombreBuilding = buildingElem.getText();
                String codigo = buildingElem.attributeValue("code");
                switch (codigo) {
                    case "4":
                        if(sim.getAlmacenCentral() != null){
                            AlmacenCentral almcen = new AlmacenCentral();
                            sim.setAlmacenCentral(almcen);
                        }
                        break;
                    case "1":
                        PiscifactoriaMar pisciMar = new PiscifactoriaMar(nombreBuilding);
                        sim.addPisci(pisciMar);
                        break;
                    case "0":
                        PiscifactoriaRio pisciRio = new PiscifactoriaRio(nombreBuilding);
                        sim.addPisci(pisciRio);
                        break;
                    case "3":
                        Piscifactoria pisciTanqueMar = sim.selectPisciMar();
                        Tanque tanqueMar = new Tanque(0, 0, false, pisciTanqueMar);
                        pisciTanqueMar.addTanque(tanqueMar);
                        break;
                    case "2":
                        Piscifactoria pisciTanqueRio = sim.selectPisciRio();
                        Tanque tanqueRio = new Tanque(0, 0, true, pisciTanqueRio);
                        pisciTanqueRio.addTanque(tanqueRio);
                        break;
                    default:
                        System.out.println("Edificio desconocido con código: " + codigo);
                        break;
                }
            } else {
                System.out.println("No se encontró el elemento 'building' en el XML.");
            }
        }
    }

    /**
     * Guarda el documento XML en el archivo indicado.
     *
     * @param doc     Documento XML a guardar.
     * @param archivo Archivo donde se guardará el documento.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private void guardarXML(Document doc, File archivo) throws IOException {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(archivo), OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.flush();
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el XMLWriter: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
