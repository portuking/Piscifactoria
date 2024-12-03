package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Caballa
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */

public class Caballa extends AlimentacionCarnivoro{

    /**
     * Constructor de Caballa 
     * @param sex Sexo del pez 
     */
    public Caballa (boolean sex){
        super(AlmacenPropiedades.CABALLA, sex);

    }
    /**
     * 
     * @return Nueva instancia de Caballa
     */
    public Pez getInstance(){
        return new Caballa(sex);
    }


    @Override
    public String toString() {
        return "===== Caballa =====\n" +
            "Especie: " + AlmacenPropiedades.CABALLA + "\n" +
            "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
            "===================";
    }

}
