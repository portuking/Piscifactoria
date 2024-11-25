package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edificios.piscifactoria.Piscifactoria;
import edificios.tanque.Tanque;

/**
 * Clase que representa el Simulador
 * @author Manuel Abalo Rietz
 * @author Adrián Ces López
 * @author Pablo Dopazo Suárez
 */
public class Simulador {
    /**Días que han pasado */
    private int days;
    /**Piscifactorías del juego*/
    private List<Piscifactoria> fishFarms;
    /**Nombre de la empresa/partida*/
    private String name;
    /**Sistema de monedas del juego*/
    private SISMonedas monedas;
    /**Scanner para manipular las entradas del Usuario*/
    Scanner sc = new Scanner(System.in);

    /**
     * Método que inicializa el Sistema
     */
    public void init(){
        try{
            System.out.print("Escriba el nombre de su empresa/partida: ");
            this.name = sc.nextLine();
        }catch(InputMismatchException e) {
            System.out.println("El nombre es incorrecto");
        }
        this.days = 0;
        this.fishFarms = new ArrayList<>();
        this.monedas = SISMonedas.getInstance();
        this.monedas.setMonedas(100);
        Piscifactoria fishFarm = new Piscifactoria("Piscifactoria1", true, 25);
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

    /**
     * Método que muestra un ménu con las Piscifactorías disponibles
     */
    public void menuPisc(){
        System.out.println("--------------------------- Piscifactorías ---------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (int i = 0; i < fishFarms.size(); i++) {
            System.out.println((i + 1) + ".- " + fishFarms.get(i).getName() + "["+fishFarms.get(i).fishesAlive()+"/"+ fishFarms.get(i).occuped()+"/"+fishFarms.get(i).maxFishes()+"]");
        }
        System.out.println("0.- Cancelar");
    }

    /**
     * Método que permite seleccionar una piscifactoria
     * @return Piscifactoría seleccionada
     */
    public Piscifactoria selectPisc(){
        this.menuPisc();
        Piscifactoria selected = null;
        while (selected == null) {
            try{
                System.out.print("Seleccione una opción: ");
                int option = sc.nextInt();
                if(option == 0){
                    break;
                }
                if(option > 0 && option -1 < this.fishFarms.size()) {
                    selected = this.fishFarms.get(option -1);
                }
            }catch(InputMismatchException e){
                System.out.println("Entrada no válida");
            }
        }
        return selected;
    }

    /**
     * Método que muestra un menú de Tanques de una Piscifactoría y permite seleccionar uno
     * @param p Piscifactoría de la que se va a devolver el Tanque
     * @return Tanque seleccionado
     */
    public Tanque selectTank(Piscifactoria p){
        System.out.println("========Menú Tanques========");
        p.listTanks();
        System.out.println("0.- Cancelar");
        Tanque selected = null;
        while (selected == null) {
            try {
                System.out.print("Seleccione una opción: ");
                int option = sc.nextInt();
                if (option == 0) {
                    break;
                }
                if (option > 0 && option -1 < p.getNTanks()) {
                    selected = p.selectTank(option -1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida");
            }
        }
        return selected;
    }

    /**
     * Método que muestra el estado general del Sistema
     * Buscar como saber si ten Almacén Central
     */
    public void showGeneralStatus() {
        System.out.println("========Estado General========");
        System.out.println("-- Día: " + this.days);
        System.out.println("-- Monedas: " + this.monedas.getMonedas());
        System.out.println("-- Estado de las Piscifactorías: ");
        for (Piscifactoria piscifactoria : fishFarms) {
            piscifactoria.showStatus();
        }
    }

    /**
     * Método que permite seleccionar una Piscifactoría y muestra toda la información de sus tanques
     */
    public void showSpecificStatus(){
        Piscifactoria selectedPisc = selectPisc();
        System.out.println("");
        if(selectedPisc != null){
            selectedPisc.showTankStatus();
        }
    }


    public static void main(String[] args) {
        Simulador sim = new Simulador();
        sim.init();
        //sim.menu();
        //sim.menuPisc();
        //System.out.println(sim.selectPisc());
        Piscifactoria p = new Piscifactoria("Pisc1", false, 50);
        //p.listTanks();
        //sim.showGeneralStatus();
        //sim.showSpecificStatus();
        sim.selectTank(p);
    }
}
