package sistema;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import edificios.almacenes.AlmacenCentral;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaMar;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
import estadisticas.Estadisticas;
import peces.Pez;
import peces.especies.Besugo;
import peces.especies.Caballa;
import peces.especies.CarpaPlateada;
import peces.especies.LenguadoEuropeo;
import peces.especies.LubinaEuropea;
import peces.especies.LubinaRayada;
import peces.especies.LucioDelNorte;
import peces.especies.Pejerrey;
import peces.especies.PercaEuropea;
import peces.especies.Robalo;
import peces.especies.SalmonAtlantico;
import peces.especies.SalmonChinook;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

/**
 * IMPLEMENNTAR CLASE
 */
public class Simulador {
    /** Días que han pasado */
    private int days;
    /** Piscifactorías del juego */
    private List<Piscifactoria> fishFarms;
    /** Nombre de la empresa/partida */
    private String name;
    /** Sistema de monedas del juego */
    private SISMonedas monedas;
    /** Almacén de comida central del Sistema */
    private AlmacenCentral centralWarehouse;
    /** Array que contiene los peces disponibles */
    private static PecesDatos[] pecesDisponibles = {
            propiedades.AlmacenPropiedades.BESUGO,
            propiedades.AlmacenPropiedades.CABALLA,
            propiedades.AlmacenPropiedades.CARPA_PLATEADA,
            propiedades.AlmacenPropiedades.LENGUADO_EUROPEO,
            propiedades.AlmacenPropiedades.LUBINA_EUROPEA,
            propiedades.AlmacenPropiedades.LUBINA_RAYADA,
            propiedades.AlmacenPropiedades.LUCIO_NORTE,
            propiedades.AlmacenPropiedades.PEJERREY,
            propiedades.AlmacenPropiedades.PERCA_EUROPEA,
            propiedades.AlmacenPropiedades.ROBALO,
            propiedades.AlmacenPropiedades.SALMON_ATLANTICO,
            propiedades.AlmacenPropiedades.SALMON_CHINOOK };

    Scanner sc = new Scanner(System.in);

    /**
     * Método que inicializa el Sistema
     */
    public void init() {
        try {
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
        } catch (InputMismatchException e) {
            System.out.println("El nombre es incorrecto");
        }
    }

    /**
     * Método que muestra el menú general
     * Implementar control de errores
     */
    public void menu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("===========Menú Principal===========");
            System.out.println("1. Estado general"); // rarete
            System.out.println("2. Estado piscifactoría"); // funciona
            System.out.println("3. Estado Tanques"); // perfecto
            System.out.println("4. Informes"); // perfecto
            System.out.println("5. Ictiopedia"); // perfecto
            System.out.println("6. Pasar día"); // necesita implementar
            System.out.println("7. Comprar comida"); // funciona
            System.out.println("8. Comprar peces"); // perfecto
            System.out.println("9. Vender peces"); // implementar
            System.out.println("10. Limpiar tanques"); // comprobar
            System.out.println("11. Vaciar tanques"); // comprobar
            System.out.println("12. Mejorar"); // terminar
            System.out.println("13. Pasar varios días"); // implementar
            System.out.println("14. Salir");
            System.out.print("Escoja una opción: ");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    this.showGeneralStatus();
                    break;
                case 2:
                    this.showSpecificStatus();
                    break;
                case 3:
                    this.showTankStatus();
                    break;
                case 4:
                    this.showStats();
                    break;
                case 5:
                    this.showIctio();
                    break;
                case 6:
                    nextDay(1);
                    break;
                case 7:
                    this.addFood();
                    break;
                case 8:
                    this.addFish();
                    break;
                case 9:
                    this.sellFish();
                    break;
                case 10:
                    this.cleanTank();
                    break;
                case 11:
                    this.emptyTank();
                    break;
                case 12:
                    this.upgrade();
                    break;
                case 13:
                    this.nextDay(5);
                    break;
                case 14:
                    exit = true;
                    break;
                case 98:
                    this.addFishAmmount();
                    break;
                case 99:
                    this.procesaOpcion99();
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Método que muestra un ménu con las Piscifactorías disponibles
     */
    public void menuPisc() {
        System.out.println("Seleccione una opción");
        System.out.println("--------------------------- Piscifactorías ---------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (int i = 0; i < fishFarms.size(); i++) {
            System.out.println((i + 1) + ".- " + fishFarms.get(i).getName() + "[" + fishFarms.get(i).fishesAlive() + "/"
                    + fishFarms.get(i).occuped() + "/" + fishFarms.get(i).maxFishes() + "]");
        }
        System.out.println("0.- Cancelar");
    }

    /**
     * Método que permite seleccionar una piscifactoria
     * 
     * @return Piscifactoría seleccionada
     */
    public Piscifactoria selectPisc() {
        this.menuPisc();
        Piscifactoria selected = null;
        while (selected == null) {
            try {
                System.out.print("Seleccione una opción: ");
                int option = sc.nextInt();
                if (option == 0) {
                    return null;
                }
                if (option > 0 && option - 1 < this.fishFarms.size()) {
                    selected = this.fishFarms.get(option - 1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida");
            }
        }
        return selected;
    }

    /**
     * Método que muestra un menú de Tanques de una Piscifactoría y permite
     * seleccionar uno
     * 
     * @return Tanque seleccionado
     */
    public Tanque selectTank() {
        Piscifactoria p = selectPisc();
        if (p == null) {
            return null;
        } else {
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
                    if (option > 0 && option - 1 < p.getNTanks()) {
                        selected = p.selectTank(option - 1);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida");
                }
            }
            return selected;
        }
    }

    public void menupeces() {
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
        System.out.println("0. Cancelar");
        System.out.print("Seleccione una opción: ");
    }

    public void showIctio() {
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
        System.out.println("-- Estado del Almacén Central:");
        if (centralWarehouse != null) {
            centralWarehouse.getOcuped();
        } else {
            System.out.println("No comprado");
        }
    }

    /**
     * Método que permite seleccionar una Piscifactoría y muestra su estado
     */
    public void showSpecificStatus() {
        Piscifactoria selected = this.selectPisc();
        if (selected != null) {
            selected.showTankStatus();
        } else {
            return;
        }
    }

    /**
     * Método que permite elegir uno de los Tanques de la Piscifactoría y muestra la
     * información de los peces
     */
    public void showTankStatus() {
        Tanque selectedTank = selectTank();
        if (selectedTank != null) {
            selectedTank.showfishestatus();
        } else {
            return;
        }
    }

    /**
     * Método que permite pasar varios días en las Piscifactorías
     * 
     * @param days Número de días a pasar
     */
    public void nextDay(int days) {
        this.days += days;
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
        if (centralWarehouse == null) {
            Piscifactoria selectedFishFarm = selectPisc();
            while (!cancel) {
                try {
                    System.out.println("Tipo de Almacén: ");
                    System.out.println("1.- Almacén de comida animal");
                    System.out.println("2.- Almacén de comida vegetal");
                    System.out.println("0.- Cancelar");
                    System.out.print("Seleccione una opción: ");
                    warehouseType = sc.nextInt();
                    if (warehouseType == 0) {
                        cancel = true;
                    } else {
                        System.out.println("Comida a añadir: ");
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
                                if (warehouseType == 1) {
                                    if (selectedFishFarm.getWarehouseA().getSpace() < 5) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    } else {
                                        ammount = 5;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }
                                } else if (warehouseType == 2) {
                                    if (selectedFishFarm.getWarehouseV().getSpace() < 5) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    } else {
                                        ammount = 5;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                }
                                this.monedas.pagar(ammount);
                                break;
                            case 2:
                                if (warehouseType == 1) {
                                    if (selectedFishFarm.getWarehouseA().getSpace() < 10) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    } else {
                                        ammount = 10;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }
                                } else if (warehouseType == 2) {
                                    if (selectedFishFarm.getWarehouseV().getSpace() < 10) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    } else {
                                        ammount = 10;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                }
                                this.monedas.pagar(ammount);
                                break;
                            case 3:
                                if (warehouseType == 1) {
                                    if (selectedFishFarm.getWarehouseA().getSpace() < 25) {
                                        ammount = selectedFishFarm.getWarehouseA().getSpace();
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    } else {
                                        ammount = 25;
                                        selectedFishFarm.getWarehouseA().addFood(ammount);
                                    }
                                } else if (warehouseType == 2) {
                                    if (selectedFishFarm.getWarehouseV().getSpace() < 25) {
                                        ammount = selectedFishFarm.getWarehouseV().getSpace();
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    } else {
                                        ammount = 25;
                                        selectedFishFarm.getWarehouseV().addFood(ammount);
                                    }
                                    if (ammount > 25) {
                                        discount = (ammount / 25) * 5;
                                        monedas.pagar(discount);
                                    } else {
                                        monedas.pagar(ammount);
                                    }
                                }
                                this.monedas.pagar(20);
                                break;
                            case 4:
                                if (warehouseType == 1) {
                                    ammount = selectedFishFarm.getWarehouseA().getSpace();
                                    selectedFishFarm.getWarehouseA().addFood(ammount);
                                } else if (warehouseType == 2) {
                                    ammount = selectedFishFarm.getWarehouseV().getSpace();
                                    selectedFishFarm.getWarehouseV().addFood(ammount);
                                }
                                if (ammount > 25) {
                                    discount = (ammount / 25) * 5;
                                    monedas.pagar(discount);
                                } else {
                                    monedas.pagar(ammount);
                                }
                            default:
                                break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Selección incorrecta");
                }
            }
        } else {
            while (!cancel) {
                System.out.println("Tipo de Almacén: ");
                System.out.println("1.- Almacén de comida animal");
                System.out.println("2.- Almacén de comida vegetal");
                System.out.println("0.- Cancelar");
                System.out.print("Seleccione una opción: ");
                warehouseType = sc.nextInt();
                if (warehouseType == 0) {
                    cancel = true;
                } else {
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
                            if (warehouseType == 1) {
                                if (this.centralWarehouse.getWarehouseA().getSpace() < 5) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                } else {
                                    ammount = 5;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            } else if (warehouseType == 2) {
                                if (this.centralWarehouse.getWarehouseV().getSpace() < 5) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                } else {
                                    ammount = 5;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            this.monedas.pagar(ammount);
                            break;
                        case 2:
                            if (warehouseType == 1) {
                                if (this.centralWarehouse.getWarehouseA().getSpace() < 10) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                } else {
                                    ammount = 10;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            } else if (warehouseType == 2) {
                                if (this.centralWarehouse.getWarehouseV().getSpace() < 10) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                } else {
                                    ammount = 10;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            this.monedas.pagar(ammount);
                            break;
                        case 3:
                            if (warehouseType == 1) {
                                if (this.centralWarehouse.getWarehouseA().getSpace() < 25) {
                                    ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                } else {
                                    ammount = 25;
                                    this.centralWarehouse.getWarehouseA().addFood(ammount);
                                }
                            } else if (warehouseType == 2) {
                                if (this.centralWarehouse.getWarehouseV().getSpace() < 25) {
                                    ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                } else {
                                    ammount = 25;
                                    this.centralWarehouse.getWarehouseV().addFood(ammount);
                                }
                            }
                            if (ammount > 25) {
                                discount = (ammount / 25) * 5;
                                this.monedas.pagar(discount);
                            } else {
                                this.monedas.pagar(ammount);
                            }
                            break;
                        case 4:
                            if (warehouseType == 1) {
                                ammount = this.centralWarehouse.getWarehouseA().getSpace();
                                this.centralWarehouse.getWarehouseA().addFood(ammount);
                            } else if (warehouseType == 2) {
                                ammount = this.centralWarehouse.getWarehouseV().getSpace();
                                this.centralWarehouse.getWarehouseV().addFood(ammount);
                            }
                            if (ammount > 25) {
                                discount = (ammount / 25) * 5;
                                this.monedas.pagar(discount);
                            } else {
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
    public void showStats() {
        String[] peces = new String[12];
        for (int i = 0; i < pecesDisponibles.length; i++) {
            peces[i] = pecesDisponibles[i].getNombre();
        }
        estadisticas.Estadisticas stats = new Estadisticas(peces);
        stats.mostrar();
    }

    /**
     * Método para vender peces vivos y maduros
     */
    public void sellFish() {
        Tanque tanque = this.selectTank();
        if (tanque == null) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Verifica peces vivos y maduros
        List<Pez> pecesParaVenta = new ArrayList<>();
        for (Pez pez : tanque.getFishes()) {
            if (pez.isAlive() && pez.isMature()) {
                pecesParaVenta.add(pez);
            }
        }

        if (pecesParaVenta.isEmpty()) {
            System.out.println("No hay peces vivos y maduros en el tanque para vender.");
            return;
        }

        // Calcular ganancias y eliminar peces vendidos
        int ganancias = 0;
        for (Pez pez : pecesParaVenta) {
            ganancias += pez.getFishStats().getMonedas();
            tanque.getFishes().remove(pez);
        }

        // Actualizar el sistema de monedas
        SISMonedas.getInstance().setMonedas(ganancias);

        // Muestra de ooperaciones realizadas
        System.out.println("Se han vendido " + pecesParaVenta.size() + " peces.");
        System.out.println("Ganancias obtenidas: " + ganancias + " monedas.");
    }

    /**
     * Metodo que elimina todos los peces muertos de los tanques de una
     * Piscifactoria
     */
    public void cleanTank() {
        Piscifactoria selectedFishFarm = selectPisc();
        selectedFishFarm.cleanDeadFishes();
    }

    /**
     * Metodo que borra todos los peces de un Tanque en concreto de una
     * piscifactoria
     */
    public void emptyTank() {
        Tanque tank = selectTank();
        tank.cleanTank();
    }

    /**
     * Método que para mejorar y comprar edificios
     */
    public void upgrade() {
        int opcion = 0;
        boolean validSelection = false;
        while (!validSelection) {
            System.out.println("1. Comprar edificios");
            System.out.println("2. Mejorar edificios");
            System.out.println("3. Cancelar");
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
                        System.out.println("Número de opción inválido");
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
        int opcion = 0;
        boolean validSelection = false;
        while (!validSelection) {
            System.out.println("1. Piscifactoría");
            System.out.println("2. Almacén Central");
            System.out.println("3. Cancelar");
            try {
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
                            if (type.equals("Rio") || type.equals("rio")) {
                                if((500 * this.getRiverFishfarms()) <= this.monedas.getMonedas()) {
                                    Piscifactoria newRiverFishFarm = new PiscifactoriaRio(name, 0);
                                    fishFarms.add(newRiverFishFarm);
                                    this.monedas.pagar(500 * (this.getRiverFishfarms()));
                                }
                                validType = true;
                                validSelection = true;
                            } else if (type.equals("Mar") || type.equals("mar")) {
                                if(this.getSeaFishfarms() > 0) {
                                    if((2000 * this.getSeaFishfarms()) <= this.monedas.getMonedas()) {
                                        Piscifactoria newSeaFishFarm = new PiscifactoriaMar(name, 0);
                                        fishFarms.add(newSeaFishFarm);
                                        this.monedas.pagar(2000 * getSeaFishfarms());
                                    }
                                }else{
                                    if(2000 <= this.monedas.getMonedas()) {
                                        Piscifactoria newSeaFishFarm = new PiscifactoriaMar(name, 0);
                                        fishFarms.add(newSeaFishFarm);
                                        this.monedas.pagar(2000 * getSeaFishfarms());
                                    }
                                }
                                validType = true;
                                validSelection = true;
                            } else {
                                System.out.println("Tipo de Piscifactoría incorrecto");
                            }
                        }
                        break;
                    case 2:
                        if (this.centralWarehouse == null) {
                            this.centralWarehouse = new AlmacenCentral();
                        } else {
                            System.out.println("Mejora ya comprada");
                        }
                        validSelection = true;
                        break;
                    case 3:
                        validSelection = true;
                    default:
                        System.out.println("Número de opción inválido");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
            }
        }
    }

    /**
     * Método que permite mejorar los edificios
     */
    public void upgradeBuildings() {
        int opcion = 0;
        boolean validSelection = false;
        while (!validSelection) {
            System.out.println("1. Piscifactoría");
            if (this.centralWarehouse != null) {
                System.out.println("2. Almacén Central");
            }
            System.out.println("0. Cancelar");
            try {
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        this.upgradeFisFarm();
                        validSelection = true;
                        break;
                    case 2:
                        if (this.centralWarehouse != null) {
                            this.upgradeCentralWarehouse();
                            validSelection = true;
                        } else {
                            System.out.println("No dispone de Almacén Central");
                            validSelection = false;
                        }
                        break;
                    case 0:
                        validSelection = true;
                    default:
                        System.out.println("Número de opción inválido");
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
        while (!validSelection) {
            System.out.println("1. Comprar Tanque");
            System.out.println("2. Aumentar Almacén de comida");
            System.out.println("0. Cancelar");
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
                    case 3:
                        validSelection = true;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida");
            }
        }
    }

    /**
     * Método para mejorar el Almacén Central
     */
    public void upgradeCentralWarehouse() {

    }

    /**
     * Método que permite añadir un pez a una Piscifactoría
     */
    public void addFish() {
        int opcion;
        ArrayList<PecesDatos> pecesCompatibles = new ArrayList<>();
        Tanque selectedTank = this.selectTank();
        String selectedFish;
        int fishPrice = 0;
        System.out.println("");
        int menuFishes = 1;
        if (selectedTank == null) {
            return;
        }
        if (selectedTank.getType()) {
            if (selectedTank.getFishType() == null) {
                for (int i = 0; i < pecesDisponibles.length; i++) {
                    if (pecesDisponibles[i].getPiscifactoria().getName().equals("Río")
                            || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
                        System.out.println((menuFishes++) + ". " + pecesDisponibles[i].getNombre() + " ("
                                + pecesDisponibles[i].getCoste() + ")");
                        pecesCompatibles.add(pecesDisponibles[i]);
                    }
                }
            } else {
                for (int i = 0; i < pecesDisponibles.length; i++) {
                    if (pecesDisponibles[i].getNombre() == selectedTank.getFishType()) {
                        System.out.println((menuFishes) + ". " + pecesDisponibles[i].getNombre() + " ("
                                + pecesDisponibles[i].getCoste() + ")");
                        pecesCompatibles.add(pecesDisponibles[i]);
                    }
                }
            }
        } else {
            if (selectedTank.getFishType() == null) {
                for (int i = 0; i < pecesDisponibles.length; i++) {
                    if (pecesDisponibles[i].getPiscifactoria().getName().equals("Mar")
                            || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
                        System.out.println((menuFishes++) + ". " + pecesDisponibles[i].getNombre() + " ("
                                + pecesDisponibles[i].getCoste() + ")");
                        pecesCompatibles.add(pecesDisponibles[i]);
                    }
                }
            } else {
                for (int i = 0; i < pecesDisponibles.length; i++) {
                    if (pecesDisponibles[i].getNombre() == selectedTank.getFishType()) {
                        System.out.println((menuFishes) + ". " + pecesDisponibles[i].getNombre() + " ("
                                + pecesDisponibles[i].getCoste() + ")");
                        pecesCompatibles.add(pecesDisponibles[i]);
                    }
                }
            }
        }
        System.out.println("0. Cancelar");
        System.out.print("Seleccione un pez: ");
        try {
            opcion = sc.nextInt() - 1;
            if (opcion >= 0 && opcion <= pecesCompatibles.size()) {
                selectedFish = pecesCompatibles.get(opcion).getNombre();
                fishPrice = pecesCompatibles.get(opcion).getCoste();
                boolean fishSex;
                if (selectedTank.fishesF() <= selectedTank.fishesM()) {
                    fishSex = true;
                } else {
                    fishSex = false;
                }
                if(this.monedas.getMonedas() >= fishPrice) {
                    switch (selectedFish) {
                        case "Besugo":
                            selectedTank.addFishes(new Besugo(fishSex));
                            break;
                        case "Caballa":
                            selectedTank.addFishes(new Caballa(fishSex));
                            break;
                        case "Carpa plateada":
                            selectedTank.addFishes(new CarpaPlateada(fishSex));
                            break;
                        case "Lenguado europeo":
                            selectedTank.addFishes(new LenguadoEuropeo(fishSex));
                            break;
                        case "Lubina europea":
                            selectedTank.addFishes(new LubinaEuropea(fishSex));
                            break;
                        case "Lubina rayada":
                            selectedTank.addFishes(new LubinaRayada(fishSex));
                            break;
                        case "Lucio del norte":
                            selectedTank.addFishes(new LucioDelNorte(fishSex));
                            break;
                        case "Pejerrey":
                            selectedTank.addFishes(new Pejerrey(fishSex));
                            break;
                        case "Perca europea":
                            selectedTank.addFishes(new PercaEuropea(fishSex));
                            break;
                        case "Róbalo":
                            selectedTank.addFishes(new Robalo(fishSex));
                            break;
                        case "Salmón atlántico":
                            selectedTank.addFishes(new SalmonAtlantico(fishSex));
                            break;
                        case "Salmón chinook":
                            selectedTank.addFishes(new SalmonChinook(fishSex));
                            break;
                        default:
                            System.out.println("No se ha encontrado el pez");
                            break;
                    }
                    this.monedas.pagar(fishPrice);
                }else{
                    System.out.println("No se ha añadido el pez");
                    return;
                }    
            }
        } catch (InputMismatchException e) {
            System.out.println("Se ha introducido una opción inválida");
        }
    }

    /**
     * Metodo oculto que añade 1000 monedas al saldo
     */
    public void procesaOpcion99() {
        this.monedas.setMonedas(this.monedas.getMonedas() + 1000);
        System.out.println("Se han añadido 1000 monedas al saldo");
        System.out.println("Monedas actuales: " + SISMonedas.getInstance().getMonedas());
    }

    /**
     * Método que añade cuatro peces aleatorios a una Piscifactoría seleccionada
     */
    public void addFishAmmount() {
        Tanque tanque = this.selectTank();
        ArrayList<PecesDatos> pecesCompatibles = new ArrayList<>();
        Random r = new Random();
        if(tanque != null) {
            if (tanque.getType()) {
                if (tanque.getFishType() == null) {
                    for (int i = 0; i < pecesDisponibles.length; i++) {
                        if (pecesDisponibles[i].getPiscifactoria().getName().equals("Río") || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
                            pecesCompatibles.add(pecesDisponibles[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < pecesDisponibles.length; i++) {
                        if (pecesDisponibles[i].getNombre() == tanque.getFishType()) {
                            pecesCompatibles.add(pecesDisponibles[i]);
                        }
                    }
                }
            } else {
                if (tanque.getFishType() == null) {
                    for (int i = 0; i < pecesDisponibles.length; i++) {
                        if (pecesDisponibles[i].getPiscifactoria().getName().equals("Mar") || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
                            pecesCompatibles.add(pecesDisponibles[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < pecesDisponibles.length; i++) {
                        if (pecesDisponibles[i].getNombre() == tanque.getFishType()) {
                            pecesCompatibles.add(pecesDisponibles[i]);
                        }
                    }
                }
            }
            int pezRandom = r.nextInt(pecesCompatibles.size());
            String selectedFish = pecesCompatibles.get(pezRandom).getNombre();
            boolean fishSex;
            for (int i = 0; i < 4; i++) {
                if (tanque.fishesF() <= tanque.fishesM()) {
                    fishSex = true;
                } else {
                    fishSex = false;
                }
                switch (selectedFish) {
                    case "Besugo":
                        tanque.addFishes(new Besugo(fishSex));
                        break;
                    case "Caballa":
                        tanque.addFishes(new Caballa(fishSex));
                        break;
                    case "Carpa plateada":
                        tanque.addFishes(new CarpaPlateada(fishSex));
                        break;
                    case "Lenguado europeo":
                        tanque.addFishes(new LenguadoEuropeo(fishSex));
                        break;
                    case "Lubina europea":
                        tanque.addFishes(new LubinaEuropea(fishSex));
                        break;
                    case "Lubina rayada":
                        tanque.addFishes(new LubinaRayada(fishSex));
                        break;
                    case "Lucio del norte":
                        tanque.addFishes(new LucioDelNorte(fishSex));
                        break;
                    case "Pejerrey":
                        tanque.addFishes(new Pejerrey(fishSex));
                        break;
                    case "Perca europea":
                        tanque.addFishes(new PercaEuropea(fishSex));
                        break;
                    case "Róbalo":
                        tanque.addFishes(new Robalo(fishSex));
                        break;
                    case "Salmón atlántico":
                        tanque.addFishes(new SalmonAtlantico(fishSex));
                        break;
                    case "Salmón chinook":
                        tanque.addFishes(new SalmonChinook(fishSex));
                        break;
                    default:
                        System.out.println("No se ha encontrado el pez");
                        break;
                }
            }
        }
    }

    /**
     * Método que busca cuantas Piscifactorías de Mar hay
     * @return el número de Piscifactorías de Mar
     */
    public int getSeaFishfarms() {
        int seaFishfarms = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if(piscifactoria instanceof PiscifactoriaMar){
                seaFishfarms += 1;
            }
        }
        return seaFishfarms;
    }

    /**
     * Método que busca cuantas Piscifactorías de Río hay
     * @return el número de Piscifactorías de Río
     */
    public int getRiverFishfarms() {
        int riverFishFarms = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if(piscifactoria instanceof PiscifactoriaRio) {
                riverFishFarms += 1;
            }
        }
        return riverFishFarms;
    }

    public static void main(String[] args) {
        Simulador sim = new Simulador();
        sim.init();
        // sim.showIctio();
        sim.menu();
        // sim.menuPisc();
        // System.out.println(sim.selectPisc());
        // Piscifactoria p = new Piscifactoria("Pisc1", false, 50);
        // p.listTanks();
        // sim.showGeneralStatus();
        // sim.showSpecificStatus();
        // sim.selectTank();
        // sim.showTankStatus();
        // sim.cleanTank();
        // sim.emptyTank();
        // sim.upgrade();
        // sim.buyBuildings();
        // sim.menuPisc();
        // sim.selectPisc();
    }
}
