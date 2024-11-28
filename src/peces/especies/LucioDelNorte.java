package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoroActivo;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Lucio del Norte
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class LucioDelNorte extends AlimentacionCarnivoroActivo{
    /**
     * Constructor de Lucio del Norte
     * @param sex Sexo del Pez
     */
    public LucioDelNorte(boolean sex) {
        super(AlmacenPropiedades.LUCIO_NORTE, sex);
    }

    /**
     * @return Nueva instancia de Lucio del Norte
     */

     public Pez getInstance(){
        return new LucioDelNorte (sex);

     }
}
