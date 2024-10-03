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
        System.out.println("Ocupación: " + (fishs.size()/this.maxCapacity));
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
    public void showCapacity() {

    }

}
