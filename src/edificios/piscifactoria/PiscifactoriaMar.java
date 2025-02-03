package edificios.piscifactoria;

import edificios.almacenes.AlmacenComida;

public class PiscifactoriaMar extends Piscifactoria{

    /**Precio de la Piscifactoría */
    private final int PRICE = 500;
    /**Almacén de comida vegetal*/
    private AlmacenComida wharehouseV;
    /**Almacén de comida Animal*/
    private AlmacenComida wharehouseA;

    public PiscifactoriaMar(String name, int initialFood) {
        super(name);
        this.wharehouseA = new AlmacenComida(100, initialFood);
        this.wharehouseV = new AlmacenComida(100, initialFood);
    }

    @Override
    public void upgradeFood() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'upgradeFood'");
    }

    @Override
    public void compraTanque() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compraTanque'");
    }

    @Override
    public String getTipo() {
        return "Mar";
    }

    @Override
    public int getPrecio() {
        return this.PRICE;
    }
    
}
