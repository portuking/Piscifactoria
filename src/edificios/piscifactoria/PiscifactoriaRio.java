package edificios.piscifactoria;

import edificios.almacenes.AlmacenComida;
import edificios.tanque.Tanque;
import sistema.SISMonedas;

/**
 * Clase que representa una Piscifactoría de Río
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class PiscifactoriaRio extends Piscifactoria{
    /**Precio de las Piscifactorías de Río*/
    private final int PRICE = 500;
    /**ID de los tanques de la Piscifactoría*/
    private int tankID;
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
        this.tankID = super.getTankID();
        this.maxTankCapacity = 25;
        this.maxFood = 250;
        Tanque initialRiverTank = new Tanque(this.maxTankCapacity, this.tankID, true, this);
        super.getTanques().add(initialRiverTank);
        this.comidaAnimal = new AlmacenComida(25, 0);
        this.comidaVegetal = new AlmacenComida(25, 0);
    }

    /**
     * Constructor de una Piscifactoría de Río
     * @param name Nombre de la Piscifactoría
     * @param stock Cantidad de comida inicial de la Piscifactoría de río
     */
    public PiscifactoriaRio(String name, int stock) {
        super(name);
        this.tankID = 1;
        this.maxTankCapacity = 25;
        this.maxFood = 250;
        Tanque initialRiverTank = new Tanque(this.maxTankCapacity, this.tankID, true, this);
        super.getTanques().add(initialRiverTank);
        this.comidaAnimal = new AlmacenComida(25, stock);
        this.comidaVegetal = new AlmacenComida(25, stock);
    }
    
    /**
     * Método que permite mejorar los Almacenes de la Piscifactoría
     */
    @Override
    public void upgradeFood() {
        SISMonedas sisMonedas = SISMonedas.getInstance();
        int costoMejora = 50;
        int incrementoCap = 25;
        int capMax = this.maxFood;
        if(sisMonedas.getMonedas() >= costoMejora){
            boolean realizaMejora = false;
            if(this.comidaAnimal.getMaxCap() <  capMax && this.comidaVegetal.getMaxCap() <  capMax){
                this.comidaAnimal.upgrade(incrementoCap);
                this.comidaVegetal.upgrade(incrementoCap);
                realizaMejora = true;
            }else{
                realizaMejora = false;
            }
            if(realizaMejora){
                System.out.println("Almacén de la Piscifactoría "+super.getName()+ " mejorado. Su capacidad ha aumentado en "+ incrementoCap + " hasta un total de " + this.comidaAnimal.getMaxCap());
                sisMonedas.pagar(costoMejora);
            }else{
                System.out.println("Ambos almacenes están al maximo");
            }
        }else{
            System.out.println("No tienes suficientes monedas para realizar la mejora");
        }
    }

    /**
     * Método que permite comprar un tanque a la Piscifactoría
     */
    @Override
    public void compraTanque() {
        if (this.canAddTanque()) {
            int numTanquesActual = super.getTanques().size();
            int costo = 150 * numTanquesActual;
            SISMonedas sistemaMonedas = SISMonedas.getInstance();
            if (sistemaMonedas.getMonedas() >= costo) {
                sistemaMonedas.pagar(costo);
                Tanque newTank = new Tanque(this.maxTankCapacity, (this.getTankID() + 1), true, this);
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
