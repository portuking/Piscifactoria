package edificios.almacenes;

public class AlmacenComida {
    /**Cantidad almacenada de comida*/
    private int stock;
    /**Capacidad máxima del Almacén de Comida*/
    private int maxCap;

    /**Constructor de AlmacenComida*/
    public AlmacenComida(int maxCap) {
        this.stock = 0;
        this.maxCap = maxCap;
    }

    /** 
     * Método que añade comida al Almacén
     * @param ammount Cantidad de Comida que se añade al Almacén
     */
    public void addFood(int ammount){
        int free = this.maxCap - this.stock;
        if(ammount > 0) {
            if(ammount <= free){
                this.stock += ammount;
            }else{
                int leftovers = ammount - free;
                this.stock += free;
                System.out.println("Han sobrado " + leftovers + " unidades de comida");
            }
        }else{
            System.out.println("Operación Incorrecta: No se añade comida");
        }
    }    

    /**
     * Método que aumenta el espacio de almacenamiento del Almacén
     * @param newCapacity Número de espacios a añadir
     */
    public void upgrade(int newCapacity) {
        this.maxCap += newCapacity;
    }
    @Override
    public String toString() {
        return "Cantidad de comida almacenada  "  + this.stock + "\n" +
        "Capacidad máxima del almacén de comida " + this.maxCap ;
    }

}
