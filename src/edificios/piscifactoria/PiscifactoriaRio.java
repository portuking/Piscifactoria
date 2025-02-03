package edificios.piscifactoria;

import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;

/**
 * Clase que representa una Piscifactoría de Río
 */
public class PiscifactoriaRio extends Piscifactoria{
    /**Precio de las Piscifactorías de Río*/
    private final int PRICE = 500;
    /**ID de los tanques de la Piscifactoría*/
    private int tankID;
    /**Almacén de comida animal de la Piscifactoría*/
    private AlmacenComida wharehouseA;
    /**Almacén de comida vegetal de la Piscifactoría*/
    private AlmacenComida wharehouseV;
    /**Capacidad máxima de comida de la Piscifactoría*/
    private int maxFood;
    /**Capacidad máxima de Peces permitidos en los Tanques*/
    private int maxTankCapacity;

    /**
     * Constructor de una Piscifactoría de Río
     * @param name Nombre de la Piscifactoría
     */
    public PiscifactoriaRio(String name) {
        super(name);
        this.tankID = 1;
        this.maxTankCapacity = 25;
        this.maxFood = 250;
        Tanque initialRiverTank = new Tanque(this.maxTankCapacity, this.tankID);
        super.getTanques().add(initialRiverTank);
        this.wharehouseA = new AlmacenComida(25, 0);
        this.wharehouseV = new AlmacenComida(25, 0);
    }
    
    /**
     * Método que permite mejorar los Almacenes de la Piscifactoría
     */
    @Override
    public void upgradeFood() {
        int wharehouseAUpgradeable = this.maxFood - this.wharehouseA.getMaxCap();
        int wharehouseVUpgradeable = this.maxFood - this.wharehouseV.getMaxCap();
        if(this.wharehouseA.getMaxCap() < this.maxFood){
            if(wharehouseAUpgradeable > 50) {
                this.wharehouseA.upgrade(50);
            }else{
                this.wharehouseA.upgrade(wharehouseAUpgradeable);
            }
        }
        if(this.wharehouseV.getMaxCap() > this.maxFood) {
            if(wharehouseVUpgradeable > 50) {
                this.wharehouseV.upgrade(50);
            }else{
                this.wharehouseV.upgrade(wharehouseVUpgradeable);
            }
        }
    }

    /**
     * Método que permite comprar un tanque a la Piscifactoría
     */
    @Override
    public void compraTanque() {
        if(this.canAddTanque()) {
            Tanque newTank = new Tanque(this.maxTankCapacity, (this.getTankID() + 1));
            super.getTanques().add(newTank);
        }
    }

    /**
     * @return Tipo de la Piscifactoría
     */
    @Override
    public String getTipo() {
        return "Río";
    }

    /**
     * @return Precio de la Piscifactoría
     */
    @Override
    public int getPrecio() {
        return this.PRICE;
    }

    public int getTankID(){
        return this.tankID;
    }
}
