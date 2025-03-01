package edificios.almacenes;

import java.util.List;
import edificios.piscifactoria.Piscifactoria;

/**
 * Clase que representa el Almacén Central
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class AlmacenCentral {
    /**Capacidad máxima del Almacén central*/
    private int maxCap;
    /**Precio de el Almacén Central */
    private final int price;
    /**Almacén de comida Animal*/
    private AlmacenComida warehouseA;
    /**Almacén de comida Vegetal*/
    private AlmacenComida warehouseV;

    /**
     * Constructor de Almacén Central
     */
    public AlmacenCentral() {
        this.warehouseA = new AlmacenComida(200, 0);
        this.warehouseV = new AlmacenComida(200, 0);
        this.price = 2000;
        this.maxCap = 400;
    }

    /**
     * Método que reparte la comida animal que hay en el almacen entre las piscifactorias
     * @param p lista de piscifactorias
     */
    public void repartirComidaAnimal(List<Piscifactoria> p) {
        int almacenes = 0;
        for (Piscifactoria piscifactoria : p) {
            if (piscifactoria.getWarehouseA() != null) {
                almacenes++;
            }
        }
    
        if (almacenes == 0) return; 
    
        int comidaTotal = this.getWarehouseA().getStock();
        int comidaRestante = comidaTotal; 
    
        for (Piscifactoria piscifactoria : p) {
            if (piscifactoria.getWarehouseA() != null) {
                int capacidadDisponible = piscifactoria.getWarehouseA().getSpace();
                int cantidadARepartir = Math.min(comidaRestante / almacenes, capacidadDisponible);
    
                piscifactoria.getWarehouseA().addFood(cantidadARepartir);
                comidaRestante -= cantidadARepartir;
                almacenes--;
            }
        }
    
        this.warehouseA.setStock(comidaRestante);
    }
    

    /**
     * Método que reparte la comida vegetal que hay en el almacén central entre las piscifactorias
     * @param p lista de piscifactorias
     */
    public void repartirComidaVegetal(List<Piscifactoria> p) {
        int almacenes = 0;
        for (Piscifactoria piscifactoria : p) {
            if (piscifactoria.getWarehouseV() != null) {
                almacenes++;
            }
        }
    
        if (almacenes == 0) return;
    
        int comidaTotal = this.getWarehouseV().getStock();
        int comidaRestante = comidaTotal;
    
        for (Piscifactoria piscifactoria : p) {
            if (piscifactoria.getWarehouseV() != null) {
                int capacidadDisponible = piscifactoria.getWarehouseV().getSpace();
                int cantidadARepartir = Math.min(comidaRestante / almacenes, capacidadDisponible);
    
                piscifactoria.getWarehouseV().addFood(cantidadARepartir);
                comidaRestante -= cantidadARepartir;
                almacenes--;
            }
        }
    
        this.warehouseV.setStock(comidaRestante);
    }
    

    /**
     * Método que añade comida vegetal al Almacén Central
     * @param ammount Cantidad de comida a añadir
     */
    public void addVegtalFood(int ammount){
        this.warehouseV.addFood(ammount);
    }

    /**
     * Método que añade comida animal al Almacén Central
     * @param ammount Cantidad de comida a añadir
     */
    public void addAnimalFood(int ammount) {
        this.warehouseA.addFood(ammount);
    }

    /**
     * Método que muestra la ocupación del Almacén Central
     */
    public void getOcuped(){
        System.out.println("--------Almacén Central--------");
        System.out.println("Comida actual: " + this.getStock());
        System.out.println("Capacidad máxima: " + this.getMaxCap());
        System.out.println("Porcentaje de ocupación general [Comida actual / Máxima capacidad] " + this.getStock() + "/" + this.getMaxCap() + " " +((this.getStock()*100)/this.getMaxCap())+"%");
        System.out.println("Porcentaje de ocupación de comida animal [Comida actual / Máxima capacidad] " + this.warehouseA.getStock() + "/" + this.warehouseA.getMaxCap() + " " + ((this.warehouseA.getStock()*100)/this.getWarehouseA().getMaxCap())+"%");
        System.out.println("Porcentaje de ocupación de comida vegetal [Comida actual / Máxima capacidad] " + this.warehouseV.getStock() + "/" + this.warehouseV.getMaxCap() + " " +((this.getWarehouseV().getStock()*100)/this.warehouseV.getMaxCap())+"%");
    }

    /**
     * Método que añade 100 espacios al Almacén central:
     * 50 espacios para la comida animal
     * 50 espacios para la comida vegetal
     */
    public void upgrade() {
        this.maxCap += 100;
        this.warehouseA.upgrade(50);
        this.warehouseV.upgrade(50); 
    }

    /**
     * @return Devuelve la capacidad maxima del almacen
     */
    public int getMaxCap() {
        return maxCap;
    }

    /**
     * @return Devuelve la comida que hay
     */
    public int getStock() {
        return this.warehouseA.getStock() + this.warehouseV.getStock();
    }

    /**
     * @return Almacén de comida animal
     */
    public AlmacenComida getWarehouseA() {
        return warehouseA;
    }

    /**
     * @return Almacén de comida vegetal
     */
    public AlmacenComida getWarehouseV() {
        return warehouseV;
    }

    /**
     * @param maximo Capacidad máxima del Almacén Central
     */
    public void setMaxCap(int maximo) {
        this.maxCap = maximo;
    }

    @Override
    public String toString() {
        return "Precio del almacén central " + this.price + "\n" + 
        "Cantidad de comida Animal " + this.warehouseA + " /200"+"\n" + 
        "Cantidad de comida vegetal " + this.warehouseV + "/200" + "\n" + 
        "Cantidad de comida almacenada en el almacnén central " + "\n" +
        "Capacidad máxima del almacen central " + this.maxCap;
    }
}
