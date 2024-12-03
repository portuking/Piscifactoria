package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class Pejerrey extends AlimentacionCarnivoro {
    /**
     * Constructor de Pejerrey
     * @param sex sexo del Pez
     */
    public Pejerrey( boolean sex) {
        super(AlmacenPropiedades.PEJERREY, sex);
        
    }

    /**
     * @return Nueva instancia de Pejerrey
     */

     public Pez getInstance(){
        return new Pejerrey(sex);
     }


    @Override
    public String toString() {
        return "===== BPejerrey =====\n" +
               "Especie: " + AlmacenPropiedades.PEJERREY + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }

}
