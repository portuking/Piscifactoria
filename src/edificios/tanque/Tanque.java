package edificios.tanque;

import java.util.ArrayList;
import java.util.Random;

import edificios.piscifactoria.Piscifactoria;
import estadisticas.Estadisticas;
import peces.Pez;
import peces.alimentacion.AlimentacionCarnivoro;
import peces.alimentacion.AlimentacionCarnivoroActivo;
import peces.alimentacion.AlimentacionFiltrador;
import peces.alimentacion.AlimentacionOmnivoro;
import sistema.SISMonedas;

/**
 * Clase que representa un Tanque
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class Tanque {
    //Piscifactoría a la que pertenece el Tanque
    private Piscifactoria fishFarm;
    /** Peces que hay en el Tanque */
    private ArrayList<Pez> fishes;
    /** Capacidad máxima del Tanque */
    int maxCapacity;
    /** Número del Tanque */
    private int tankNum;
    /**Tipo de Pez del Tanque*/
    private String fishType;
    /**Tipo del Tanque: true -> Río | false -> Mar */
    private boolean type;

    /**
     * Constructor de Tanque
     * @param maxCapacity capacidad máxima del Tanque
     * @param tankNum Número de tanque
     * @param type Define el tipo de Tanque
     */
    public Tanque(int maxCapacity, int tankNum, boolean type , Piscifactoria p) {
        this.fishes = new ArrayList<>(this.maxCapacity);
        this.maxCapacity = maxCapacity;
        this.tankNum = tankNum;
        this.fishType = null;
        this.type = type;
        this.fishFarm = p;
    }

    /**
     * Muestra la información del Tanque 
     */
    public void showStatus(){
        System.out.println("=============== Tanque "+ this.tankNum +" ===============");
        if(this.fishes.size() > 0 && this.getMaxCapacity() > 0){
            System.out.println("Ocupación: peces/max " + this.fishes.size() + "/" + this.getMaxCapacity() + " " + ((this.fishes.size() * 100)/this.getMaxCapacity())+"%");
        }else{
            System.out.println("Ocupación: peces/max 0%");
        }
        if(this.fishesAlive() > 0 && this.getMaxCapacity() > 0) {
            System.out.println("Peces vivos: vivos/total " + this.fishesAlive() + "/" + this.getMaxCapacity() + " " + ((this.fishesAlive() * 100)/this.getMaxCapacity())+"%");
        }else{
            System.out.println("Peces vivos: vivos/total 0%");
        }
        if(this.alimentedFishes() > 0 && this.fishesAlive() > 0) {
            System.out.println("Peces alimentados: alimentados/vivos" + this.alimentedFishes() + "/" + this.fishesAlive() + " " + ((this.alimentedFishes() * 100)/this.fishesAlive())+"%");
        }else{
            System.out.println("Peces alimentados: alimentados/vivos 0%");
        }
        if(this.matureFishes() > 0 && this.fishesAlive() > 0){
            System.out.println("Peces adultos: adultos/vivos "+ this.matureFishes()+"/"+this.fishesAlive() + " " + ((this.matureFishes() * 100)/this.fishesAlive())+"%");
        }else{
            System.out.println("Peces adultos: adultos/vivos 0%");
        }
        if(this.fishesF() > 0 && this.fishesM() > 0) {
            System.out.println("Hembras/Machos " + this.fishesF() + "/" +this.fishesM());
        }else{
            System.out.println("Hembras/Machos: 0/0");
        }
        if(this.fertiles() > 0 && this.fishesAlive() > 0) {
            System.out.println("Fertiles: fertiles/vivos 0%");
        }else{
            System.out.println("Fertiles: fertiles/vivos " + this.fertiles() + "/" + this.fishesAlive());
        }
        System.out.println("Peces enfermos: " + this.contarEnfermos());
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
        if(piscifactoria != null) {
            if(this.isEmpty() == false){
                int capacity = this.fishes.size() / this.maxCapacity;
                System.out.println("Tanque " + piscifactoria.getTankID() + " de la piscifactoría " + piscifactoria.getName() + " al " + capacity + "% de capacidad. [peces/espacios]");
            }else{
                System.out.println("Tanque " + piscifactoria.getTankID() + " de la piscifactoría " + piscifactoria.getName() + " al 0% de capacidad. [peces/espacios]");
            }
        }
    }

    /**
     * Método que añade un Pez al Tanque
     * @param fish Pez a añadir
     */
    public void addFishes(Pez fish){
        if(this.canaddFish(fish)){
            if(this.fishType == null) {
                this.fishType = fish.getName();
            }
            this.fishes.add(fish);
        }
    }

    /**
     * Método que comprueba si se puede añadir un pez según el tipo
     * @param fish pez a comprobar
     * @return true si se puede añadir y false si no se puede
     */
    public boolean canaddFish(Pez fish){
        if(this.fishType == null || fish.getName().equals(this.getFishType())) {
            return true;
        }else{
            return false;
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
    public void nextDay(Piscifactoria p, Estadisticas estadisticas) {
    int vendidos = 0;
    int ganancias = 0;
    SISMonedas sisMonedas = SISMonedas.getInstance();
    
    ArrayList<Pez> nuevosPeces = new ArrayList<>(); 
    for (Pez pez : fishes) {
        if (pez.isAlive()) {
            int consumida;
            boolean comio = false;
            if (pez instanceof AlimentacionCarnivoro || pez instanceof AlimentacionCarnivoroActivo) {
                consumida = pez.eat();
                if (consumida > 0) {
                    if(p.getWarehouseA().getStock() > 0) {
                        p.getWarehouseA().setStock(p.getWarehouseA().getStock() - consumida);
                        comio = true;
                    }else{
                        comio = false;
                    }
                }
            } else if (pez instanceof AlimentacionFiltrador) {
                consumida = pez.eat();
                if (consumida > 0) {
                    if(p.getWarehouseV().getStock() > 0) {
                        p.getWarehouseV().setStock(p.getWarehouseV().getStock() - consumida);
                        comio = true;
                    }else{
                        comio = false;
                    }
                }
            } else if(pez instanceof AlimentacionOmnivoro) {
                consumida = pez.eat();
                if(consumida > 0) {
                    Random r = new Random();
                    boolean wharehouse = r.nextBoolean();
                    if(wharehouse){
                        if(p.getWarehouseV().getStock() > 0) {
                            p.getWarehouseV().setStock(p.getWarehouseV().getStock() - consumida);
                            comio = true;
                        }else{
                            comio = false;
                        }
                    }else{
                        if(p.getWarehouseA().getStock() > 0) {
                            p.getWarehouseA().setStock(p.getWarehouseA().getStock() - consumida);
                            comio = true;
                        }else{
                            comio = false;
                        }
                    }
                }else{
                    comio = false;
                }
            }
            pez.grow(comio);
            if (pez.isFemale() && pez.isFertile() && fishMatch()) {
                for (int j = 0; j < pez.getFishStats().getHuevos(); j++) {
                    if (!this.isFull() && (fishes.size() + nuevosPeces.size() < this.getMaxCapacity())) {
                        boolean sex; 
                        if(this.fishesF() <= this.fishesM()){
                            sex = true;
                        }else{
                            sex = false;
                        }
                        Pez nuevoPez = pez.reproduce(sex);
                        nuevosPeces.add(nuevoPez);
                        estadisticas.registrarNacimiento(nuevoPez.getFishStats().getNombre());
                        System.out.println("Ha nacido un nuevo: " + nuevoPez.getFishStats().getNombre());
                    } else {
                        System.out.println("El tanque está lleno, no se pueden añadir más peces.");
                        break;
                    }
                }
            }
        }
    }
    for (int i = fishes.size() - 1; i >= 0; i--) {
        if (fishes.get(i).isAlive() && fishes.get(i).getAge() == fishes.get(i).getFishStats().getOptimo()) {
            estadisticas.registrarVenta(fishes.get(i).getName(), fishes.get(i).getFishStats().getMonedas());
            vendidos++;
            ganancias += fishes.get(i).getFishStats().getMonedas();
            fishes.remove(i);
        }
    }
    for (Pez nuevoPez : nuevosPeces) {
        if (!this.isFull()) {
            fishes.add(nuevoPez);
        } else {
            break;
        }
    }
    sisMonedas.setMonedas(sisMonedas.getMonedas() + ganancias);
    System.out.println("Se han vendido: " + vendidos + " peces");
    System.out.println("Se han ganado: " + ganancias + " monedas");
    this.propagarEnfermedad();
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
        this.fishType = null;
    }

    /**
     * Método que elimina los peces muertos del Tanque
     */
    public void cleanDeadFishes(){
        if(!this.isEmpty()){
            for (int i = fishes.size() - 1; i >= 0; i--) {
                if(!fishes.get(i).isAlive()) {
                    fishes.remove(fishes.get(i));
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
        return this.fishType;
    }

    /**
     * @return El tipo de peces que puede contener en forma de boolean
     */
    public boolean getType() {
        return this.type;
    }

    /**
     * @return El tipo de peces que puede contener en forma textual
     */
    public String getTankType() {
        return this.type ? "Río" : "Mar";
    }

    /**
     * @return Devuelve el nombre de la Piscifactoría a la que pertenece el tanque
     */    
    public String getFishfarmName() {
        return this.fishFarm.getName();
    }

    /**
     * Método que cuenta enfermos
     * @return peces enfermos
     */
    public int contarEnfermos() {
        int enfermos = 0;
        for (Pez pez : fishes) {
            if (pez.isEnfermo()) {
                enfermos++;
            }
        }
        return enfermos;
    }
    /**
     * Método que propaga la enfermedad en un tanque
     */
    public void propagarEnfermedad() {
        Random r = new Random();
        boolean hayMuertos = false;
        boolean hayEnfermos = false;

        for (Pez pez : fishes) {
            if (!pez.isAlive()) {
                hayMuertos = true;
            }
            if (pez.isEnfermo()) {
                hayEnfermos = true;
            }
        }

        for (Pez pez : fishes) {
            if (!pez.isEnfermo() && pez.isAlive()) {
                if (hayMuertos && r.nextInt(100) < 5) {
                    pez.infectar();
                } else if (hayEnfermos && r.nextInt(100) < 10) {
                    pez.infectar();
                }
            }
        }
    }

    /**
     * Método que cura a los peces en un tanque
     */
    public void curarPeces() {
        for (Pez pez : fishes) {
            if (pez.isEnfermo()) {
                pez.curar();
            }
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
