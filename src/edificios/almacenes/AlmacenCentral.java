package edificios.almacenes;

/**
 * Clase que representa el Almacén Central
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class AlmacenCentral {
    /**Capacidad máxima del Almacén central*/
    private int maxCap;
    /**Stock del Almacén central */
    private int stock;
    /**Precio de el Almacén Central */
    private int price;
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
        this.stock = 0;
        this.price = 2000;
        this.maxCap = 400;
    }

    /**
     * Método que reparte la mitad del Stock a cada Almacén
     */
    public void distribute(){
        if(this.stock > 1 ){
            int halfStock = this.stock / 2;
            this.warehouseA.addFood(halfStock);
            this.warehouseV.addFood(halfStock);
            this.stock = 0;
        }else{
            System.out.println("Operación Incorrecta: El número de Comida a añadir es muy pequeño");
        }
    }

    /**
     * Método que añade comida al Almacén central
     * @param food unidades de comida a añadir
     */
    public void addFood(int food){
        int free = this.maxCap - this.stock;
        if(food > 0){
            if(food >= 400){
                int leftovers = food - this.stock;
                this.stock = 400;
                System.out.println("Han sobrado " + leftovers + " unidades de comida");
                distribute();
            }else{
                
                if(free >= food) {
                    this.stock += food;
                    distribute();
                }else{
                    int added = food - free;
                    this.stock += added;
                    distribute();
                }
            }
        }else{
            System.out.println("Operacion Incorrecta: No se añade comida");
        }      
    }

    /**
     * Método que muestra la ocupación del Almacén Central
     */
    public void getOcuped(){
        System.out.println("--------Almacén Central--------");
        System.out.println("Comida actual: " + this.getStock());
        System.out.println("Capacidad máxima: " + this.getMaxCap());
        System.out.println("Porcentaje de ocupación [Comida actual / Máxima capacidad] " + (this.getStock()/this.getMaxCap())*100 + "%");
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
        return stock;
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
