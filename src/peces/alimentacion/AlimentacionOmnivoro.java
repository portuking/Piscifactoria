package peces.alimentacion;

import peces.Pez;
import java.util.Random;
import propiedades.PecesDatos;

/**
 * Clase que representa la alimentación omnívora de un pez
 * Se pueden alimentar de comida vegetal y animal. 25% de no consumir comida.
 * @autor Manuel Abalo Rietz
 * @autor Adrián Ces López
 * @autor Pablo Dopazo Suárez
 */
public abstract class AlimentacionOmnivoro extends Pez {

    /**
     * Constructor para crear un nuevo pez omnívoro.
     * @param fishStats Objeto de tipo PecesDatos que contiene las estadísticas del pez.
     * @param sex booleano que indica el sexo del pez (true si es macho, false si es hembra).
     */
    public AlimentacionOmnivoro(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    /**
     * Método que crea un nuevo pez
     * @return Una cría de pez
     */
    @Override
    public abstract Pez reproduce(boolean sex);

    /**
     * Método que simula la acción de comer del pez omnívoro.
     * @return La cantidad de comida consumida (0, 1 o 2 si ha comido).
     */
    @Override
    public int eat() {
        int comidaConsumida = 0;
        int probabilidad = 0;
        if (this.isAlive() && !this.isEat()) {
            Random r = new Random();
            probabilidad = r.nextInt(4); 
            if(probabilidad < 4) {
                comidaConsumida = 1;
            }else{
                comidaConsumida = 0;
            }
        }
        return comidaConsumida;
    }
}