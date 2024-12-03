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

public class SalmonAtlantico extends AlimentacionCarnivoro{

    /**
     * Constructor de Salmon Atlantico
     * @param sex
     */
    public SalmonAtlantico(boolean sex) {
        super(AlmacenPropiedades.SALMON_ATLANTICO, sex);
       
    }


    /**
     * @return nueva instancia de Salmon Atlantico
     */
        public Pez getInstance(){
            return new SalmonAtlantico(sex);
        }



    @Override
    public String toString() {
        return "===== Salmon Atlantico =====\n" +
               "Especie: " + AlmacenPropiedades.SALMON_ATLANTICO + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }


}
