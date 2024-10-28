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
    public int eat() {
        int comidaConsumida= 0;
            if(this.isAlive() && !this.isEat()) {
                    this.eat = true;
                    comidaConsumida += 1;
                }  
                return comidaConsumida;
            }
          
        
    

    @Override
    public Pez getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }


}
