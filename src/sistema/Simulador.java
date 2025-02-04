package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edificios.almacenes.AlmacenCentral;
import edificios.almacenes.AlmacenComida;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
import estadisticas.Estadisticas;
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
    /**Almacén de comida central del Sistema*/
    private AlmacenCentral centralWarehouse;
    /**Array que contiene los peces disponibles*/
    private static String[] pecesDisponibles = {
        propiedades.AlmacenPropiedades.BESUGO.getNombre(),
        propiedades.AlmacenPropiedades.CABALLA.getNombre(),
        propiedades.AlmacenPropiedades.CARPA_PLATEADA.getNombre(),
        propiedades.AlmacenPropiedades.LENGUADO_EUROPEO.getNombre(),
        propiedades.AlmacenPropiedades.LUBINA_EUROPEA.getNombre(),
        propiedades.AlmacenPropiedades.LUBINA_RAYADA.getNombre(),
        propiedades.AlmacenPropiedades.LUCIO_NORTE.getNombre(),
        propiedades.AlmacenPropiedades.PEJERREY.getNombre(),
        propiedades.AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
        propiedades.AlmacenPropiedades.ROBALO.getNombre(),
        propiedades.AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
        propiedades.AlmacenPropiedades.SALMON_CHINOOK.getNombre()};
    /**INstancia de clase estadisticas*/
    public estadisticas.Estadisticas stats = new Estadisticas(pecesDisponibles);

    Scanner sc = new Scanner(System.in);

    /**
     * Método que inicializa el Sistema
     */
    public void init(){
        try{
            System.out.print("Escriba el nombre de su empresa/partida: ");
            this.name = sc.nextLine();
            System.out.print("Escriba el nombre de su primera Piscifactoría: ");
            String fishfarmName = sc.nextLine();
            this.days = 0;
            this.fishFarms = new ArrayList<Piscifactoria>();
            this.monedas = SISMonedas.getInstance();
            this.monedas.setMonedas(100);
            Piscifactoria initialRiverFishfarm = new PiscifactoriaRio(fishfarmName, 25);
            fishFarms.add(initialRiverFishfarm);
            this.centralWarehouse = null;
        }catch(InputMismatchException e) {
            System.out.println("El nombre es incorrecto");
        }
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

    public void menupeces(){
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
    }

    public void showIctio(){
        menupeces();                      
        int opcion = sc.nextInt();
        
        if (opcion == 0) {
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
        boolean cancel = false;
        int warehouseType;
        if(centralWarehouse == null){
            Piscifactoria selectedFishFarm = selectPisc();    
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
                        int ammount = 0;
                        int discount = 0;
                        switch (option) {
                            case 1:
                                if(warehouseType == 1) {
                                    if(selectedFishFarm.getWarehouseA().getSpace() < 5) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }else{
                                        ammount = 5;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }
                                }else if(warehouseType == 2){
                                    if(selectedFishFarm.getWarehouseV().getSpace() < 5) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }else{
                                        ammount = 5;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                }
                                this.monedas.pagar(ammount);
                                break;
                            case 2:
                                if(warehouseType == 1) {
                                    if(selectedFishFarm.getWarehouseA().getSpace() < 10) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }else{
                                        ammount = 10;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    } 
                                }else if(warehouseType == 2){
                                    if(selectedFishFarm.getWarehouseV().getSpace() < 10) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }else{
                                        ammount = 10;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                }
                                this.monedas.pagar(ammount);
                                break;
                            case 3:
                                if(warehouseType == 1) {
                                    if(selectedFishFarm.getWarehouseA().getSpace() < 25) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }else{
                                        ammount = 25;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    } 
                                }else if(warehouseType == 2){
                                    if(selectedFishFarm.getWarehouseV().getSpace() < 25) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }else{
                                        ammount = 25;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                    if(ammount > 25) {
                                        discount = (ammount / 25) * 5;
                                        monedas.pagar(discount);
                                    }else{
                                        monedas.pagar(ammount);
                                    }
                                }
                                this.monedas.pagar(20);
                                break;
                            case 4:
                                if(warehouseType == 1) {
                                    ammount = selectedFishFarm.getWarehouseA().getSpace();
                                    selectedFishFarm.getWarehouseA().addFood(ammount);
                                }else if(warehouseType == 2){
                                    ammount = selectedFishFarm.getWarehouseV().getSpace();
                                    selectedFishFarm.getWarehouseV().addFood(ammount);
                                }
                                if(ammount > 25) {
                                    discount = (ammount / 25) * 5;
                                    monedas.pagar(discount);
                                }else{
                                    monedas.pagar(ammount);
                                }                      
                            default:
                                break;
                        }   
                    }
                }catch(InputMismatchException e) {
                    System.out.println("Selección incorrecta");
                }   
            }
        }else{
            while (!cancel) {
                System.out.println("Tipo de Almacén: ");
                System.out.println("1.- Almacén de comida animal");
                System.out.println("2.- Almacén de comida vegetal");
                System.out.println("0.- Cancelar");
                System.out.print("Seleccione una opción: ");
                warehouseType = sc.nextInt();
                if(warehouseType == 0) {
                    cancel = true;
                }else{
                    System.out.println("1.- 5");
                    System.out.println("2.- 10");
                    System.out.println("3.- 25");
                    System.out.println("4.- Llenar");
                    System.out.println("0.- Cancelar");
                    System.out.print("Seleccione una opción: ");
                    int opcion = sc.nextInt();
                    int discount = 0;
                    int ammount = 0;
                    switch (opcion) {
                        case 1:
                            if(warehouseType == 1) {
                                if(this.centralWarehouse.getWarehouseA().getSpace() < 5) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }else{
                                    ammount = 5;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            }else if(warehouseType == 2){
                                if(this.centralWarehouse.getWarehouseV().getSpace() < 5) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }else{
                                    ammount = 5;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            this.monedas.pagar(ammount);
                            break;
                        case 2:
                            if(warehouseType == 1) {
                                if(this.centralWarehouse.getWarehouseA().getSpace() < 10) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }else{
                                    ammount = 10;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            }else if(warehouseType == 2){
                                if(this.centralWarehouse.getWarehouseV().getSpace() < 10) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }else{
                                    ammount = 10;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            this.monedas.pagar(ammount);
                            break;
                        case 3:
                            if(warehouseType == 1) {
                                if(this.centralWarehouse.getWarehouseA().getSpace() < 25) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }else{
                                    ammount = 25;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            }else if(warehouseType == 2){
                                if(this.centralWarehouse.getWarehouseV().getSpace() < 25) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }else{
                                    ammount = 25;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            if(ammount > 25) {
                                discount = (ammount / 25) * 5;
                                this.monedas.pagar(discount);
                            }else{
                                this.monedas.pagar(ammount);
                            }
                            break;
                        case 4:
                            if(warehouseType == 1) {
                                ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                this.centralWarehouse.getWarehouseA().addFood(ammount);
                            }else if(warehouseType == 2){
                                ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                this.centralWarehouse.getWarehouseV().addFood(ammount);
                            }
                            if(ammount > 25) {
                                discount = (ammount / 25) * 5;
                                this.monedas.pagar(discount);
                            }else{
                                this.monedas.pagar(ammount);
                            }
                            break;                   
                    }
                }
                
            }
        }
     }
    

    /**
     * Método que muestra las estadisticas de los peces de la piscifactoria
     */
    public void showStats(){
        stats.mostrar();
    }


    /**
     * Metodo que elimina todos los peces muertos de los tanques de una Piscifactoria
     */
    public void cleanTank(){
        Piscifactoria selectedFishFarm = selectPisc();
        selectedFishFarm.cleanDeadFishes();
    }

    /**
     * Metodo que borra todos los peces de un Tanque en concreto de una piscifactoria
     */
    public void emptyTank(){
        Tanque tank = selectTank();
        tank.cleanTank();
    } 

    /**
     * Método que para mejorar y comprar edificios
     */
    public void upgrade() {
        int opcion = 0;
        boolean validSelection = false;
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificios"); 
        System.out.println("3. Cancelar");
        while (!validSelection) {
            try {
                System.out.print("Seleccione una opción: "); 
                opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    this.buyBuildings();            
                    break;
                case 2:
                    this.upgradeBuildings();
                    break;
                case 3:
                    validSelection = true;
                    break;
                default:
                    break;
            }  
            } catch (Exception e) {
                System.out.println("Entrada inválida");
                sc.nextLine();
            }         
        }
    }

    /**
     * Método que permite comprar edificios
     */
    public void buyBuildings() {
        /*int opcion = 0;
        boolean validSelection = false;
        System.out.println("1. Piscifactoría");
        System.out.println("2. Almacén Central");
        System.out.println("3. Cancelar");
        while (!validSelection) {
            try{
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.print("Nombre de la nueva Piscifactoría: ");
                        String name = sc.nextLine();
                        boolean validType = false;
                        while (!validType) {
                            System.out.print("Tipo de Piscifactoría Rio / Mar: ");
                            String type = sc.nextLine();
                            if(type.equals("Rio")) {
                                Piscifactoria newRiverFishFarm = new Piscifactoria(name, true, 0);
                                fishFarms.add(newRiverFishFarm);
                                this.monedas.pagar(500 * (this.fishFarms.size() -1));
                                validType = true;
                                validSelection = true;
                            }else if(type.equals("Mar")) {
                                Piscifactoria newSeaFishFarm = new Piscifactoria(name, false, 0);
                                fishFarms.add(newSeaFishFarm);
                                this.monedas.pagar(2000 * this.fishFarms.size() -1);
                                validType = true;
                                validSelection = true;
                            }else{
                                System.out.println("Tipo de Piscifactoría incorrecto");
                            }
                        }
                        break;
                        case 2:
                            if(this.centralWarehouse == null) {
                                this.centralWarehouse = new AlmacenCentral();
                            }else{
                                System.out.println("Mejora ya comprada");
                            }
                            validSelection = true;
                            break;
                    default:
                        break;
                } 
            }catch(InputMismatchException e) {
                System.out.println("Entrada inválida.");
            }
        } */ //TODO
    }

    /**
     * Método que permite mejorar los edificios
     */
    public void upgradeBuildings() {
        int opcion = 0;
        boolean validSelection = false;
        System.out.println("1. Piscifactoría");
        if(this.centralWarehouse != null) {
            System.out.println("2. Almacén Central");
        }
        System.out.println("3. Cancelar");
        while (!validSelection) {
            try {
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        this.upgradeFisFarm();
                        break;
                    case 2:
                        if(this.centralWarehouse != null) {
                            this.upgradeCentralWarehouse();
                        }else{
                            System.out.println("No dispone de Almacén Central");
                            validSelection = false;
                        }
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida");
            }
        }
    }

    /**
     * Método para mejorar las Piscifactorías
     */
    public void upgradeFisFarm() {
        Piscifactoria selectedFishFarm = selectPisc();
        int opcion = 0;
        boolean validSelection = false;
        System.out.println("1. Comprar Tanque");
        System.out.println("2. Aumentar Almacén de comida");
        while (!validSelection) {
            try {
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        selectedFishFarm.compraTanque();
                        validSelection = true;
                        break;
                    case 2: 
                        selectedFishFarm.upgradeFood();
                        validSelection = true;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida");
            }
        }
    }

    /**
     * Método para mejorar el Almacén Central
     */
    public void upgradeCentralWarehouse() {

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
        //sim.cleanTank();
        //sim.emptyTank();
        //sim.upgrade();
        //sim.buyBuildings();
        //sim.menuPisc();
        sim.selectPisc();
    }
}

