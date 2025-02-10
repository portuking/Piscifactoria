package peces;

import java.util.List;
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
    /**Nombre Común del Pez*/
    protected final String name;
    /**Nombre Científico del Pez*/
    protected final String scientifcName;
    /** Edad del Pez */
    protected int age;
    /** Sexo del Pez  true -> Hembra | false -> Macho*/
    protected final boolean sex;
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
        this.name = fishStats.getNombre();
        this.scientifcName = fishStats.getCientifico();
        this.sex = sex;
        this.age = 0;
        this.fertile = false;
        this.alive = true;
        this.eat = true;
        this.mature = false;
        this.reproductionCycle = fishStats.getCiclo();
    }

    /** Método que muestra el estado del Pez */
    public void showStatus() {
        System.out.println("---------------"+this.getName()+"---------------");
        System.out.println("Edad: " + this.age + " días");
        System.out.println(this.sex ? "Sexo: H" : "Sexo: M");
        System.out.println(this.alive ?"Vivo: Si" : "Vivo: No");
        System.out.println(this.eat ? "Alimentado: Si" : "Alimentado: No");
        System.out.println(this.mature ? "Adulto: Si" : "Adulto: No");
        System.out.println(this.fertile ? "Fértil: Si" : "Fértil: No");
    }

    /**
     * @return Método que devuelve una instancia de la clase Pez
     */
    public abstract Pez getNewFish(boolean sex);

    /**
     * Método que implementa la manera de comer del pez
     */
    public abstract int eat();

    /**
     * Método que hace crecer un Pez
     * @param fishes Lista de peces para realizar la reproduccion si es posible
     * @param tank Tanque en el que se reproducen los peces si es posible
     */
    public void grow(List<Pez> fishes, Tanque tank){
        if(!this.alive){
            return;
        }else{
            Random r = new Random();
            if(!this.eat) {
                this.eat();
                this.eat = true;
                if(r.nextBoolean()){
                    this.alive = false;
                    return;
                }
                this.age++;
                if(this.mature){
                    if(this.fertile && this.isFemale()){
                        this.reproduce(fishes, tank);
                    }
                }else{
                    if(this.age % 2 == 0) {
                        int dead = r.nextInt(100) + 1;
                        if(dead <= 5) {
                            this.alive = false;
                            return;
                        }
                    }
                }
            }else{
                this.eat = false;
                this.age++;
                if(this.mature){
                    if(this.fertile && this.isFemale()){
                        this.reproduce(fishes, tank);
                    }
                }else{
                    if(this.age % 2 == 0) {
                        int dead = r.nextInt(100) + 1;
                        if(dead <= 5) {
                            this.alive = false;
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Método que reproduce los peces
     * @param fishes Lista de Peces
     * @param tank Tanque en el que se reproduce
     */
    public void reproduce(List<Pez> fishes, Tanque tank) {
        if(!this.alive || !this.mature || !this.fertile || !this.isFemale() || this.reproductionCycle > 0) {
            return;
        }else{
            boolean fertileMale = false;
            for (Pez pez : fishes) {
                if(pez.isMale() && pez.fertile && pez.alive) {
                    fertileMale = true;
                    break;
                }
            }
            if(fertileMale){
                int nEggs = this.fishStats.getHuevos();
                for (int i = 0; i < nEggs; i++) {
                    boolean newSex = false;
                    if(!tank.isFull()){
                        if(tank.fishesF() > tank.fishesM()) {
                            newSex = true;
                        }else{
                            newSex = false;
                        }
                    }
                    Pez newFish = this.getNewFish(newSex);
                    fishes.add(newFish);
                }
            }
            this.reproductionCycle = this.fishStats.getCiclo();
            this.fertile = false;
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
     * @return La edad del Pez
     */
    public int getAge() {
        return age;
    }

    /**
     * @return Nombre Común del Pez
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return Nombre Científico del Pez
     */
    public String getScientificName(){
        return this.scientifcName;
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
        if(this.age >= this.getFishStats().getMadurez()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return Si el Pez es fértil
     */
    public boolean isFertile(){
        if(this.age < this.fishStats.getCiclo()){
            this.reproductionCycle --;
            this.fertile = false;
        }else{
            this.fertile = true;
        }
        return this.fertile;
    }

    /**
     * @return true si el pez es macho
     */
    public boolean isMale(){
        if(this.sex == false){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return true si el pez es hembra
     */
    public boolean isFemale() {
        if(this.sex == true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return Número de dias que tarda el pez en ser fértil
     */
    public int getReproductionCycle() {
        return reproductionCycle;
    }

    /**
     * Método para setear el ciclo de reproducción
     * @param reproductionCycle número de días que dura el ciclo
     */
    public void setReproductionCycle(int reproductionCycle) {
        this.reproductionCycle = reproductionCycle;
    }

     /**
     * Método para setear la comida
     * @param eat Si el pez ha comido
     */
    public void setEat(boolean eat) {
        this.eat = eat;
    }

    /**
     * Método para setear la fertilidad
     * @param fertile false si no es fértil y true si lo es
     */
    public void setFertile(boolean fertile) {
        this.fertile = fertile;
    }
    @Override
    public String toString() {
        return "===== Estado del Pez =====\n" +
               "Nombre Común: " + name + "\n" +
               "Nombre Científico: " + scientifcName + "\n" +
               "Edad: " + age + " días\n" +
               "Sexo: " + (sex ? "Hembra" : "Macho") + "\n" +
               "Estado:\n" +
               "  - Vivo: " + (alive ? "Sí" : "No") + "\n" +
               "  - Alimentado: " + (eat ? "Sí" : "No") + "\n" +
               "  - Maduro: " + (mature ? "Sí" : "No") + "\n" +
               "  - Fértil: " + (fertile ? "Sí" : "No") + "\n" +
               "Ciclo de Reproducción Restante: " + reproductionCycle + " días\n" +
               "===========================";
    }
}
