package peces.especies;

import peces.IMar;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Caballa
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */

public class Caballa extends AlimentacionCarnivoro implements IMar {

    /**
     * Constructor de Caballa 
     * @param sex Sexo del pez 
     */
    public Caballa (boolean sex){
        super(AlmacenPropiedades.CABALLA, sex);

    }

    public Pez getInstance(){
        return new Caballa(sex);
    }

}
