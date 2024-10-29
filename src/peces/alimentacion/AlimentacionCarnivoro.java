package peces.alimentacion;

import peces.Pez;
import propiedades.PecesDatos;

public class AlimentacionCarnivoro extends Pez {

    public AlimentacionCarnivoro(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    @Override
    public int eat() {
        int comidaConsumida = 0;
        if (this.isAlive() && !this.isEat()) {
            this.eat = true;
            comidaConsumida += 1;
        }
        return comidaConsumida;
    }

    @Override
    public Pez getNewFish() {
        throw new UnsupportedOperationException("Método sin implementar");
    }

}
