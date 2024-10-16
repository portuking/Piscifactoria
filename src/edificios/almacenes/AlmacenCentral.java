package edificios.almacenes;

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
        this.warehouseA = new AlmacenComida(200);
        this.warehouseV = new AlmacenComida(200);
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
    @Override
    public String toString() {
        return "Precio del almacén central " + this.price + "\n" + 
        "Cantidad de comida Animal " + this.warehouseA + " /200"+"\n" + 
        "Cantidad de comida vegetal " + this.warehouseV + "/200" + "\n" + 
        "Cantidad de comida almacenada en el almacnén central " + "\n" +
        "Capacidad máxima del almacen central " + this.maxCap;
    }

    
}
