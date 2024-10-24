package peces.especies;

import peces.IMar;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class Robalo extends AlimentacionCarnivoro implements IMar{

/**
 * Constructor de Robalo
 * @param sex
 */
    public Robalo(boolean sex) {
        super(AlmacenPropiedades.ROBALO, sex);

    }


    /**
     * @return Nueva instancia de Besugo
     */

     public Pez getInstance(){
        return new Besugo(sex);
     }


}
