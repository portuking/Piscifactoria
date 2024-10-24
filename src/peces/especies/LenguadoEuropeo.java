package peces.especies;

import peces.IMar;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Lenguado Europeo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class LenguadoEuropeo extends AlimentacionCarnivoro implements IMar{
    /**
     * Constructor de Lenguado Europeo
     * @param sex Sexo del Pez
     */

    public LenguadoEuropeo(boolean sex) {
        super(AlmacenPropiedades.LENGUADO_EUROPEO, sex);
       
    }
    
    /**
     * @return Nueva instancia de Lenguado Europeo
     */

     public Pez getInstance(){
        return new LenguadoEuropeo(sex);
     }
}
