package peces.especies;

import peces.IMar;
import peces.IRio;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Lubina Europea
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 
public class LubinaEuropea extends AlimentacionCarnivoro implements IRio, IMar{
    /**
     * Constructor de Lubina Europea
     * @param sex sexo del pez
     */
    public LubinaEuropea(boolean sex) {
        super(AlmacenPropiedades.LUBINA_EUROPEA, sex);
        
    }

}
