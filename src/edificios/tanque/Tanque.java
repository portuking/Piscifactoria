package edificios.tanque;

import java.util.ArrayList;
import java.util.List;
import peces.Pez;


public class Tanque {
    /** Contador de Tanques */
    public static int tankCount = 0;
    /** Peces que hay en el Tanque */
    private List<Pez> fishs;
    /** Capacidad máxima del Tanque */
    int maxCapacity;
    /** Número del Tanque */
    private int tankNum;

    /**
     * Constructor de Tanque
     * @param maxCapacity capacidad máxima del Tanque
     */
    public Tanque(int maxCapacity) {
        this.fishs = new ArrayList<>(this.maxCapacity);
        this.maxCapacity = maxCapacity;
        tankCount++;
        this.tankNum = tankCount;
    }

    /**
     * Muestra la información del Tanque 
     */
    public void showStatus(){
        System.out.println("=============== Tanque "+ this.tankNum +" ===============");
        System.out.println("Ocupación: peces / max " + (fishs.size()/this.maxCapacity)+"%");
        System.out.println("Peces vivos: vivos / total " + (fishAlive() / this.maxCapacity)+"%");
        System.out.println("Peces alimentados: alimentados / vivos" + (fishHungry()/ fishAlive()));
        System.out.println("Peces adultos: adultos / vivos" + (matureFishs() / fishAlive()));
        System.out.println("Hembras / Machos" + ( fishF()/fishH()));
        System.out.println("Fertiles: fertiles / vivos" + (fertiles()/ fishAlive()));

    }
    /**
     * Muestra la información de los Peces del Tanque
     */
    public void showFishStatus(){
        for (Pez pez : fishs) {
            pez.showStatus();
        }
    }

    /** Muestra la ocupación del Tanque */
    public void showCapacity(String pisciName) {
        if(this.fishs.size() > 0) {
            int capacity = this.fishs.size() / this.maxCapacity;
            System.out.println("Tanque " + this.tankNum + " de la piscifactoría " + pisciName + " al " + capacity + "% de capacidad. [peces/espacios]");
        }else{
        System.out.println("Tanque " + this.tankNum + " de la piscifactoría " + pisciName + " al 0% de capacidad. [peces/espacios]");
        }
    }

    /**
     * @return Número de Peces vivos del Tanque
     */
    public int fishAlive(){
        int numAlive = 0;
        for (Pez pez : fishs) {
            if (pez.isAlive() == true) {
                numAlive+=1;
            }
        }
        return numAlive;
    }

    /**
     * @return Número de Peces que han comido del Tanque
     */
    public int fishHungry(){
        int numHungry = 0;
        for (Pez pez : fishs) {
            if (pez.isHungry() == false && pez.isAlive() == true) {
                numHungry+=1;
            }
        }
        return numHungry;
    }

   /**
    * @return El número de peces maduros del Tanque 
    */

    public int matureFishs(){
        int mature = 0;
        for (Pez pez : fishs){
            if (pez.isMature()== true && pez.isAlive()== true){
                mature ++;
            }
        }
        return mature;
    }




    /**
     * @return Número de peces hembra del Tanque
     */
    public int fishF() {
        int females = 0;
        for (Pez pez : fishs) {
            if(pez.isAlive() == true && pez.isFemale() == true) {
                females += 1;
            }
        }
        return females;
    }

    /**
     * @return Número de peces macho del Tanque
     */
    public int fishH() {
        int males = 0;
        for (Pez pez : fishs) {
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
        for (Pez pez : fishs) {
            if(pez.isAlive() == true && pez.isFertile() == true) {
                fertiles += 1;
            }
        }
        return fertiles;
    }
}
