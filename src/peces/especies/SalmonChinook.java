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
        return "===== Salmon Chinook =====\n" +
               "Especie: " + AlmacenPropiedades.SALMON_CHINOOK + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }
}
