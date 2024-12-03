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

public class Robalo extends AlimentacionCarnivoro {

/**
 * Constructor de Robalo
 * @param sex
 */
    public Robalo(boolean sex) {
        super(AlmacenPropiedades.ROBALO, sex);

    }


    /**
     * @return Nueva instancia de Robalo
     */

     public Pez getInstance(){
        return new Robalo(sex);
     }


    @Override
    public String toString() {
        return "===== Robalo =====\n" +
               "Especie: " + AlmacenPropiedades.ROBALO + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }


}
