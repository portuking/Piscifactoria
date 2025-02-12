package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoroActivo;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Lucio del Norte
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class LucioDelNorte extends AlimentacionCarnivoroActivo{
    /**
     * Constructor de Lucio del Norte
     * @param sex Sexo del Pez
     */
    public LucioDelNorte(boolean sex) {
        super(AlmacenPropiedades.LUCIO_NORTE, sex);
    }

    /**
     * Método que crea un pez
     * @return Una cría de Lucio del Norte
     */
    @Override
    public Pez reproduce(boolean sex) {
        return new LucioDelNorte(sex);
    }

     @Override
     public String toString() {
         return "===== Estado del pez =====\n" +
             "Especie: " + AlmacenPropiedades.LUCIO_NORTE + "\n"+
             "Nombre comun: " + name + "\n" +
             "Nombre cientifico: " + scientifcName + "\n" +
             "Tipo de pez: " + fishStats.getTipo() + "\n" +
             "Costo de compra: " + fishStats.getCoste() + " monedas\n" +
             "Monedas al vender: " + fishStats.getMonedas() + " monedas\n" +
             "Edad: " + age + " dias\n" +
             "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
             "Estado:\n" +
             "  - Vivo: " + (alive ? "Si" : "No") + "\n" +
             "  - Alimentado: " + (eat ? "Si" : "No") + "\n" +
             "  - Maduro: " + (mature ? "Si" : "No") + "\n" +
             "  - Fertil: " + (fertile ? "Si" : "No") + "\n" +
             "Ciclo de reproduccion: " + fishStats.getCiclo() + " dias\n" +
             "Huevos por reproduccion: " + fishStats.getHuevos() + "\n" +
             "Madurez alcanzada en: " + fishStats.getMadurez() + " dias\n" +
             "Optimo para venta en: " + fishStats.getOptimo() + " dias\n" +
             "Piscifactoria compatible: " + fishStats.getPiscifactoria() + "\n" +
             "===========================";
     }
}
