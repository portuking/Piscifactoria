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
    /**Si el Pez se puede reproducir o no*/
    protected boolean reproducible;

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
    public abstract Pez reproduce(boolean sex);

    /**
     * Método que implementa la manera de comer del pez
     */
    public abstract int eat();

    /**
     * Método que hace crecer un Pez
     */
    public void grow(boolean comido){
        if(this.isAlive()) {
            if(this.isEat()){
                this.setEat(false);
                this.age +=1;
                if(this.age >= this.fishStats.getMadurez()) {
                    setMature(true);
                }else{
                    setMature(false);
                }
                if(this.age == this.reproductionCycle) {
                    setFertile(true);
                }else{
                    setFertile(false);
                }
                if(this.isFertile() && this.isMature()) {
                    this.reproducible = true;
                } 
            }else{
                Random r = new Random();
                Boolean dead = r.nextBoolean();
                if(!dead){
                    setEat(comido);
                    this.age +=1;
                    if(this.age >= this.fishStats.getMadurez()) {
                        setMature(true);
                    }else{
                        setMature(false);
                    }
                    if(this.age == this.reproductionCycle) {
                        setFertile(true);
                    }else{
                        setFertile(false);
                    }
                    if(this.isFertile() && this.isMature()) {
                        this.reproducible = true;
                    }
                }else{
                    this.setAlive(false);
                    this.setMature(false);
                    this.setFertile(false);
                    this.setEat(false);
                }
            }
        }else{
            this.setMature(false);
            this.setFertile(false);
            this.setEat(false);
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
    public boolean isFertile() {
        if (this.age >= this.fishStats.getCiclo()) {
            this.fertile = true;
        } else {
            this.fertile = false;
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

    /**
     * Método que permite setear si esta vivo o no
     * @param alive si esta vivo o no
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Método que permite setear si es maduro o no
     * @param mature si es maduro o no
     */
    public void setMature(boolean mature) {
        this.mature = mature;
    }

    /**
     * @return si el Pez es reproducible o no
     */
    public boolean isReproducible(){
        return this.reproducible;
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
