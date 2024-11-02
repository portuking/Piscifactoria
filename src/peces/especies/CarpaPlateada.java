package peces.especies;

import peces.IRio;
import peces.Pez;
import peces.alimentacion.AlimentacionFiltrador;
import propiedades.AlmacenPropiedades;


/**
 * Clase que representa una Carpa Plateada
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */

public class CarpaPlateada extends AlimentacionFiltrador implements IRio {

    /**
     * Constructor de una Carpa Plateada
     * @param sex Sexo del Pez
     */

    public CarpaPlateada(boolean sex) {
        super(AlmacenPropiedades.CARPA_PLATEADA,  sex);

       
    }

    /**
     * @return Nueva instancia de Carpa Plateada
     *
     */

     public Pez getInstance(){
        return new CarpaPlateada(sex);
     }

    
}
