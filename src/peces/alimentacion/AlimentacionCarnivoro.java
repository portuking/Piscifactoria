package peces.alimentacion;

import peces.Pez;
import propiedades.PecesDatos;

/**
 * Clase que representa la alimentación carnívora de un pez
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class AlimentacionCarnivoro extends Pez {

    /**
     * Constructor para crear un nuevo pez carnívoro.
     *
     * @param fishStats Objeto de tipo PecesDatos que contiene las estadísticas del pez.
     * @param sex       booleano que indica el sexo del pez (true si es macho, false si es hembra).
     */
    public AlimentacionCarnivoro(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

     /**
     * Método que simula la acción de comer del pez carnívoro.
     *
     * @return La cantidad de comida consumida (1 si ha comido).
     */
    @Override
    public int eat() {
        int comidaConsumida = 0;
        if (this.isAlive() && !this.isEat()) {
            this.eat = true;
            comidaConsumida += 1;
        }
        return comidaConsumida;
    }

    /**
     * Método que crea un nuevo pez. Este método no está implementado y lanzará una excepción.
     *
     * @return Un nuevo objeto de tipo Pez (no implementado).
     * @throws UnsupportedOperationException Si se llama a este método.
     */
    @Override
    public Pez getNewFish(boolean sex) {
        throw new UnsupportedOperationException("Método sin implementar");
    }

}
