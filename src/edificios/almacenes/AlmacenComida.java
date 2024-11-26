package edificios.almacenes;

/**
 * Clase que representa el Almacén de Comida
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class AlmacenComida {
    /**Cantidad almacenada de comida*/
    private int stock;
    /**Capacidad máxima del Almacén de Comida*/
    private int maxCap;

    /**Constructor de AlmacenComida*/
    public AlmacenComida(int maxCap, int stock) {
        this.stock = stock;
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
                this.setStock(this.getStock()+ammount);;
            }else{
                int leftovers = ammount - free;
                this.setStock(this.getStock()+free);
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

    /**
     * @param stock Nueva cantidad para el Stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Método que permite restar comida al Almacén
     * @param cantidad Cantidad a restar
     * @return Nueva cantidad
     */
    public int restarComida(int cantidad){
        int newStock = 0;
        if(stock > 0){
            if(cantidad > stock){
               return 0;
            }
            else{
                newStock = stock - cantidad;
            }
        }
        this.setStock(newStock);
        return newStock;
    }

    /**
     * Método para llenar el Almacén de comida
     * @return Cantidad de comida añadida para llenar el Almacén
     */
    public int setFull(){
        int emptyAmmount = this.getMaxCap() - this.getStock();
        this.setStock(this.getStock() + emptyAmmount);
        return emptyAmmount;
    }

    @Override
    public String toString() {
        return "Cantidad de comida almacenada  "  + this.stock + "\n" +
        "Capacidad máxima del almacén de comida " + this.maxCap ;
    }

}
