package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Lubina Europea
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 
public class LubinaEuropea extends AlimentacionCarnivoro{
    /**
     * Constructor de Lubina Europea
     * @param sex sexo del pez
     */
    public LubinaEuropea(boolean sex) {
        super(AlmacenPropiedades.LUBINA_EUROPEA, sex);
        
    }
    /**
     * 
     * @return Nueva instancia de Lubina Europea
     */
    public Pez getInstance(){
        return new LubinaEuropea(sex);
    }


    @Override
    public String toString() {
        return "===== Lubina Europea =====\n" +
               "Especie: " + AlmacenPropiedades.LUBINA_EUROPEA + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }

}
