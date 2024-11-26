package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edificios.almacenes.AlmacenCentral;
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
    private AlmacenCentral centralWarehouse;
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
        this.centralWarehouse = null;
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
    public Tanque selectTank(){
        Piscifactoria p = selectPisc();
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
     */
    public void showGeneralStatus() {
        System.out.println("========Estado General========");
        System.out.println("-- Día: " + this.days);
        System.out.println("-- Monedas: " + this.monedas.getMonedas());
        System.out.println("-- Estado de las Piscifactorías: ");
        for (Piscifactoria piscifactoria : fishFarms) {
            piscifactoria.showStatus();
        }
        if(centralWarehouse != null) {
            System.out.println("-- Estado del Almacén Central:");
            centralWarehouse.getOcuped();
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

    /**
     * Método que permite elegir uno de los Tanques de la Piscifactoría y muestra la información de los peces
     */
    public void showTankStatus() {
        Tanque selectedTank = selectTank();
        selectedTank.showfishestatus();
    }

    /**
     * Método que permite pasar varios días en las Piscifactorías
     * @param days Número de días a pasar
     */
    public void nextDay(int days) {
        for (int i = 0; i < days; i++) {
            for (Piscifactoria piscifactoria : fishFarms) {
                piscifactoria.nextDay();
            }
        }
    }

    /**
     * Método que añade comida al Almacén de comida elegido de la Piscifactoría dada
     */
    public void addFood() {
        Piscifactoria selectedFishFarm = selectPisc();
        boolean cancel = false;
        int warehouseType;
        while (!cancel) {
            try{
                System.out.println("Tipo de Almacén: ");
                System.out.println("1.- Almacén de comida animal");
                System.out.println("2.- Almacén de comida vegetal");
                System.out.println("0.- Cancelar");
                System.out.print("Seleccione una opción: ");
                warehouseType = sc.nextInt();
                if (warehouseType == 0) {
                    cancel = true;
                }else{
                    System.out.println("1.- 5");
                    System.out.println("2.- 10");
                    System.out.println("3.- 25");
                    System.out.println("4.- Llenar");
                    System.out.println("0.- Cancelar");
                    System.out.print("Seleccione una opción: ");
                    int option = sc.nextInt();
                    switch (option) {
                        case 1:
                            if(warehouseType == 1) {
                                selectedFishFarm.getWarehouseA().addFood(5);
                            }else if(warehouseType == 2){
                                selectedFishFarm.getWarehouseV().addFood(5);
                            }
                            this.monedas.pagar(5);
                            break;
                        case 2:
                            if(warehouseType == 1) {
                                selectedFishFarm.getWarehouseA().addFood(10);
                            }else if(warehouseType == 2){
                                selectedFishFarm.getWarehouseV().addFood(10);
                            }
                            this.monedas.pagar(10);
                            break;
                        case 3:
                            if(warehouseType == 1) {
                                selectedFishFarm.getWarehouseA().addFood(25);
                            }else if(warehouseType == 2){
                                selectedFishFarm.getWarehouseV().addFood(25);
                            }
                            this.monedas.pagar(24);
                            break;
                        case 4:
                            if(warehouseType == 1) {
                                selectedFishFarm.getWarehouseA().setFull();
                            }else if(warehouseType == 2){
                                selectedFishFarm.getWarehouseV().setFull();
                            }
                            this.monedas.pagar(option);                       
                        default:
                            break;
                    }   
                }
            }catch(InputMismatchException e) {
                System.out.println("Selección incorrecta");
            }   
        }
    }


    public static void main(String[] args) {
        Simulador sim = new Simulador();
        sim.init();
        //sim.menu();
        //sim.menuPisc();
        //System.out.println(sim.selectPisc());
        //Piscifactoria p = new Piscifactoria("Pisc1", false, 50);
        //p.listTanks();
        //sim.showGeneralStatus();
        //sim.showSpecificStatus();
        //sim.selectTank();
        //sim.showTankStatus();
    }
}
