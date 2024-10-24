package peces.especies;

import peces.IRio;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Salmon Chinook
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class SalmonChinook extends AlimentacionCarnivoro implements IRio{
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
}
