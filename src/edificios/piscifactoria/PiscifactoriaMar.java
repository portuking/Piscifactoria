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
    /**Almacén de comida vegetal*/
    /**ID de los tanques de la Piscifactoría*/
    private int tankID;
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
        this.tankID = super.getTankID();
        Tanque initialSeaTank = new Tanque(this.maxTankCapacity, this.tankID, this.getTipo());
        super.getTanques().add(initialSeaTank);
        this.comidaAnimal = new AlmacenComida(100, 0);
        this.comidaVegetal = new AlmacenComida(100, 0);
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
        Tanque initialSeaTank = new Tanque(this.maxTankCapacity, this.tankID, this.getTipo());
        super.getTanques().add(initialSeaTank);
        this.comidaAnimal = new AlmacenComida(100, stock);
        this.comidaVegetal = new AlmacenComida(100, stock);
        this.maxFood = 1000;
    }

    /**
     * Método que permite mejorar la capacidad de comida
     */
    @Override
    public void upgradeFood() {
        SISMonedas sisMonedas = SISMonedas.getInstance();
        int costoMejora = 200;
        int incrementoCap = 100;
        int capMax = this.maxFood;
        if(sisMonedas.getMonedas() >= costoMejora){
            boolean realizaMejora = false;
            if(this.comidaAnimal.getMaxCap() <  capMax){
                this.comidaAnimal.upgrade(incrementoCap);
                realizaMejora = true;
            }
            if(this.comidaVegetal.getMaxCap() <  capMax){
                this.comidaVegetal.upgrade(incrementoCap);
                realizaMejora = true;
            }if(realizaMejora){
                sisMonedas.pagar(costoMejora);
            }else{
                System.out.println("Ambos almacenes están al maximo");
            }
        }else{
            System.out.println("No tienes suficientes monedas para realizar la mejora");
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
                Tanque newTank = new Tanque(this.maxTankCapacity, (this.getTankID() + 1), this.getTipo());
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
