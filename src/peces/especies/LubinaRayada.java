package peces.especies;

import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Lubina Rayada
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */ 

public class LubinaRayada extends AlimentacionCarnivoro{
    /**
     * Constructor de Lubina Rayada
     * @param sex Sexo del Pez
     */
    public LubinaRayada( boolean sex) {
        super(AlmacenPropiedades.LUBINA_RAYADA,  sex);
    
    

}
     /**
     * @return Nueva instancia de LUbina Rayada
     */

     public Pez getInstance(){
        return new LubinaRayada(sex);
     }


    @Override
    public String toString() {
        return "===== Lubina Rayada =====\n" +
               "Especie: " + AlmacenPropiedades.LUBINA_RAYADA + "\n" +
               "Sexo: " + (sex ? "Macho" : "Hembra") + "\n" +
               "===================";
    }

}
