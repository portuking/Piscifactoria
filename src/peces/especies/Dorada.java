package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionOmnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Dorada
 * @autor Manuel Abalo Rietz
 * @autor Adrián Ces López
 * @autor Pablo Dopazo Suárez
 */

public class Dorada extends AlimentacionOmnivoro {
    /**
     * Constructor de Dorada
     * @param sex
     * */
    public Dorada(boolean sex) {
        super(AlmacenPropiedades.DORADA, sex);
    }

    /**
     * Método que crea un pez
     * @return Una cría de Dorada
     */
    @Override
    public Pez reproduce (boolean sex) {
        return new Dorada(sex);
    }

    @Override
    public String toString() {
        return "===== Estado del pez =====\n" +
            "Especie: " + AlmacenPropiedades.DORADA + "\n"+
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
