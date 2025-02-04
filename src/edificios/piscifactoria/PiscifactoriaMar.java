package edificios.piscifactoria;

import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;
import sistema.SISMonedas;

/**
 * Clase que representa una Piscifactoría de Mar
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class PiscifactoriaMar extends Piscifactoria{

    /**Precio de la Piscifactoría */
    private final int PRICE = 2000;
    /**ID de los tanques*/
    private int tankID;
    /**Almacén de comida vegetal*/
    private AlmacenComida wharehouseV;
    /**Almacén de comida Animal*/
    private AlmacenComida wharehouseA;
    /**Cantidad máxima de comida de la Piscifactoría*/
    private int maxFood;
    /**Cantidad máxima de Peces permitidos en los Tanques*/
    private int maxTankCapacity;

    /**
     * Constructor de Piscifactoría de mar
     * @param name Nombre de la Piscifactoría
     */
    public PiscifactoriaMar(String name) {
        super(name);
        this.maxTankCapacity = 100;
        this.tankID = 1;
        Tanque initialSeaTank = new Tanque(this.maxTankCapacity, this.tankID);
        super.getTanques().add(initialSeaTank);
        this.wharehouseA = new AlmacenComida(100, 0);
        this.wharehouseV = new AlmacenComida(100, 0);
        this.maxFood = 1000;
    }

    /**
     * Constructor de Piscifactoría de mar
     * @param name Nombre de la Piscifactoría
     * @param stock Cantidad inicial de comida de la Piscifactoría de Mar
     */
    public PiscifactoriaMar(String name, int stock) {
        super(name);
        this.maxTankCapacity = 100;
        this.tankID = 1;
        Tanque initialSeaTank = new Tanque(this.maxTankCapacity, this.tankID);
        super.getTanques().add(initialSeaTank);
        this.wharehouseA = new AlmacenComida(100, stock);
        this.wharehouseV = new AlmacenComida(100, stock);
        this.maxFood = 1000;
    }

    /**
     * Método que permite mejorar la capacidad de comida
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
     * Método que permite comprar un Tanque
     */
    @Override
    public void compraTanque() {
        if (this.canAddTanque()) {
            int numTanquesActual = super.getTanques().size();
            int costo = 150 * numTanquesActual;
            SISMonedas sistemaMonedas = SISMonedas.getInstance();
            if (sistemaMonedas.getMonedas() >= costo) {
                sistemaMonedas.pagar(costo);
                Tanque newTank = new Tanque(this.maxTankCapacity, (this.getTankID() + 1));
                super.getTanques().add(newTank);
            } else {
                System.out.println("No tienes suficientes monedas para comprar un tanque.");
            }
        } else {
        System.out.println("No se pueden añadir más tanques. Se ha alcanzado el máximo (10).");
    }
    }

    /**
     * @return Tipo de la Piscifactoría
     */
    @Override
    public String getTipo() {
        return "Mar";
    }

    /**
     * @return Precio de una Piscifactoría de Mar
     */
    @Override
    public int getPrecio() {
        return this.PRICE;
    }

    /**
     * @return Método que devuelve el ID del Tanque
     */
    public int getTankID(){
        return this.tankID;
    }
    
}
