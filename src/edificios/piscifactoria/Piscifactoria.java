package edificios.piscifactoria;

import java.util.ArrayList;
import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;
import estadisticas.Estadisticas;

/**
 * Clase que representa la Piscifactoría
 * 
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public abstract class Piscifactoria {
    /** Nombre de la Piscifactoria */
    private String name;
    /** Capacidad máxima de comida de la Piscifactoria */
    private int maxFood;
    /** Comida actual de la Piscifactoria */
    private int currentFood;
    /** Máximo de Tanques de las Piscifactorías */
    private int maxTank;
    /** ID de los tanques */
    private int tankID;
    /** Tanques de la Piscifactoria */
    private ArrayList<Tanque> tanques;
    /** Almacén de Comida vegetal */
    protected AlmacenComida comidaVegetal;
    /** Almacén de Comida animal */
    protected AlmacenComida comidaAnimal;

    /**
     * Constructor de Piscifactoria
     * 
     * @param name Nombre de la Piscifactoria
     */
    protected Piscifactoria(String name) {
        this.name = name;
        this.maxTank = 10;
        this.tankID = 0;
        this.tanques = new ArrayList<>(this.maxTank);
    }

    /**
     * @return Ocupación de cada Tanque de la Piscifactoria
     */
    public int occuped() {
        int okupas = 0;
        for (Tanque tanque2 : tanques) {
            okupas += tanque2.getFishes().size();
        }
        return okupas;
    }

    /**
     * @return Número total de peces Maduros en los Tanques
     */
    public int matureFishes() {
        int matureFishes = 0;
        for (Tanque tanque2 : tanques) {
            matureFishes += tanque2.matureFishes();
        }
        return matureFishes;
    }

    /**
     * @return Número total de peces Hembra en los Tanques
     */
    public int fishesF() {
        int fishesF = 0;
        for (Tanque tanque2 : tanques) {
            fishesF += tanque2.fishesF();
        }
        return fishesF;
    }

    /**
     * @return Número total de peces Macho en los Tanques
     */
    public int fishesM() {
        int fishesM = 0;
        for (Tanque tanque2 : tanques) {
            fishesM += tanque2.fishesM();
        }
        return fishesM;
    }

    /**
     * @return Número total de peces Fertiles en los Tanques
     */
    public int fertiles() {
        int fertiles = 0;
        for (Tanque tanque2 : tanques) {
            fertiles += tanque2.fertiles();
        }
        return fertiles;
    }

    /**
     * @return Número total del máximo de peces de los Tanques
     */
    public int maxFishes() {
        int maxFishes = 0;
        for (Tanque tanque2 : tanques) {
            maxFishes += tanque2.getMaxCapacity();
        }
        return maxFishes;
    }

    /**
     * @return Número total de peces alimentados de los Tanques
     */
    public int alimentedFishes() {
        int alimentedFishes = 0;
        for (Tanque tanque2 : tanques) {
            alimentedFishes += tanque2.alimentedFishes();
        }
        return alimentedFishes;
    }

    /**
     * @return Número total de peces vivos de los Tanques
     */
    public int fishesAlive() {
        int fishesAlive = 0;
        for (Tanque tanque2 : tanques) {
            fishesAlive += tanque2.fishesAlive();
        }
        return fishesAlive;
    }

    /**
     * Método que muestra las estadisticas de la Piscifactoria
     */
    public void showStatus() {
        this.currentFood = this.comidaAnimal.getStock() + this.comidaVegetal.getStock();
        this.maxFood = this.comidaAnimal.getMaxCap() + this.comidaVegetal.getMaxCap();
        System.out.println("===============  " + this.name + " ===============");
        System.out.println("Tanques :" + this.tanques.size());
        if (this.occuped() > 0) {
            System.out.println("Ocupación: peces/max " + this.occuped() + "/" + this.maxFishes() + " " +((this.occuped()*100) / this.maxFishes())+ "%");
        } else {
            System.out.println("Ocupación: peces/max 0/0 0%");
        }
        if (this.fishesAlive() > 0) {
            System.out.println("Peces vivos: vivos/total " + this.fishesAlive() + "/" + this.occuped() + " " + ((this.fishesAlive()*100) / this.occuped()+"%"));
        } else {
            System.out.println("Peces vivos: vivos/total 0/0 0%");
        }
        if (this.alimentedFishes() > 0 && this.fishesAlive() > 0) {
            System.out.println("Peces alimentados: alimentados/vivos " + this.alimentedFishes() + "/" + this.fishesAlive() + " " + ((this.alimentedFishes()*100) / this.fishesAlive())+ "%");
        } else {
            System.out.println("Peces alimentados: alimentados/vivos 0/0 0%");
        }
        if (this.matureFishes() > 0 && this.fishesAlive() > 0) {
            System.out.println("Peces adultos: adultos/vivos " + this.matureFishes() + "/" + this.fishesAlive() + " " + ((this.matureFishes()*100) / this.fishesAlive()) + "%");
        } else {
            System.out.println("Peces adultos: adultos/vivos 0/0 0%");
        }
        System.out.println("Hembras/Machos: " + this.fishesF() + "/" + this.fishesM());
        System.out.println("Fértiles: fertiles/vivos: " + this.fertiles() + "/" + this.fishesAlive());
        
        if (this.currentFood > 0 && this.maxFood > 0) {
            System.out.println("Almacén de comida: actual/max " + (this.getCurrentFood() / this.getMaxFood()) * 100 + "%");
        } else {
            System.out.println("Almacén de comida: actual/max 0%");
        }
        System.out.println("Cantidad de comida actual: " + this.getCurrentFood());
        System.out.println("Cantidad de comida animal: " + this.getWarehouseA().getStock());
        System.out.println("Cantidad de comida Vegetal: " + this.getWarehouseV().getStock());
        System.out.println("Cantidad de comida máxima: " + this.getMaxFood());
        System.out.println("Cantidad máxima de comida Animal: " + this.getWarehouseA().getMaxCap());
        System.out.println("Cantidad máxima de comida Vegetal: " + this.getWarehouseV().getMaxCap());
    }

    /**
     * Método que muestra el estado de los tanques de la Piscifactoria
     */
    public void showTankStatus() {
        for (Tanque tanque2 : tanques) {
            tanque2.showStatus();
        }
    }

    /**
     * Muestra la información de los Peces de un Tanque determinado
     * 
     * @param tanque tanque a examinar
     */
    public void showFishStatus(Tanque tanque) {
        tanque.showfishestatus();
    }

    /**
     * Método que muestra la capacidad de un Tanque determinado
     */
    public void showCapacity(Tanque tanque) {
        tanque.showCapacity(this);
    }

    /**
     * Muestra el estado del Almacén de comida
     */
    public void showFood() {
        this.currentFood = comidaAnimal.getStock() + comidaVegetal.getStock();
        this.maxFood = comidaAnimal.getMaxCap() + comidaVegetal.getMaxCap();
        if (this.currentFood == 0 || this.maxFood == 0) {
            System.out.println("Depósito de comida de la piscifactoría" + this.name + "al 0% de su capacidad. [comida/max]");
        } else {
            System.out.println("Depósito de comida de la piscifactoría" + this.name + "al "+ (this.currentFood / this.maxFood) + "% de su capacidad. [comida/max]");
        }
    }

    /**
     * Método que hace que todos los Peces de la Piscifactoria pasen de día
     */
    public void nextDay(Estadisticas estadisticas) {
        for (Tanque tanque : tanques) {
            tanque.nextDay(this, estadisticas);
        }
    }

    /**
     * Método que vende todos los Peces que son adultos y están vivos
     */
    public void sellFish() {
        for (Tanque tanque2 : tanques) {
            tanque2.sellFishes();
        }
    }

    /**
     * Método que mejora el Almacén de comida
     */
    public abstract void upgradeFood();

    /**
     * Método que comprueba si es posible añadir un Tanque
     * @return si es posible añadirlo
     */
    public boolean canAddTanque() {
        if (this.tanques.size() < this.getMaxTank()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que permite comprar un Tanque para la Piscifactoria
     */
    public abstract void compraTanque();


    public void cleanTank(Tanque tank) {
        tank.cleanTank();
    }

    /**
     * @return Nombre de la Piscifactoria
     */
    public String getName() {
        return name;
    }

    /**
     * @return Número máximo de Tanques de la Piscifactoria
     */
    public int getMaxTank() {
        return maxTank;
    }

    /**
     * @return Capacidad máxima de Comida de la Piscifactoria
     */
    public int getMaxFood() {
        return maxFood;
    }

    /**
     * @return Comida actual de la Piscifactoria
     */
    public int getCurrentFood() {
        return currentFood;
    }

    /**
     * @return El id de un Tanque
     */
    public int getTankID() {
        return this.tankID;
    }

    /**
     * @return Lista de Tanques de la Piscifactoria
     */
    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    /**
     * @param index Número de la posición del Tanque
     * @return Devuelve un Tanque de la lista
     */
    public Tanque selectTank(int index) {
        return this.tanques.get(index);
    }

    /**
     * @return Devuelve el tipo de Piscifactoría
     */
    public abstract String getTipo();

    /**
     * @return El número de Tanques de la Piscifactoria
     */
    public int getNTanks() {
        return this.tanques.size();
    }

    /**
     * @return Almacén de comida Animal de la Piscifactoría
     */
    public AlmacenComida getWarehouseA() {
        return comidaAnimal;
    }

    /**
     * @return Almacén de comida Vegetal de la Piscifactoría
     */
    public AlmacenComida getWarehouseV() {
        return comidaVegetal;
    }

    /**
     * @return El precio de la Piscifactoría
     */
    public abstract int getPrecio();

    /**
     * Método que muestra una lista de los Tanques de la Piscifactoría
     */
    public void listTanks() {
        for (int i = 0; i < this.tanques.size(); i++) {
            System.out.println((i + 1) + ".- " + "Tipo: " + tanques.get(i).getTankType());
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + this.getName() + "\n" +
                "Tipo: " + this.getTipo() + "\n" + "Precio: " + this.getPrecio() + "\n"
                + "Máximo de Tanques permitidos: " +
                this.getMaxTank() + "\n" + "Almacenes de comida: " + "\n" + "Almacén de comida vegetal:"
                + "\n" + this.getWarehouseV().toString() + "\n" + "Almacén de comida animal: " + "\n"
                + this.getWarehouseA().toString() + "\n" + "Tanques: " + "\n" + this.getTanques();
    }
}
