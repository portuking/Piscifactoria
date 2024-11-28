package edificios.tanque;

import java.util.ArrayList;

import edificios.piscifactoria.Piscifactoria;
import peces.Pez;
import sistema.SISMonedas;

/**
 * Clase que representa un Tanque
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class Tanque {
    /** Peces que hay en el Tanque */
    private ArrayList<Pez> fishes;
    /** Capacidad máxima del Tanque */
    int maxCapacity;
    /** Número del Tanque */
    private int tankNum;
    /**Tipo de Pez del Tanque*/
    private String fishType;

    /**
     * Constructor de Tanque
     * @param maxCapacity capacidad máxima del Tanque
     * @param tankNum Número de tanque
     */
    public Tanque(int maxCapacity, int tankNum) {
        this.fishes = new ArrayList<>(this.maxCapacity);
        this.maxCapacity = maxCapacity;
        this.tankNum = tankNum;
        this.fishType = null;
    }

    /**
     * Muestra la información del Tanque 
     */
    public void showStatus(){
        System.out.println("=============== Tanque "+ this.tankNum +" ===============");
        if(this.fishes.size() > 0 && this.getMaxCapacity() > 0){
            System.out.println("Ocupación: peces / max " + (this.fishes.size()/this.getMaxCapacity())*100+"%");
        }else{
            System.out.println("Ocupación: peces / max 0%");
        }
        if(this.fishesAlive() > 0 && this.getMaxCapacity() > 0) {
            System.out.println("Peces vivos: vivos / total " + (this.fishesAlive() / this.getMaxCapacity())*100+"%");
        }else{
            System.out.println("Peces vivos: vivos / total 0%");
        }
        if(this.alimentedFishes() > 0 && this.fishesAlive() > 0) {
            System.out.println("Peces alimentados: alimentados / vivos" + (this.alimentedFishes()/ this.fishesAlive())*100+"%");
        }else{
            System.out.println("Peces alimentados: alimentados / vivos 0%");
        }
        if(this.matureFishes() > 0 && this.fishesAlive() > 0){
            System.out.println("Peces adultos: adultos / vivos " + (this.matureFishes() / this.fishesAlive())*100+"%");
        }else{
            System.out.println("Peces adultos: adultos / vivos 0%");
        }
        if(this.fishesF() > 0 && this.fishesM() > 0) {
            System.out.println("Hembras / Machos " + (this.fishesF()/this.fishesM())*100+"%");
        }else{
            System.out.println("Hembras / Machos 0%");
        }
        if(this.fertiles() > 0 && this.fishesAlive() > 0) {
            System.out.println("Fertiles: fertiles / vivos 0%");
        }
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
    public void showCapacity(Piscifactoria piscifactoria) {
        if(this.isEmpty() == false) {
            int capacity = this.fishes.size() / this.maxCapacity;
            System.out.println("Tanque " + piscifactoria.getTankNum() + " de la piscifactoría " + piscifactoria.getName() + " al " + capacity + "% de capacidad. [peces/espacios]");
        }else{
        System.out.println("Tanque " + piscifactoria.getTankNum() + " de la piscifactoría " + piscifactoria.getName() + " al 0% de capacidad. [peces/espacios]");
        }
    }

    /**
     * Método que añade un Pez al Tanque
     * @param fish Pez a añadir
     */
    public void addFishes(Pez fish){
        if (!this.isFull()) {
            if(this.fishType == null) {
                this.fishType = fish.getName();
            }
            if(this.fishType == fish.getName()) {

            }else{
                System.out.println("El tipo de Pez es incorrecto para esta Piscifactoría");
            }
        }
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
     * Método que hace crecer todos los peces del Tanque y vende 
     * los que hayan llegado a la edad óptima
     */
    public void nextDay(){
        for (Pez pez : fishes) {
            if(pez.isAlive()){
                pez.grow(fishes, null);
            }
        }
        int soldFishes = 0;
        int earnings = 0;
        for (int i = 0; i < fishes.size(); i++) {
            Pez pez = fishes.get(i);
            if(pez.isAlive() && pez.getAge()>= pez.getFishStats().getOptimo()) {
                earnings+= pez.getFishStats().getMonedas();
                fishes.remove(i);
                i--;
                soldFishes++;
            }
        }
        if(soldFishes > 0){
            SISMonedas sisMonedas = SISMonedas.getInstance();
            int currentMoney = sisMonedas.getMonedas();
            sisMonedas.setMonedas(currentMoney + earnings);

            System.out.println("Se han vendido: " + soldFishes + " peces");
            System.out.println("Se han ganado " + earnings + " Monedas");
        }else{
            System.out.println("No se ha vendido ningún Pez");
        }
    }

    /**
     * Método que vende los peces vivos y maduros
     */
    public void sellFishes(){
        SISMonedas sisMonedas = SISMonedas.getInstance();
        int earnings = 0;
        for (int i = 0; i < fishes.size(); i++) {
            Pez pez = fishes.get(i);
            if(pez.isAlive() && pez.isMature()){
                earnings += pez.getFishStats().getMonedas();
                fishes.remove(i);
                i--; 
            }
        }
        sisMonedas.setMonedas(sisMonedas.getMonedas() + earnings);
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
     * Elimina todos los peces del Tanque
     */
    public void cleanTank(){
        this.fishes.clear();
    }

    /**
     * Método que elimina los peces muertos del Tanque
     */
    public void cleanDeadFishes(){
        if(!this.isEmpty()){
            for (int i = 0; i < fishes.size(); i++) {
                if (fishes.get(i).isAlive() == false) {
                    fishes.remove(i);
                }
            }
        }else{
            System.out.println("No hay peces");
        }
    }

    /**
     * @return Número de Peces que han comido del Tanque
     */
    public int alimentedFishes(){
        int numEated = 0;
        for (Pez pez : fishes) {
            if (pez.isEat() == true && pez.isAlive() == true) {
                numEated+=1;
            }
        }
        return numEated;
    }

    /**
     * Método para saber cuanta comida hace falta para los peces
     * @return Comida que comen los peces
     */
    public int foodAmount(){
        int foodAmount = 0;
        for (Pez pez : fishes) {
            foodAmount += pez.eat();
        }
        return foodAmount;
    }

   /**
    * @return El número de peces maduros del Tanque 
    */
    public int matureFishes(){
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
    public ArrayList<Pez> getFishes(){
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

    /**
     * @return Devuelve la capacidad máxima del tanque
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * @return Devuelve el numero del tanque
     */ 
     public int getTankNum() {
        return tankNum;
    }

    /**
     * @return El tipo de Pez permitido por el Tanque
     */
    public String getFishType(){
        if(this.fishType == null){
            return "Sin Tipo";
        }else{
            return this.fishType;
        }
    }

    @Override
    public String toString() {
        return "Tanque # " + this.tankNum + "\n" + 
        "Capacidad máxima " + this.maxCapacity + "\n" +
        "Peces actuales " + this.fishes.size() + "\n" + 
        "Peces vivos " + fishesAlive() + "\n" +
        "Peces alimentados " + alimentedFishes() + "\n" +
        "Peces adultos " + matureFishes() + "\n" +
        "Peces hembra " + fishesF() + "\n" + 
        "Peces macho " + fishesM() + "\n" +
        "Peces fértiles " + fertiles(); 
    }

}
