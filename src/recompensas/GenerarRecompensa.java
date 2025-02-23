package recompensas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import registros.Registros;

/**
 * Clase que genera recompensas en formato XML.
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class GenerarRecompensa {

    private static final String PATH_RECOMPENSAS = "rewards";
    /**
     * Genera un archivo XML con la estructura de una recompensa.
     * @return El archivo XML generado.
     */
    public Document generarArchivoXML(){
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("reward");
        return doc;
    }

    /**
     * Genera una recompensa de alga.
     * @param lvl Nivel de la recompensa.
     */
    public void algaReward(int lvl){
        String rutaArchivo = PATH_RECOMPENSAS + "/alga_" + lvl + ".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        String lvlreward="";
        int food=0;
        int rarity=0;
        switch(lvl){
            case 1:
                lvlreward = "I";
                food = 100;
                rarity = 0;
                break;
            case 2:
                lvlreward = "II";
                food = 200;
                rarity = 1;
                break;
            case 3:
                lvlreward = "III";
                food = 500;
                rarity = 2;
                break;
            case 4:
                lvlreward = "IV";
                food = 1000;
                rarity = 3;
                break;
            case 5:
                lvlreward = "V";
                food = 5000;
                rarity = 4;
                break;
        }
        root.addElement("name").addText("Alga " + lvlreward);
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText(food + " que se puede usar para alimentar a los peces.");
        root.addElement("rarity").addText(Integer.toString(rarity));
        Element give = root.addElement("give");
        Element comida = give.addElement("food").addText(Integer.toString(food));
        comida.addAttribute("type", "algae");
        root.addElement("quantity").addText(Integer.toString(1));        
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de pienso.
     * @param lvl Nivel de la recompensa.
     */
    public void piensoReward(int lvl){
        String rutaArchivo = PATH_RECOMPENSAS + "/pienso_" + lvl + ".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        String lvlreward="";
        int food=0;
        int rarity=0;
        switch(lvl){
            case 1:
                lvlreward = "I";
                food = 100;
                rarity = 0;
                break;
            case 2:
                lvlreward = "II";
                food = 200;
                rarity = 1;
                break;
            case 3:
                lvlreward = "III";
                food = 500;
                rarity = 2;
                break;
            case 4:
                lvlreward = "IV";
                food = 1000;
                rarity = 3;
                break;
            case 5:
                lvlreward = "V";
                food = 2000;
                rarity = 4;
                break;
        }
        root.addElement("name").addText("Pienso de peces " + lvlreward);
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText(food + " unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
        root.addElement("rarity").addText(Integer.toString(rarity));
        Element give = root.addElement("give");
        Element comida = give.addElement("food").addText(Integer.toString(food));
        comida.addAttribute("type", "animal");
        root.addElement("quantity").addText(Integer.toString(1));        
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de monedas.
     * @param lvl Nivel de la recompensa.
     */
    public void BolivaresReward(int lvl){
        String rutaArchivo = PATH_RECOMPENSAS + "/monedas_" + lvl + ".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        String lvlreward="";
        int bolivares=0;
        int rarity=0;
        switch(lvl){
            case 1:
                lvlreward = "I";
                bolivares = 100;
                rarity = 0;
                break;
            case 2:
                lvlreward = "II";
                bolivares = 350;
                rarity = 1;
                break;
            case 3:
                lvlreward = "III";
                bolivares = 500;
                rarity = 2;
                break;
            case 4:
                lvlreward = "IV";
                bolivares = 750;
                rarity = 3;
                break;
            case 5:
                lvlreward = "V";
                bolivares = 1000;
                rarity = 4;
                break;
        }
        root.addElement("name").addText("Monedas " + lvlreward);
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText(bolivares + " monedas.");
        root.addElement("rarity").addText(Integer.toString(rarity));
        Element give = root.addElement("give");
        give.addElement("coins").addText(Integer.toString(bolivares));
        root.addElement("quantity").addText(Integer.toString(1));        
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de comida.
     * @param lvl Nivel de la recompensa.
     */
    public void comidaReward(int lvl){
        String rutaArchivo = PATH_RECOMPENSAS + "/comida_" + lvl + ".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        String lvlreward="";
        int food=0;
        int rarity=0;
        switch(lvl){
            case 1:
                lvlreward = "I";
                food = 50;
                rarity = 0;
                break;
            case 2:
                lvlreward = "II";
                food = 100;
                rarity = 1;
                break;
            case 3:
                lvlreward = "III";
                food = 250;
                rarity = 2;
                break;
            case 4:
                lvlreward = "IV";
                food = 500;
                rarity = 3;
                break;
            case 5:
                lvlreward = "V";
                food = 1000;
                rarity = 4;
                break;
        }
        root.addElement("name").addText("Comida general " + lvlreward);
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText(food + " unidades de pienso multipropósito para todo tipo de peces.");
        root.addElement("rarity").addText(Integer.toString(rarity));
        Element give = root.addElement("give");
        Element comida = give.addElement("food").addText(Integer.toString(food));
        comida.addAttribute("type", "general");
        root.addElement("quantity").addText(Integer.toString(1));        
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de tanque.
     * @param tipo Tipo de tanque (río o mar).
     */
    public void tanqueReward(String tipo) {
        String part = "A";
        String nombreTanque;
        String codigoBuilding;
        String rarity;
        String rutaArchivo;
        
        if (tipo.equalsIgnoreCase("r")) {
            nombreTanque = "Tanque de río";
            codigoBuilding = "2";
            rarity = "2";
            rutaArchivo = PATH_RECOMPENSAS + "/tanque_r.xml";
        } else if (tipo.equalsIgnoreCase("m")) {
            nombreTanque = "Tanque de mar";
            codigoBuilding = "3";
            rarity = "3";
            rutaArchivo = PATH_RECOMPENSAS + "/tanque_m.xml";
        } else {
            System.err.println("Tipo de tanque desconocido: " + tipo);
            return;
        }
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        root.addElement("name").addText(nombreTanque);
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText("Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de " + (tipo.equalsIgnoreCase("r") ? "río" : "mar") + ".");
        root.addElement("rarity").addText(rarity);
        Element give = root.addElement("give");
        Element building = give.addElement("building").addText(nombreTanque);
        building.addAttribute("code", codigoBuilding);
        give.addElement("part").addText(part);
        give.addElement("total").addText(part);
        root.addElement("quantity").addText("1");
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de piscifactoría de mar.
     * @param tipo Tipo de piscifactoría (A/B).
     */
    public void pisciMarReward(String tipo){
        String rutaArchivo = PATH_RECOMPENSAS + "/pisci_m_"+ tipo.toLowerCase()+".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        root.addElement("name").addText("Piscifactoría de mar [" + tipo +"]");
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText("Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.");
        root.addElement("rarity").addText("4");
        Element give = root.addElement("give");
        Element building = give.addElement("building").addText("Piscifactoría de mar");
        building.addAttribute("code", "1");
        give.addElement("part").addText(tipo);
        give.addElement("total").addText("AB");
        root.addElement("quantity").addText("1");
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de piscifactoría de río.
     * @param tipo Tipo de piscifactoría (A/B).
     */
    public void pisciRioReward(String tipo){
        String rutaArchivo = PATH_RECOMPENSAS + "/pisci_r_"+ tipo.toLowerCase()+".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        root.addElement("name").addText("Piscifactoría de rio [" + tipo +"]");
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText("Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.");
        root.addElement("rarity").addText("3");
        Element give = root.addElement("give");
        Element building = give.addElement("building").addText("Piscifactoría de río");
        building.addAttribute("code", "0");
        give.addElement("part").addText(tipo);
        give.addElement("total").addText("AB");
        root.addElement("quantity").addText("1");
        guardarXML(doc, rutaArchivo);
    }

    /**
     * Genera una recompensa de almacen central.
     * @param tipo Tipo de almacén (A/B/C/D).
     */
    public void almacenReward(String tipo){
        String rutaArchivo = PATH_RECOMPENSAS + "/almacen_"+ tipo.toLowerCase()+".xml";
        File file = new File(rutaArchivo);
        if (file.exists()) {
            aumentarCantidad(file);
            return;
        }
        Document doc = generarArchivoXML();
        Element root = doc.getRootElement();
        root.addElement("name").addText("Almacén central [" + tipo +"]");
        root.addElement("origin").addText("Adrián_PablilloPitillo_ManolilloPorrito");
        root.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
        root.addElement("rarity").addText("1");
        Element give = root.addElement("give");
        Element building = give.addElement("building").addText("Almacén central");
        building.addAttribute("code", "4");
        give.addElement("part").addText(tipo);
        give.addElement("total").addText("ABCD");
        root.addElement("quantity").addText("1");
        guardarXML(doc, rutaArchivo);
    }
    
    /**
     * Método que aumenta la Cantidad de Piezas de la recompensa
     * @param archivo archivo con la recompensa
     */
    private void aumentarCantidad(File archivo){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(archivo);
            Element root = doc.getRootElement();
            Element quantity = root.element("quantity");
            int cantidad = Integer.parseInt(quantity.getText());
            cantidad++;
            quantity.setText(Integer.toString(cantidad));
            guardarXML(doc, archivo.getAbsolutePath());
        } catch (DocumentException e) {
            System.err.println("Error al leer el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Guarda un archivo XML en disco.
     * @param doc Documento XML.
     * @param rutaArchivo Ruta del archivo a guardar.
     */
    public void guardarXML(Document doc, String rutaArchivo) {
    XMLWriter writer = null;
    try {
        writer = new XMLWriter(new FileWriter(rutaArchivo), OutputFormat.createPrettyPrint());
        writer.write(doc); 
        writer.flush();     
        System.out.println("Recompensa generada en: " + rutaArchivo);
        Registros.registrarCreaRecompensa(doc.getName());
    } catch (IOException e) {
        System.err.println("Error al guardar el archivo XML: " + e.getMessage());
        e.printStackTrace();
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
