package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edificios.piscifactoria.Piscifactoria;

/**
 * IMPLEMENNTAR CLASE
 */
public class Simulador {
    /**Días que han pasado */
    private int days;
    /**Piscifactorías del juego*/
    private List fishFarms;
    /**Nombre de la empresa/partida*/
    private String name;
    /**Sistema de monedas del juego*/
    private SISMonedas monedas;
    Scanner sc = new Scanner(System.in);

    public void init(){
        try{
            System.out.println("Escriba el nombre de su empresa/partida:");
            this.name = sc.nextLine();
        }catch(InputMismatchException e) {
            System.out.println("El nombre es incorrecto");
        }
        this.days = 0;
        this.fishFarms = new ArrayList<>();
        this.monedas = SISMonedas.getInstance();
        this.monedas.setMonedas(100);
        Piscifactoria fishFarm = new Piscifactoria("Piscifactoria1", days, days, false);

    }
}
