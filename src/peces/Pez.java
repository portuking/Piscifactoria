package peces;

import java.util.Random;

import edificios.tanque.Tanque;
import propiedades.PecesDatos;

/**
 * Clase que representa un Pez
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public  abstract class Pez {
    /**Objeto de la clase PecesDatos con los datos del Pez */
    protected PecesDatos fishStats;
    /** Edad del Pez */
    protected int age;
    /** Sexo del Pez */
    protected boolean sex;
    /** Si el Pez es fértil */
    protected boolean fertile;
    /** Si el Pez está vivo */
    protected boolean alive;
    /** Si el Pez ha comido o no */
    protected boolean eat;
    /** Si el Pez es adulto */
    protected boolean mature;
    /**Ciclo de reprosucción del Pez*/
    protected int reproductionCycle;

    /**
     * Constructor de Pez
     * @param age Edad del Pez
     * @param sex Sexo del Pez
     */
    public Pez(boolean sex, PecesDatos fishStats) {
        this.fishStats = fishStats;
        this.sex = sex;
        this.age = 0;
        this.fertile = false;
        this.alive = true;
        this.eat = true;
        this.mature = false;
        this.reproductionCycle = this.getFishStats().getCiclo();
    }

    /** Método que muestra el estado del Pez */
    public void showStatus() {
        System.out.println("---------------"+fishStats.getNombre()+"---------------");
        System.out.println("Edad: " + this.age + " días");
        System.out.println(sex ? "Sexo: H" : "Sexo: M");
        System.out.println(eat ? "Alimentado: Si" : "Alimentado: No");
        System.out.println(mature ? "Adulto: Si" : "Adulto: No");
        System.out.println(fertile ? "Fértil: Si" : "Fértil: No");
    }

    /**
     * @return Método que devuelve una instancia de la clase Pez
     */
    public abstract Pez getInstance();

    /**
     * Método que implementa la manera de comer del pez
     * FACER O MÉTODO DE TIPO INT
     * 1. DECLARAR UNHA VARIABLE QUE DEVOLVERA O NÚMERO DE UNIDADES DE COMIDA QUE VAI A COMER O PEIXE
     * 2. IMPLEMENTAR A LÓGICA DO MÉTODO E GARDAR O QUE COME NA VARIABLE ANTERIOR
     * 3. DEVOLVER A VARIABLE PARA POSTERIORMENTE RESTAR ESA CANTIDAD DO ALMACÉN DE COMIDA SI É NECESARIO
     */
    public abstract void eat(Tanque<? extends Pez> tank);

    /**
     * Método que hace crecer un Pez
     */
    public void grow(){
        Random r = new Random();
        if (this.alive){
            
            if(!this.eat){
                this.alive = r.nextBoolean(); 
            }
            this.age++;
            if(!this.mature){
                if(this.age % 2 == 0) {
                    int kill = r.nextInt(100)+1;
                    if (kill<=5) {
                        this.alive=false;
                    }
                }
            }
        }
    }

    /**
     * Método que reproduce un Pez
     */
    public void reproduce(Tanque <? extends Pez> tank) {
        if(this.fertile) {
            for (int i = 0; i < this.fishStats.getHuevos(); i++) {
                if(!tank.isFull()) {
                    Pez newFish = this.getInstance();
                    tank.addFishes(newFish);
                }else{
                    break;
                }
            }
            this.reproductionCycle = this.fishStats.getCiclo();
        }
    }

    /**
     * Método que comprueba la madurez, la edad, la fertilidad y los ciclos de los Peces
     */
    public void isMilf() {
        if(this.age >= this.fishStats.getMadurez()) {
            this.mature = true;
        }
        if(this.mature) {
            if(this.reproductionCycle == 0) {
                this.fertile = true;
            }else{
                this.reproductionCycle --;
            }
        }
    }

    /**
     * Método que resetea un Pez
     */
    public void reset(){
        this.age = 0;
        this.fertile = false;
        this.alive = true;
        this.eat = true;
        this.mature = false;
    }

    /**
     * @return Devuelve el objeto con la información del Pez
     */
    public PecesDatos getFishStats(){
        return this.fishStats;
    }

    /**
     * @return Si el Pez esta vivo o no
     */
    public boolean isAlive(){
        return this.alive;
    }

    /**
     * @return Si el Pez tiene hambre
     */
    public boolean isEat(){
        return this.eat;
    }

    /**
     * @return Si el Pez es maduro o no
     */
    public boolean isMature(){
        return this.mature;
    }

    /**
     * @return Si el Pez es fértil
     */
    public boolean isFertile(){
        return this.fertile;
    }

    /**
     * @return true si el pez es macho
     */
    public boolean isMale(){
        if(this.sex == true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Método para setear el sexo
     * @param sex sexo para el pez
     */
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    /**
     * Método para setear la comida
     * @param eat Si el pez tiene hambre
     */
    public void setEat(boolean eat) {
        this.eat = eat;
    }

    /**
     * @return true si el pez es hembra
     */
    public boolean isFemale() {
        if(this.sex == false){
            return true;
        }else{
            return false;
        }
    }
}
