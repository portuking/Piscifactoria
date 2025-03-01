package peces.alimentacion;

import java.util.Random;
import peces.Pez;
import propiedades.PecesDatos;

/**
 * Clase que representa la alimentación carnívora activa de un pez
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public abstract class AlimentacionCarnivoroActivo extends Pez {

    /**
     * Constructor para crear un nuevo pez carnívoro activo.
     * @param fishStats Objeto de tipo PecesDatos que contiene las estadísticas del pez.
     * @param sex booleano que indica el sexo del pez (true si es macho, false si es hembra).
     */
    public AlimentacionCarnivoroActivo(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    /**
     * Método que crea un nuevo pez
     * @return Una cría de Pez
     */
    @Override
    public abstract Pez reproduce(boolean sex);

    /**
     * Método que simula la acción de comer del pez carnívoro activo.
     * @return La cantidad de comida consumida (1 o 2 si ha comido).
     */
    @Override
    public int eat() {
        int comidaConsumida = 0;
        if (this.isAlive() && !this.isEat()) {
            Random r = new Random();
            if (r.nextBoolean()) {
                comidaConsumida = 2;
            } else {
                comidaConsumida = 1;
            }
        }
        return comidaConsumida;
    }

}
