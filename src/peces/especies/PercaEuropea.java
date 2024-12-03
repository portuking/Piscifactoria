package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoroActivo;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class PercaEuropea extends AlimentacionCarnivoroActivo {

    /**
     * Constructor de PercaEuropea
     * @param sex Sexo del pez
     */

     public PercaEuropea(boolean sex){
        super(AlmacenPropiedades.PERCA_EUROPEA, sex);
     }
     
     /**
      * 
      * @return Nueva instancia de Perca Europea
      */
     public Pez getInstance(){
      return new PercaEuropea(sex);
     }



    @Override
      public String toString() {
         return "===== Perca Europea =====\n" +
               "Especie: " + AlmacenPropiedades.PERCA_EUROPEA+ "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
}

}
