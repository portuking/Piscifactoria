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

    /**
     * Método que inicializa el Sistema
     */
    public void init(){
        try{
            System.out.println("Escriba el nombre de su empresa/partida:");
            this.name = sc.nextLine();
        }catch(InputMismatchException e) {
            System.out.println("El nombre es incorrecto");
        }
        this.days = 0;
        this.fishFarms = new ArrayList<Piscifactoria>();
        this.monedas = SISMonedas.getInstance();
        this.monedas.setMonedas(100);
        Piscifactoria fishFarm = new Piscifactoria("Piscifactoria1", false, 25);
        fishFarms.add(fishFarm);
    }

    /**
     * Método que muestra el menú general
     */
    public void menu(){
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado Tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanques");
        System.out.println("12. Mejorar");
        System.out.println("13. Pasar varios días");
        System.out.println("14. Salir");
    }

    public void menuPisc(){
        try{
            System.out.println("Seleccione una opción");
            System.out.println("--------------------------- Piscifactorías ---------------------------");
            System.out.println("[Peces vivos / Peces totales / Espacio total]");
            for (int i = 0; i < fishFarms.size(); i++) {
                System.out.println(i + ".- " + fishFarms.get(i) );
            }
        }catch(InputMismatchException e){
            System.out.println("Elección incorrecta");
        }
    }
}
