package peces.especies;

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

}
