package peces.alimentacion;

import peces.Pez;
import java.util.Random;
import propiedades.PecesDatos;

/**
 * Clase que representa la alimentación filtrador de un pez
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class AlimentacionFiltrador extends Pez {

    /**
     * Constructor para crear un nuevo pez filtrador.
     *
     * @param fishStats Objeto de tipo PecesDatos que contiene las estadísticas del pez.
     * @param sex       booleano que indica el sexo del pez (true si es macho, false si es hembra).
     */
    public AlimentacionFiltrador(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    /**
     * Método que crea un nuevo pez. Este método no está implementado y lanzará una excepción.
     *
     * @return Un nuevo objeto de tipo Pez (no implementado).
     * @throws UnsupportedOperationException Si se llama a este método.
     */
    @Override
    public Pez getNewFish(boolean sex) {
        throw new UnsupportedOperationException("Método no Implementado");
    }

    /**
     * Método que simula la acción de comer del pez filtrador.
     *
     * @return La cantidad de comida consumida (0 si no ha comido o 1 si ha comido).
     */
    @Override
    public int eat() {
        int comidaConsumida = 0;
        if (this.isAlive() && !this.isEat()) {
            this.eat = true;
            Random r = new Random();
            if (r.nextBoolean()) {
                comidaConsumida = 0;
            } else {
                comidaConsumida = 1;
            }
        }
        return comidaConsumida;
    }

}
