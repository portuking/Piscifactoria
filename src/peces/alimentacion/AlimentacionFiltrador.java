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
public abstract class AlimentacionFiltrador extends Pez {

    /**
     * Constructor para crear un nuevo pez filtrador.
     * @param fishStats Objeto de tipo PecesDatos que contiene las estadísticas del pez.
     * @param sex booleano que indica el sexo del pez (true si es macho, false si es hembra).
     */
    public AlimentacionFiltrador(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    /**
     * Método que crea un nuevo pez
     * @return Una cría de pez
     */
    @Override
    public abstract Pez reproduce(boolean sex);

    /**
     * Método que simula la acción de comer del pez filtrador.
     * @return La cantidad de comida consumida (0 si no ha comido o 1 si ha comido).
     */
    @Override
    public int eat() {
        int comidaConsumida = 0;
        if (this.isAlive() && !this.isEat()) {
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
