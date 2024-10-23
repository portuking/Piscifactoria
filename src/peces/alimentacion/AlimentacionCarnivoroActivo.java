package peces.alimentacion;

import edificios.tanque.Tanque;
import peces.Pez;
import propiedades.PecesDatos;

public class AlimentacionCarnivoroActivo extends Pez{

    public AlimentacionCarnivoroActivo(PecesDatos fishStats, boolean sex) {
        super(sex, fishStats);
    }

    @Override
    public Pez getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }

    @Override
    public void eat(Tanque<? extends Pez> tank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eat'");
    }


}
