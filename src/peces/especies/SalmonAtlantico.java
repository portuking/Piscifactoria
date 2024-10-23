package peces.especies;

import peces.IMar;
import peces.IRio;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class SalmonAtlantico extends AlimentacionCarnivoro implements IMar, IRio{

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


}
