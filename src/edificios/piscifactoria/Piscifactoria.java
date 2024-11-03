package edificios.piscifactoria;

import java.util.ArrayList;

import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;

public class Piscifactoria {
    private String name;
    private static int tankNum = 0;
    private int maxTank;
    private int maxFood;
    private int currentFood;
    private int maxCapacity;
    private ArrayList<Tanque> tanque;
    private AlmacenComida comidaVegetal;
    private AlmacenComida comidaAnimal;

    public Piscifactoria(String name, int maxFood, int tankCapacity) {
        this.name = name;
        this.maxTank = 10;
        this.maxFood = maxFood;
        this.currentFood = maxFood;
        this.maxCapacity = tankCapacity;
        this.comidaAnimal = new AlmacenComida(tankCapacity);
        this.comidaVegetal = new AlmacenComida(tankCapacity);
        tanque = new ArrayList<>(this.maxTank);
        tanque.add(new Tanque(maxCapacity, tankNum++));
    }

    public String getName() {
        return name;
    }

    public static int getTankNum() {
        return tankNum;
    }

    public int getMaxTank() {
        return maxTank;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public int getCurrentFood() {
        return currentFood;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<Tanque> getTanque() {
        return tanque;
    }

    public int occuped() {
        int okupas = 0;
        for (Tanque tanque2 : tanque) {
            okupas += tanque2.getFishes().size();
        }
        return okupas;
    }

    public int matureFishes() {
        int matureFishes = 0;
        for (Tanque tanque2 : tanque) {
            matureFishes += tanque2.matureFishes();
        }
        return matureFishes;
    }

    public int fishesF() {
        int fishesF = 0;
        for (Tanque tanque2 : tanque) {
            fishesF += tanque2.fishesF();
        }
        return fishesF;
    }

    public int fishesM() {
        int fishesM = 0;
        for (Tanque tanque2 : tanque) {
            fishesM += tanque2.fishesM();
        }
        return fishesM;
    }

    public int fertiles() {
        int fertiles = 0;
        for (Tanque tanque2 : tanque) {
            fertiles += tanque2.fertiles();
        }
        return fertiles;
    }

    public int maxFishes(){
        int maxFishes = 0;
        for (Tanque tanque2 : tanque) {
            maxFishes += tanque2.getMaxCapacity();
        }
        return maxFishes;
    }

    public int alimentedFishes() {
        int alimentedFishes = 0;
        for (Tanque tanque2 : tanque) {
            alimentedFishes += tanque2.alimentedFishes();
        }
        return alimentedFishes;
    }

    public int fishesAlive() {
        int fishesAlive = 0;
        for (Tanque tanque2 : tanque) {
            fishesAlive += tanque2.fishesAlive();
        }
        return fishesAlive;
    }

    public void showStatus() {
        System.out.println("===============  "+ this.name +" ===============");
        System.out.println("Tanques :" + tanque.size());
        System.out.println("Ocupación: peces / max " + (occuped()/maxFishes())+"%");
        System.out.println("Peces vivos: vivos / total " + (fishesAlive()/maxFishes()+"%"));
        System.out.println("Peces alimentados: alimentados / vivos " + (alimentedFishes()/fishesAlive()) + "%");
        System.out.println("Peces adultos: adultos / vivos " + (matureFishes()/fishesAlive()) + "%");
        System.out.println("Hembras/Machos " + (fishesF()/fishesM()) + "%");
        System.out.println("Fértiles: fertiles/vivos " + (fertiles()/fishesAlive()) + "%");
        System.out.println("Almacén de comida: actual/max"+ (currentFood/maxFood) +"%");
        
    }

    public void showTankStatus() {
        for (Tanque tanque2 : tanque) {
            tanque2.showStatus();
        }
    }

    public void showFishStatus(Tanque tanque) {
        tanque.showfishestatus();
    }

    public void showCapacity() {
        for (Tanque tanque2 : tanque) {
            System.out.println("Tanque " + tanque2.getTankNum() + "de la piscifactoria " + this.name + "al " + (occuped()/maxFishes())+"% de capacidad. [peces/espacios]");
        }
    }

    public void showFood() {
    
    }

    public void nextDay() {
    }

    public void sellFish() {

    }

    public void upgradeFood() {

    }
}
