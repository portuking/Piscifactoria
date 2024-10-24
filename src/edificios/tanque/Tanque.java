package edificios.tanque;

import java.util.ArrayList;
import peces.Pez;

/**
 * Clase que representa un Tanque
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class Tanque<T extends Pez> {
    /** Peces que hay en el Tanque */
    private ArrayList<T> fishes;
    /** Capacidad máxima del Tanque */
    int maxCapacity;
    /** Número del Tanque */
    private int tankNum;

    /**
     * Constructor de Tanque
     * @param maxCapacity capacidad máxima del Tanque
     */
    public Tanque(int maxCapacity, int tankNum) {
        this.fishes = new ArrayList<>(this.maxCapacity);
        this.maxCapacity = maxCapacity;
        this.tankNum = tankNum;
    }

    /**
     * Muestra la información del Tanque 
     */
    public void showStatus(){
        System.out.println("=============== Tanque "+ this.tankNum +" ===============");
        System.out.println("Ocupación: peces / max " + (fishes.size()/this.maxCapacity)+"%");
        System.out.println("Peces vivos: vivos / total " + (fishesAlive() / this.maxCapacity)+"%");
        System.out.println("Peces alimentados: alimentados / vivos" + (alimentedFishes()/ fishesAlive()));
        System.out.println("Peces adultos: adultos / vivos" + (maturefishes() / fishesAlive()));
        System.out.println("Hembras / Machos" + ( fishesF()/fishesM()));
        System.out.println("Fertiles: fertiles / vivos" + (fertiles()/ fishesAlive()));

    }
    /**
     * Muestra la información de los Peces del Tanque
     */
    public void showfishestatus(){
        for (Pez pez : fishes) {
            pez.showStatus();
        }
    }

    /** 
     * Muestra la ocupación del Tanque
     */
    public void showCapacity(String pisciName) {
        if(this.isEmpty() == false) {
            int capacity = this.fishes.size() / this.maxCapacity;
            System.out.println("Tanque " + this.tankNum + " de la piscifactoría " + pisciName + " al " + capacity + "% de capacidad. [peces/espacios]");
        }else{
        System.out.println("Tanque " + this.tankNum + " de la piscifactoría " + pisciName + " al 0% de capacidad. [peces/espacios]");
        }
    }

    /**
     * Método que añade un Pez al Tanque
     * @param fish Pez a añadir
     */
    public void addFishes(Pez fish){
        if (this.isFull() == false) {
            this.fishes.add((T) fish);            
        }

        //Comprobacion del número de peces masculino y femeninos para añadir el nuevo pez de forma equitativa
        if(this.fishesM() > this.fishesF()) {
            fish.setSex(false);
        }else if(this.fishesF() > this.fishesM()){
            fish.setSex(true);
        }else if(this.fishesM() == this.fishesF()){
            fish.setSex(false);
        }

        //Buscar como se registra el nacimiento del pez

    }

    /**
     * Método que comprueba si hay un macho y una hembra para reproducirse
     * @return True si hay un macho y una hembra fertiles y False si no hay
     */
    public boolean fishMatch() {
        boolean fertileMale = false;
        boolean fertileFemale = false;
        for (Pez fish : fishes) {
            if(fish.isMale() && fish.isFertile()) {
                fertileMale = true;
            }
            if(fish.isFemale() && fish.isFertile()) {
                fertileFemale = true;
            }
            if(fertileFemale && fertileMale) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Número de Peces vivos del Tanque
     */
    public int fishesAlive(){
        int numAlive = 0;
        for (Pez pez : fishes) {
            if (pez.isAlive() == true) {
                numAlive+=1;
            }
        }
        return numAlive;
    }

    /**
     * @return Número de Peces que han comido del Tanque
     */
    public int alimentedFishes(){
        int numHungry = 0;
        for (Pez pez : fishes) {
            if (pez.isEat() == false && pez.isAlive() == true) {
                numHungry+=1;
            }
        }
        return numHungry;
    }

   /**
    * @return El número de peces maduros del Tanque 
    */
    public int maturefishes(){
        int mature = 0;
        for (Pez pez : fishes){
            if (pez.isMature()== true && pez.isAlive()== true){
                mature ++;
            }
        }
        return mature;
    }

    /**
     * @return Número de peces hembra del Tanque
     */
    public int fishesF() {
        int females = 0;
        for (Pez pez : fishes) {
            if(pez.isAlive() == true && pez.isFemale() == true) {
                females += 1;
            }
        }
        return females;
    }

    /**
     * @return Número de peces macho del Tanque
     */
    public int fishesM() {
        int males = 0;
        for (Pez pez : fishes) {
            if(pez.isAlive() == true && pez.isMale() == true) {
                males += 1;
            }
        }
        return males;
    }

    /**
     * @return Número de peces Fertiles del Tanque
     */
    public int fertiles() {
        int fertiles = 0;
        for (Pez pez : fishes) {
            if(pez.isAlive() == true && pez.isFertile() == true) {
                fertiles += 1;
            }
        }
        return fertiles;
    }

    /**
     * @return ArrayList con los peces del Tanque
     */
    public ArrayList<T> getFishes(){
        return fishes;
    }

    /**
     * @return Método que muestra si el Tanque está vacio o no
     */
    public boolean isEmpty(){
        if (this.fishes.size() <= 0) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return Devuelve si el Tanque esta lleno
     */
    public boolean isFull(){
        if(this.fishes.size() < this.maxCapacity) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Tanque # " + this.tankNum + "\n" + 
        "Capacidad máxima " + this.maxCapacity + "\n" +
        "Peces actuales " + this.fishes.size() + "\n" + 
        "Peces vivos " + fishesAlive() + "\n" +
        "Peces alimentados " + alimentedFishes() + "\n" +
        "Peces adultos " + maturefishes() + "\n" +
        "Peces hembra " + fishesF() + "\n" + 
        "Peces macho " + fishesM() + "\n" +
        "Peces fértiles " + fertiles(); 
    }


}
