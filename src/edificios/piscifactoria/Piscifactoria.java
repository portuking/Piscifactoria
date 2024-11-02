package edificios.piscifactoria;

import java.util.ArrayList;

import edificios.tanque.Tanque;
import peces.Pez;

public class Piscifactoria {
    private String name;
    private String type;
    private int maxTank = 10;
    private int maxFood = 0;
    private int currentFood = 0;
    private ArrayList<Tanque> tanque;
    
    public Piscifactoria(String name, String type) {
        this.name = name;
        this.type = type;

        if(type.equals("rio")){
            maxFood = 25;
            tanque = new ArrayList<>(25);
        }else if(type.equals("mar")){
            maxFood = 100;
            tanque = new ArrayList<>(100);
        }else{
            this.currentFood = maxFood;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxTank() {
        return maxTank;
    }

    public void setMaxTank(int maxTank) {
        this.maxTank = maxTank;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public void setMaxFood(int maxFood) {
        this.maxFood = maxFood;
    }

    public int getCurrentFood() {
        return currentFood;
    }

    public void setCurrentFood(int currentFood) {
        this.currentFood = currentFood;
    }

    public ArrayList<Tanque> getTanque() {
        return tanque;
    }

    public void setTanque(ArrayList<Tanque> tanque) {
        this.tanque = tanque;
    }

    public void showStatus(){
        System.out.println("==========Nombre: " + this.name + "==========");
    }

    public void showTankStatus(){
        for(Tanque tank : tanque){
            tank.showStatus();
        }
    }

    public void showFishStatus(){
        
    }
    
    public void showCapacity(int index){
        if(index < 0 && index >= tanque.size()){
            System.out.println("El numero de tanque no existe");
        }else{
            tanque.get(index).showCapacity(name);
        }
    }    

    public void showFood(){
        System.out.printf("Depósito de comida de la piscifactoría %s al %.2f%% de su capacidad. [%d/%d]%n",
                      this.name, ((double) currentFood / maxFood) * 100, currentFood, maxFood);
    }

    public void nextDay(){
         for (Tanque t : tanque) {
        for (Pez p : t.getFishes()) {
            if (p.isAlive()) {
                if (currentFood > 0) {
                    p.setEat(true);
                    currentFood--;
                } else {
                    p.setEat(false);
                }
                p.grow();
                p.isMilf();
            }
        }
    }
    }

    public void sellFish(){

    }

    public void upgradeFood(){

    }
}
