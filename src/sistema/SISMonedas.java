package sistema;

public class SISMonedas {

    public static SISMonedas saldo = null;
    protected int monedas;
    
    private SISMonedas(){
       monedas = 100;
    }

    public static SISMonedas getInstance(){
        if(saldo == null){
            saldo = new SISMonedas();
        }
        return saldo;
    }    

    public void pagar(int cantidad){
        if (this.monedas >= cantidad) {
        this.monedas -= cantidad;
        System.out.println("Pago realizado con exito");
        } else {
        System.out.println("No tienes suficientes momendas para comprar");
        }
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public static SISMonedas getSaldo() {
        return saldo;
    }

    public static void setSaldo(SISMonedas saldo) {
        SISMonedas.saldo = saldo;
    }

}
