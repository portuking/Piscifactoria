package peces.alimentacion;

import edificios.tanque.Tanque;
import peces.Pez;
import propiedades.PecesDatos;
import java.util.Random;

public class AlimentacionCarnivoro extends Pez{

    public AlimentacionCarnivoro(PecesDatos fishStats, boolean sex){
        super(sex, fishStats);
    }

    @Override
    public void eat(Tanque<? extends Pez> tank) {
        for (int i = 0; i < tank.getFishes().size(); i++) {
            if(tank.getFishes().get(i) != null) {
                if(!tank.getFishes().get(i).isAlive()) {
                    this.setHungry(false);
                    Random r = new Random();
                    boolean random = r.nextBoolean();
                    if(random == false) {
                        tank.getFishes().set(i, null);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public Pez getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }


}
