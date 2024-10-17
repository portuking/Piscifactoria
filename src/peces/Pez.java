package peces;

import java.util.Random;

import propiedades.PecesDatos;

/**
 * Clase que representa un Pez
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public  abstract class Pez {
    /**Objeto de la clase PecesDatos con los datos del Pez */
    private PecesDatos fishStats;
    /** Edad del Pez */
    private int age;
    /** Sexo del Pez */
    private boolean sex;
    /** Si el Pez es fértil */
    private boolean fertile;
    /** Si el Pez está vivo */
    private boolean alive;
    /** Si el Pez tiene hambre o no */
    private boolean hungry;
    /** Si el Pez es adulto */
    private boolean mature;

    /**
     * Constructor de Pez
     * @param age Edad del Pez
     * @param sex Sexo del Pez
     */
    public Pez(boolean sex) {
        this.age = 0;
        this.sex = sex;
        this.fertile = false;
        this.alive = true;
        this.hungry = true;
        this.mature = false;
    }

    /** Método que muestra el estado del Pez */
    public void showStatus() {
        System.out.println("---------------"+fishStats.getNombre()+"---------------");
        System.out.println("Edad: " + this.age + " días");
        System.out.println(sex ? "Sexo: H" : "Sexo: M");
        System.out.println(hungry ? "Alimentado: Si" : "Alimentado: No");
        System.out.println(mature ? "Adulto: Si" : "Adulto: No");
        System.out.println(fertile ? "Fértil: Si" : "Fértil: No");
    }

    /**
     * Método que implementa la manera de comer del pez
     */
    public abstract void eat();

    /**
     * Método que reproduce el Pez
     */
    public abstract void reproduce();

    /**
     * Método que hace crecer un Pez
     */
    public void grow(){
        Random r = new Random();
        if (this.alive){
            this.eat();
            if(!this.hungry){
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
            if (this.mature && this.fertile) {
                this.reproduce();
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
        this.hungry = true;
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
    public boolean isHungry(){
        return this.hungry;
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
