package peces.especies;

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

public class Pejerrey extends AlimentacionCarnivoro implements IRio{
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

}
