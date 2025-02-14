package recompensas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class GestorRecompensas {

    private static final String PATH_RECOMPENSAS = "rewards";

    /**
     * Recorre todos los archivos XML en la carpeta rewards, disminuye en 1 el valor de <quantity> 
     * y elimina el archivo si el valor es menor que 1.
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
            //errores.insertar("Error al reducir la cantidad de la recompensa");
        }catch (DocumentException e) {
            //errores.insertar("Error al leer el documento XML");
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
