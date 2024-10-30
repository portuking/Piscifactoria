package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class Besugo extends AlimentacionCarnivoro{

    /**
     * Constructor de Besugo
     * @param sex Sexo del Pez
     */
    public Besugo(boolean sex) {
        super(AlmacenPropiedades.BESUGO, sex);
    }

    /**
     * @return Nueva instancia de Besugo
     */
    public Pez getInstance() {
        return new Besugo(sex);
    }



}
