package peces.alimentacion;

import edificios.tanque.Tanque;
import peces.Pez;
import propiedades.PecesDatos;

public class AlimentacionFiltrador extends Pez {

    public AlimentacionFiltrador(PecesDatos fishStats, boolean sex){
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
