package peces.alimentacion;

import peces.Pez;
import java.util.Random;
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
    public int eat() {
        int comidaConsumida= 0;
            if(this.isAlive() && !this.isEat()) {
                    this.eat = true;
                    Random r = new Random();
                    if(r.nextBoolean()){
                        comidaConsumida = 0;
                    }else{
                        comidaConsumida = 1;
                    }
                }  
                return comidaConsumida;
            }

}
