package edificios.piscifactoria;

import java.util.ArrayList;

import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;
import sistema.SISMonedas;
/**
 * REVISAR ATRIBUTOS
 * REVISAR MÉTODOS ANOTADOS
 * IMPLEMENTAR MÉTODO nextDay()
 * IMPLEMENTAR toString()
 */
public class Piscifactoria {
    /**Nombre de la Piscifactoria */
    private String name;
    /**Número de Tanque*/
    private static int tankNum = 0;
    /**Tipo de Piscifactoria */
    private boolean tipo;
    /**Máximo de Tanques permitidos en la Piscifactoria*/
    private int maxTank;
    /**Capacidad máxima de comida de la Piscifactoria */
    private int maxFood;
    /**Comida actual de la Piscifactoria */
    private int currentFood;
    /** */
    private int maxCapacity;
    /**Precio de la Piscifactoria */
    private int precio;
    /**Tanques de la Piscifactoria */
    private ArrayList<Tanque> tanque;
    /**Almacén de Comida vegetal */
    private AlmacenComida comidaVegetal;
    /**Almacén de Comida animal */
    private AlmacenComida comidaAnimal;

    /**
     * Constructor de Piscifactoria
     * @param name Nombre de la Piscifactoria 
     * @param tipo Tipo de la Piscifactoria
     */
    public Piscifactoria(String name, boolean tipo) {
        this.name = name;
        this.maxTank = 10;
        this.maxCapacity = tankCapacity;
        this.tipo = tipo;
        tanque = new ArrayList<>(this.maxTank);
        tanque.add(new Tanque(maxCapacity, tankNum++));
        if (tipo) {
            this.comidaAnimal = new AlmacenComida(25);
            this.comidaVegetal = new AlmacenComida(25);
            this.precio = 500;
        } else {
            this.comidaAnimal = new AlmacenComida(100);
            this.comidaVegetal = new AlmacenComida(100);
            this.precio = 2000;
        }
    }

    /**
     * @return Nombre de la Piscifactoria
     */
    public String getName() {
        return name;
    }

    /**
     * @return Número de Tanque de la Piscifactoria
     */
    public static int getTankNum() {
        return tankNum;
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
     * @return Capacidad máxima de la Piscifactoria
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * @return Lista de Tanques de la Piscifactoria
     */
    public ArrayList<Tanque> getTanque() {
        return tanque;
    }

    /**
     * @return Ocupación de cada Tanque de la Piscifactoria
     */
    public int occuped() {
        int okupas = 0;
        for (Tanque tanque2 : tanque) {
            okupas += tanque2.getFishes().size();
        }
        return okupas;
    }

    /**
     * @return Número total de peces Maduros en los Tanques
     */
    public int matureFishes() {
        int matureFishes = 0;
        for (Tanque tanque2 : tanque) {
            matureFishes += tanque2.matureFishes();
        }
        return matureFishes;
    }

    /**
     * @return Número total de peces Hembra en los Tanques
     */
    public int fishesF() {
        int fishesF = 0;
        for (Tanque tanque2 : tanque) {
            fishesF += tanque2.fishesF();
        }
        return fishesF;
    }

    /**
     * @return Número total de peces Macho en los Tanques
     */
    public int fishesM() {
        int fishesM = 0;
        for (Tanque tanque2 : tanque) {
            fishesM += tanque2.fishesM();
        }
        return fishesM;
    }

    /**
     * @return Número total de peces Fertiles en los Tanques
     */
    public int fertiles() {
        int fertiles = 0;
        for (Tanque tanque2 : tanque) {
            fertiles += tanque2.fertiles();
        }
        return fertiles;
    }

    /**
     * @return Número total del máximo de peces de los Tanques
     */
    public int maxFishes() {
        int maxFishes = 0;
        for (Tanque tanque2 : tanque) {
            maxFishes += tanque2.getMaxCapacity();
        }
        return maxFishes;
    }

    /**
     * @return Número total de peces alimentados de los Tanques
     */
    public int alimentedFishes() {
        int alimentedFishes = 0;
        for (Tanque tanque2 : tanque) {
            alimentedFishes += tanque2.alimentedFishes();
        }
        return alimentedFishes;
    }

    /**
     * @return Número total de peces vivos de los Tanques
     */
    public int fishesAlive() {
        int fishesAlive = 0;
        for (Tanque tanque2 : tanque) {
            fishesAlive += tanque2.fishesAlive();
        }
        return fishesAlive;
    }

    /**
     * Método que muestra las estadisticas de la Piscifactoria
     */
    public void showStatus() {
        System.out.println("===============  " + this.name + " ===============");
        System.out.println("Tanques :" + tanque.size());
        System.out.println("Ocupación: peces / max " + (occuped() / maxFishes()) + "%");
        System.out.println("Peces vivos: vivos / total " + (fishesAlive() / maxFishes() + "%"));
        System.out.println("Peces alimentados: alimentados / vivos " + (alimentedFishes() / fishesAlive()) + "%");
        System.out.println("Peces adultos: adultos / vivos " + (matureFishes() / fishesAlive()) + "%");
        System.out.println("Hembras/Machos " + (fishesF() / fishesM()) + "%");
        System.out.println("Fértiles: fertiles/vivos " + (fertiles() / fishesAlive()) + "%");
        System.out.println("Almacén de comida: actual/max" + (currentFood / maxFood) + "%");

    }

    /**
     * Método que muestra el estado de los tanques de la Piscifactoria
     */
    public void showTankStatus() {
        for (Tanque tanque2 : tanque) {
            tanque2.showStatus();
        }
    }

    /**
     * Muestra la información de los Peces de un Tanque determinado
     * @param tanque tanque a examinar
     * -------------------------------------------------REVISAR
     */
    public void showFishStatus(Tanque tanque) {
        tanque.showfishestatus();
    }

    /**
     * Método que muestra la capacidad de un Tanque determinado
     * NECESITA CORRECCIÓN:
     * ESTE MÉTODO MOSTRA A OCUPACIÓN DE TODOS OS TANQUES
     */
    public void showCapacity() {
        for (Tanque tanque2 : tanque) {
            System.out.println("Tanque " + tanque2.getTankNum() + "de la piscifactoria " + this.name + "al "
                    + (occuped() / maxFishes()) + "% de capacidad. [peces/espacios]");
        }
    }

    /**
     * Muestra el estado del Almacén de comida 
     * REVISAR
     */
    public void showFood() {
        int stock = comidaAnimal.getStock() + comidaVegetal.getStock();
        int maxFood = comidaAnimal.getMaxCap() + comidaVegetal.getMaxCap();
        if (stock == 0 || maxFood == 0) {
            System.out.println(
                    "Depósito de comida de la piscifactoría" + this.name + "al 0% de su capacidad. [comida/max]");
        } else {
            System.out.println("Depósito de comida de la piscifactoría" + this.name + "al " + (stock / maxFood)
                    + "% de su capacidad. [comida/max]");
        }
    }

    /**
     * Método que hace que todos los Peces de la Piscifactoria pasen de día
     * -------------------IMPLEMENTAR
     */
    public void nextDay() {
    
    }

    /**
     * Método que vende todos los Peces que son adultos y están vivos
     */
    public void sellFish() {
        for (Tanque tanque2 : tanque) {
            tanque2.sellFishes();
        }
    }

    /**
     * Método que mejora el Almacén de comida
     */
    public void upgradeFood() {
        SISMonedas monedas = SISMonedas.getInstance();
        if (this.tipo) {
            if (this.comidaAnimal.getMaxCap() <= 250 && this.comidaVegetal.getMaxCap() <= 250) {
                monedas.pagar(50);
                this.comidaAnimal.upgrade(this.comidaAnimal.getMaxCap() + 25);
                this.comidaVegetal.upgrade(this.comidaVegetal.getMaxCap() + 25);
            }

        } else {
            if (this.comidaAnimal.getMaxCap() <= 1000 && this.comidaVegetal.getMaxCap() <= 1000) {
                monedas.pagar(200);
                this.comidaAnimal.upgrade(this.comidaAnimal.getMaxCap() + 100);
                this.comidaVegetal.upgrade(this.comidaVegetal.getMaxCap() + 100);
            }
        }
    }

    /**
     * Método que añade un Tanque
     * @return si es posible añadirlo
     * ---------------------------------REVISAR
     */
    public boolean addTanque() {
        if (tanque.size() < this.maxTank) {
            this.tanque.add(new Tanque(this.maxTank, tankNum + 1));
            return true;
        }
        return false;
    }

    /**
     * Método que permite comprar un Tanque para la Piscifactoria
     * ---------------------------------------------------------------REVISAR
     */
    public void compraTanque() {
        SISMonedas monedas = SISMonedas.getInstance();
        if (tipo) {
            if (addTanque()) {
                monedas.pagar(tanque.size() - 1 * 150);

            }
        } else {
            if (addTanque()) {
                monedas.pagar(tanque.size() - 1 * 600);
            }
        }

    }

}
