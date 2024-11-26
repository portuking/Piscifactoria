package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edificios.piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

/**
 * IMPLEMENNTAR CLASE
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

    /**
     * Método que muestra un ménu con las Piscifactorías disponibles
     */
    public void menuPisc(){
        System.out.println("Seleccione una opción");
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
                System.out.println("Seleccione una opción");
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
    public void showIctio(){
        System.out.println("Seleccione un pez para ver más detalles:(0 para ancelar)");
        System.out.println("1. Besugo");
        System.out.println("2. Caballa");
        System.out.println("3. Carpa Plateada");
        System.out.println("4. Lenguado Europeo");  
        System.out.println("5. Lubina Europea");
        System.out.println("6. Lubina Rayada");
        System.out.println("7. Lucio del norte");
        System.out.println("8. Pejerrey");
        System.out.println("9. Perca Europea");
        System.out.println("10. Robalo");
        System.out.println("11. Salmón Atlantico");
        System.out.println("12. Salmón Chinook");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        
        if (opcion == 0) {
        System.out.println("Operación cancelada.");
        return;
        }

        switch (opcion) {
            case 1: 
                name = "Besugo";        
                break;
            case 2:
                name = "Caballa";
                break;
            case 3:
                name = "Carpa Plateada";
                break;
            case 4:
                name = "Lenguado Europeo";
                break;
            case 5:
                name = "Lubina Europea";
                break;
            case 6:
                name = "Lubina Rayada";                
                break;
            case 7:
                name = "Lucio del norte";               
                break;
            case 8:
                name = "Pejerrey";
                break;
            case 9:
                name = "Perca Europea";              
                break;
            case 10:
                name = "Róbalo";
                break;
            case 11:
                name = "Salmón Atlántico";
                break;
            case 12:
                name = "Salmón Chinook";
                break;    
        
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
                break;
        }

        PecesDatos pezSeleccionado = AlmacenPropiedades.getPropByName(name);

        if (pezSeleccionado != null) {
            System.out.println("-------- " + pezSeleccionado.getNombre() + " --------");
            System.out.println("Nombre científico: " + pezSeleccionado.getCientifico());
            System.out.println("Tipo: " + pezSeleccionado.getTipo().getValue());
            System.out.println("Coste: " + pezSeleccionado.getCoste());
            System.out.println("Valor de venta: " + pezSeleccionado.getMonedas());
            System.out.println("Huevos: " + pezSeleccionado.getHuevos());
            System.out.println("Ciclo: " + pezSeleccionado.getCiclo());
            System.out.println("Madurez: " + pezSeleccionado.getMadurez());
            System.out.println("Edad óptima: " + pezSeleccionado.getOptimo());
        }

    }


    public static void main(String[] args) {
        Simulador sim = new Simulador();
        //sim.init();
        sim.showIctio();
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

