package peces.alimentacion;

import edificios.tanque.Tanque;
import java.util.Random;
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
    public int eat() {
        int comidaConsumida= 0;
            if(this.isAlive() && !this.isEat()) {
                    this.eat = true;
                    Random r = new Random();
                    if(r.nextBoolean()){
                        comidaConsumida = 2;
                    }else{
                        comidaConsumida = 1;
                    }
                }  
                return comidaConsumida;
            }


}
