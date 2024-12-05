package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Salmon Chinook
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class SalmonChinook extends AlimentacionCarnivoro{
    /**
     * Constructor de Salmon Chinook
     * @param sex
     */
    public SalmonChinook( boolean sex) {
        super(AlmacenPropiedades.SALMON_CHINOOK, sex);
        
    }

    /**
     * @return Nueva instancia de Salmón chinook
    */

    public Pez getInstance(){
        return new SalmonChinook(sex);
    }


    @Override
    public String toString() {
        return "===== Estado del pez =====\n" +
            "Especie: " + AlmacenPropiedades.SALMON_CHINOOK + "\n"+
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
