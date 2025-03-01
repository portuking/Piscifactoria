package sistema;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import bd.Conexion;
import bd.DAOPedidos;
import bd.GeneradorBD;
import dto.PedidoDTO;
import edificios.almacenes.AlmacenCentral;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaMar;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
import estadisticas.Estadisticas;
import gestor_guardado.Cargado;
import gestor_guardado.Guardado;
import peces.Pez;
import peces.especies.Besugo;
import peces.especies.Caballa;
import peces.especies.CarpaPlateada;
import peces.especies.Dorada;
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
import propiedades.PecesProps;
import recompensas.GenerarRecompensa;
import recompensas.GestorRecompensas;
import registros.Registros;
import registros.Transcripciones;

/**
 * Clase que representa el Simulador del sistema de Piscifactorías
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
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
            propiedades.AlmacenPropiedades.SALMON_CHINOOK,
            propiedades.AlmacenPropiedades.DORADA };
    Scanner sc = new Scanner(System.in);
    /* Instancia objeto helper una sola vez */
    private Helper helper = new Helper();
    public static String[] fishesNames = {
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
            propiedades.AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
            propiedades.AlmacenPropiedades.DORADA.getNombre() };
    private static Estadisticas estadisticas = new Estadisticas(fishesNames);

    GenerarRecompensa generar = new GenerarRecompensa();

    public Simulador() {
        this.monedas = SISMonedas.getInstance(); // Inicializa el sistema de monedas
        this.fishFarms = new ArrayList<>(); // Inicializa la lista de piscifactorías
        this.days = 1; // Inicia el día en 1
    }

    /**
     * Método que inicializa el Sistema
     */
    public void init() {
        try {
            crearCarpetas("transcripciones", "logs", "rewards", "saves");
    
            // Verificar si hay partidas guardadas
            File savesDir = new File("saves");
            File[] partidasGuardadas = savesDir.listFiles((dir, name) -> name.endsWith(".save"));
    
            if (partidasGuardadas != null && partidasGuardadas.length > 0) {
                // Mostrar las partidas guardadas disponibles
                System.out.println("Partidas guardadas encontradas:");
                for (File partida : partidasGuardadas) {
                    System.out.println("- " + partida.getName().replace(".save", ""));
                }
    
                // Preguntar al usuario si desea cargar una partida
                System.out.print("¿Desea cargar una partida? (s/n): ");
                String respuesta = sc.nextLine().trim().toLowerCase();
    
                if (respuesta.equals("s")) {
                    // Solicitar el nombre de la partida a cargar
                    System.out.print("Ingrese el nombre de la partida que desea cargar: ");
                    String nombrePartida = sc.nextLine().trim();
                    cargarPartida(nombrePartida); // Llamar al método cargarPartida
                    return; // Salir del método init después de cargar la partida
                }
            }
    
            // Si no hay partidas guardadas o el usuario elige no cargar una, crear una nueva partida
            System.out.print("Escriba el nombre de su empresa/partida: ");
            this.name = sc.nextLine();
            Transcripciones.getInstance(this.name);
            System.out.print("Escriba el nombre de su primera Piscifactoría: ");
            String fishfarmName = sc.nextLine();
            Registros.getInstance().crearRegistro(this.name);
            this.days = 1;
            this.fishFarms = new ArrayList<>();
            this.monedas = SISMonedas.getInstance();
            this.monedas.setMonedas(100);
            Piscifactoria initialRiverFishfarm = new PiscifactoriaRio(fishfarmName, 25);
            fishFarms.add(initialRiverFishfarm);
            this.centralWarehouse = null;
            Registros.registrarInicio(this.name, this.monedas.getMonedas(), fishesNames, fishfarmName);

    
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
            String[] opciones = { "Estado general", "Estado piscifactoría", "Estado Tanques", "Informes", "Ictiopedia",
                    "Pasar día", "Comprar comida", "Comprar peces", "Vender peces", "Limpiar tanques", "Vaciar tanques",
                    "Mejorar", "Pasar varios días", "Pedidos" };
            int[] extraOps = { 95, 96, 97, 98, 99 };
            Helper helper = new Helper();
            int opcion = helper.mostrarMenu("Menu Principal", opciones, extraOps);

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
                    new Guardado(this).guardarPartida();
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
                    gestionarPedidos();
                    break;
                case 15:
                    exit = true;
                    new Guardado(this).guardarPartida();
                    Registros.registrarSalir();
                    Registros.cerrarRegistros();
                    break;
                
                case 95:
                    DAOPedidos.borrarPedidos();
                    break;
                case 96:
                    GestorRecompensas g = new GestorRecompensas();
                    g.mostrarYCanjearReward(this);
                case 97:
                    GestorRecompensas gen = new GestorRecompensas();

                    generar.algaReward(1);
                    generar.algaReward(2);
                    generar.algaReward(3);
                    generar.algaReward(4);
                    generar.algaReward(5);

                    generar.piensoReward(1);
                    generar.piensoReward(2);
                    generar.piensoReward(3);
                    generar.piensoReward(4);
                    generar.piensoReward(5);

                    generar.comidaReward(1);
                    generar.comidaReward(2);
                    generar.comidaReward(3);
                    generar.comidaReward(4);
                    generar.comidaReward(5);

                    generar.BolivaresReward(1);
                    generar.BolivaresReward(2);
                    generar.BolivaresReward(3);
                    generar.BolivaresReward(4);
                    generar.BolivaresReward(5);

                    generar.pisciMarReward("A");
                    generar.pisciMarReward("B");

                    generar.pisciRioReward("A");
                    generar.pisciRioReward("B");

                    generar.tanqueReward("r");
                    generar.tanqueReward("m");

                    generar.almacenReward("A");
                    generar.almacenReward("B");

                    generar.almacenReward("D");
                    gen.mostrarYCanjearReward(this);
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
                sc.next();
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
                    sc.next();
                }
            }
            return selected;
        }
    }

    /**
     * Método que muestra un menú con los peces que hay,
     * permite seleccionar uno y devuelve la información de dicho pez
     */
    public void showIctio() {
        int opcion = helper.mostrarMenu("Menú Peces", fishesNames, null);
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
            case 13:
                name = "Dorada";
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
            PecesProps[] propiedades = pezSeleccionado.getPropiedades();
            System.out.print("Alimentación: ");
            for (int i = 0; i < propiedades.length; i++) {
                switch (propiedades[i]) {
                    case CARNIVORO:
                        System.out.print("Carnívoro\n");
                        break;
                    case FILTRADOR:
                        System.out.print("Herbívoro\n");
                        break;
                    case OMNIVORO:
                        System.out.print("Omnívoro\n");
                        break;
                    default:
                        break;
                }
            }
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
        for (int i = 0; i < days; i++) {
            System.out.println("==========Día: " + this.days + "==========");
            for (Piscifactoria piscifactoria : fishFarms) {
                piscifactoria.nextDay(estadisticas);
            }
            System.out.println("===========================================");
            this.days++;
            Registros.registrarNextDay(this.days, i, i, i, i);
            if (this.days % 10 == 0) {
                pedidilloAutomata();
            }
        }
        if(this.centralWarehouse != null) {
            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
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
                        int spaceA = selectedFishFarm.getWarehouseA().getSpace();
                        int spaceV = selectedFishFarm.getWarehouseV().getSpace();
                        int disscount = 0;
                        switch (option) {
                            case 1:
                                if (warehouseType == 1) {
                                    if (spaceA >= 5) {
                                        if (this.monedas.getMonedas() >= 5) {
                                            this.monedas.pagar(5);
                                            selectedFishFarm.getWarehouseA().addFood(5);
                                            Registros.registrarCompraComida(5, "animal", 5, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            selectedFishFarm.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 5) {
                                        if (this.monedas.getMonedas() >= 5) {
                                            this.monedas.pagar(5);
                                            selectedFishFarm.getWarehouseV().addFood(5);
                                            Registros.registrarCompraComida(5, "vegetal", 5, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            selectedFishFarm.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 2:
                                if (warehouseType == 1) {
                                    if (spaceA >= 10) {
                                        if (this.monedas.getMonedas() >= 10) {
                                            this.monedas.pagar(10);
                                            selectedFishFarm.getWarehouseA().addFood(10);
                                            Registros.registrarCompraComida(10, "animal", 10, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            selectedFishFarm.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 10) {
                                        if (this.monedas.getMonedas() >= 10) {
                                            this.monedas.pagar(10);
                                            selectedFishFarm.getWarehouseV().addFood(10);
                                            Registros.registrarCompraComida(10, "vegetal", 10, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            selectedFishFarm.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 3:
                                if (warehouseType == 1) {
                                    if (spaceA >= 25) {
                                        if (this.monedas.getMonedas() >= 20) {
                                            this.monedas.pagar(20);
                                            selectedFishFarm.getWarehouseA().addFood(25);
                                            Registros.registrarCompraComida(25, "animal", 25, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            selectedFishFarm.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA, false,
                                                    selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 25) {
                                        if (this.monedas.getMonedas() >= 20) {
                                            this.monedas.pagar(20);
                                            selectedFishFarm.getWarehouseV().addFood(25);
                                            Registros.registrarCompraComida(25, "vegetal", 20, false, selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            selectedFishFarm.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV, false, selectedFishFarm.getName());
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 4:
                                if (warehouseType == 1) {
                                    int payment = 0;
                                    if (spaceA >= 25) {
                                        disscount = (int) (spaceA / 25) * 5;
                                        payment = spaceA - disscount;
                                    } else {
                                        payment = spaceA;
                                    }
                                    if (this.monedas.getMonedas() >= payment) {
                                        this.monedas.pagar(payment);
                                        selectedFishFarm.getWarehouseA().addFood(spaceA);
                                        Registros.registrarCompraComida(payment, "animal", payment, false, selectedFishFarm.getName());
                                    } else {
                                        System.out.println("No se ha realizado la operación");
                                    }
                                } else if (warehouseType == 2) {
                                    int payment = 0;
                                    if (spaceV >= 25) {
                                        disscount = (int) (spaceV / 25) * 5;
                                        payment = spaceV - disscount;
                                    } else {
                                        payment = spaceV;
                                    }
                                    if (this.monedas.getMonedas() >= payment) {
                                        this.monedas.pagar(payment);
                                        selectedFishFarm.getWarehouseV().addFood(spaceV);
                                        Registros.registrarCompraComida(payment, "vegetal", payment, false, selectedFishFarm.getName());
                                    } else {
                                        System.out.println("No se ha realizado la operación");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Opción inválida");
                                break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Se ha introducido un tipo de opción incorrecta");
                }
            }
        } else {
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
                    } else {// LA CENTRALITA DEL ALAMASEN
                        System.out.println("Comida a añadir: ");
                        System.out.println("1.- 5");
                        System.out.println("2.- 10");
                        System.out.println("3.- 25");
                        System.out.println("4.- Llenar");
                        System.out.println("0.- Cancelar");
                        System.out.print("Seleccione una opción: ");
                        int option = sc.nextInt();
                        int spaceA = this.centralWarehouse.getWarehouseA().getSpace();
                        int spaceV = this.centralWarehouse.getWarehouseV().getSpace();
                        int disscount = 0;
                        switch (option) {
                            case 1:
                                if (warehouseType == 1) {
                                    if (spaceA >= 5) {
                                        if (this.monedas.getMonedas() >= 5) {
                                            this.monedas.pagar(5);
                                            this.centralWarehouse.getWarehouseA().addFood(5);
                                            Registros.registrarCompraComida(5, "animal", 5,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            this.centralWarehouse.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 5) {
                                        if (this.monedas.getMonedas() >= 5) {
                                            this.monedas.pagar(5);
                                            this.centralWarehouse.getWarehouseV().addFood(5);
                                            Registros.registrarCompraComida(5, "vegetal", spaceA,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            this.centralWarehouse.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 2:
                                if (warehouseType == 1) {
                                    if (spaceA >= 10) {
                                        if (this.monedas.getMonedas() >= 10) {
                                            this.monedas.pagar(10);
                                            this.centralWarehouse.getWarehouseA().addFood(10);
                                            Registros.registrarCompraComida(10, "animal", 10,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            this.centralWarehouse.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 10) {
                                        if (this.monedas.getMonedas() >= 10) {
                                            this.monedas.pagar(10);
                                            this.centralWarehouse.getWarehouseV().addFood(10);
                                            Registros.registrarCompraComida(10, "vegetal", 10,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            this.centralWarehouse.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 3:
                                if (warehouseType == 1) {
                                    if (spaceA >= 25) {
                                        if (this.monedas.getMonedas() >= 20) {
                                            this.monedas.pagar(20);
                                            this.centralWarehouse.getWarehouseA().addFood(25);
                                            Registros.registrarCompraComida(25, "animal", 20,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceA) {
                                            this.monedas.pagar(spaceA);
                                            this.centralWarehouse.getWarehouseA().addFood(spaceA);
                                            Registros.registrarCompraComida(spaceA, "animal", spaceA ,true, "");
                                            this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                } else if (warehouseType == 2) {
                                    if (spaceV >= 25) {
                                        if (this.monedas.getMonedas() >= 20) {
                                            this.monedas.pagar(20);
                                            this.centralWarehouse.getWarehouseV().addFood(25);
                                            Registros.registrarCompraComida(25, "vegetal", 20,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    } else {
                                        if (this.monedas.getMonedas() >= spaceV) {
                                            this.monedas.pagar(spaceV);
                                            this.centralWarehouse.getWarehouseV().addFood(spaceV);
                                            Registros.registrarCompraComida(spaceV, "vegetal", spaceV,true, "");
                                            this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                        } else {
                                            System.out.println("No se ha realizado la operación");
                                        }
                                    }
                                }
                                break;
                            case 4:
                                if (warehouseType == 1) {
                                    int payment = 0;
                                    if (spaceA >= 25) {
                                        disscount = (int) (spaceA / 25) * 5;
                                        payment = spaceA - disscount;
                                    } else {
                                        payment = spaceA;
                                    }
                                    if (this.monedas.getMonedas() >= payment) {
                                        this.monedas.pagar(payment);
                                        this.centralWarehouse.getWarehouseA().addFood(spaceA);
                                        Registros.registrarCompraComida(payment, "animal", payment,true, "");
                                        this.centralWarehouse.repartirComidaAnimal(this.fishFarms);
                                    } else {
                                        System.out.println("No se ha realizado la operación");
                                    }
                                } else if (warehouseType == 2) {
                                    int payment = 0;
                                    if (spaceV >= 25) {
                                        disscount = (int) (spaceV / 25) * 5;
                                        payment = spaceV - disscount;
                                    } else {
                                        payment = spaceV;
                                    }
                                    if (this.monedas.getMonedas() >= payment) {
                                        this.monedas.pagar(payment);
                                        this.centralWarehouse.getWarehouseV().addFood(spaceV);
                                        Registros.registrarCompraComida(payment, "vegetal", payment,true, "");
                                        this.centralWarehouse.repartirComidaVegetal(this.fishFarms);
                                    } else {
                                        System.out.println("No se ha realizado la operación");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Opción inválida");
                                break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Se ha introducido un tipo de opción incorrecta");
                }
            }
        }
    }

    /**
     * Método que muestra las estadisticas de los peces de la piscifactoria
     */
    public void showStats() {
        estadisticas.mostrar();
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
        int ganancias = 0;
        for (Pez pez : pecesParaVenta) {
            ganancias += pez.getFishStats().getMonedas();
            estadisticas.registrarVenta(pez.getFishStats().getNombre(), pez.getFishStats().getMonedas());
            // Nº peces vendidos, nombre de la pisci, ganancias totales
            Registros.registrarVenderPeces(pecesParaVenta.size(), tanque.getFishfarmName(), ganancias);
            tanque.getFishes().remove(pez);
        }
        this.monedas.setMonedas(this.monedas.getMonedas() + ganancias);
        System.out.println("Se han vendido " + pecesParaVenta.size() + " peces.");
        System.out.println("Ganancias obtenidas: " + ganancias + " monedas.");
    }

    /**
     * Metodo que elimina todos los peces muertos de los tanques de una
     * Piscifactoria
     */
    public void cleanTank() {
        Tanque tanque = this.selectTank();
        if (tanque == null) {
            return;
        } else {
            tanque.cleanDeadFishes();
            Registros.registrarLimpiarTanque(tanque.getTankNum(), tanque.getFishfarmName());
        }
    }

    /**
     * Metodo que borra todos los peces de un Tanque en concreto de una
     * piscifactoria
     */
    public void emptyTank() {
        Tanque tanque = this.selectTank();
        if (tanque == null) {
            return;
        } else {
            tanque.cleanTank();
            Registros.registrarVaciarTanque(tanque.getTankNum(), tanque.getFishfarmName());
        }
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
                                if ((500 * this.getRiverFishfarms()) <= this.monedas.getMonedas()) {
                                    Piscifactoria newRiverFishFarm = new PiscifactoriaRio(name, 0);
                                    fishFarms.add(newRiverFishFarm);
                                    int dolarescuesta = 500 * (this.getRiverFishfarms());
                                    this.monedas.pagar(500 * (this.getRiverFishfarms()));
                                    Registros.registrarCompraEdificio("piscifactoria", dolarescuesta, 0,
                                            newRiverFishFarm.getName());
                                }
                                validType = true;
                                validSelection = true;
                            } else if (type.equals("Mar") || type.equals("mar")) {
                                if (this.getSeaFishfarms() > 0) {
                                    if ((2000 * this.getSeaFishfarms()) <= this.monedas.getMonedas()) {
                                        Piscifactoria newSeaFishFarm = new PiscifactoriaMar(name, 0);
                                        fishFarms.add(newSeaFishFarm);
                                        int dolarescuesta = 500 * (this.getSeaFishfarms());
                                        this.monedas.pagar(2000 * getSeaFishfarms());
                                        Registros.registrarCompraEdificio("mar", dolarescuesta, 0,
                                                newSeaFishFarm.getName());
                                    }
                                } else {
                                    if (2000 <= this.monedas.getMonedas()) {
                                        Piscifactoria newSeaFishFarm = new PiscifactoriaMar(name, 0);
                                        fishFarms.add(newSeaFishFarm);
                                        this.monedas.pagar(2000 * getSeaFishfarms());
                                        Registros.registrarCompraEdificio("mar", 2000, 0, newSeaFishFarm.getName());
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
                            Registros.registrarCompraEdificio("almacen", 200, 0, "");
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
                sc.next();
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
                sc.next();
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
                        Registros.registrarCompraEdificio("tanque", 0, selectedFishFarm.getTankID(),
                                selectedFishFarm.getName());
                        break;
                    case 2:
                        selectedFishFarm.upgradeFood();
                        if (selectedFishFarm instanceof PiscifactoriaMar) {
                            Registros.registrarMejoraEdificio(selectedFishFarm.getName(),
                                    100 + selectedFishFarm.getWarehouseA().getMaxCap(), 200);
                        } else {
                            Registros.registrarMejoraEdificio(selectedFishFarm.getName(),
                                    25 + selectedFishFarm.getWarehouseV().getMaxCap(), 50);
                        }
                        validSelection = true;
                    case 3:
                        validSelection = true;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida");
                sc.next();
            }
        }
    }

    /**
     * Método para mejorar el Almacén Central
     */
    public void upgradeCentralWarehouse() {
        if (this.monedas.getMonedas() >= 200) {
            this.centralWarehouse.upgrade();
            this.monedas.pagar(200);
        } else {
            System.out.println("La mejora no se ha realizado");
        }
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
                if (this.monedas.getMonedas() >= fishPrice) {
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
                        case "Dorada":
                            selectedTank.addFishes(new Dorada(fishSex));
                            break;
                        default:
                            System.out.println("No se ha encontrado el pez");
                            break;
                    }
                    this.monedas.pagar(fishPrice);
                } else {
                    System.out.println("No se ha añadido el pez");
                    return;
                }
                estadisticas.registrarNacimiento(selectedFish);
                Registros.registrarComprarPeces(selectedFish, fishSex, fishPrice, selectedTank.getTankNum(),
                        selectedTank.getFishfarmName());
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
        Registros.registrarOpsOcultas(99, "", SISMonedas.getInstance().getMonedas());
    }

    /**
     * Método que añade cuatro peces aleatorios a una Piscifactoría seleccionada
     */
    public void addFishAmmount() {
        Tanque tanque = this.selectTank();
        ArrayList<PecesDatos> pecesCompatibles = new ArrayList<>();
        Random r = new Random();
        if (tanque != null) {
            if (tanque.getType()) {
                if (tanque.getFishType() == null) {
                    for (int i = 0; i < pecesDisponibles.length; i++) {
                        if (pecesDisponibles[i].getPiscifactoria().getName().equals("Río")
                                || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
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
                        if (pecesDisponibles[i].getPiscifactoria().getName().equals("Mar")
                                || pecesDisponibles[i].getPiscifactoria().getName().equals("Río y mar")) {
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
                    case "Dorada":
                        tanque.addFishes(new Dorada(fishSex));
                        break;
                    default:
                        System.out.println("No se ha encontrado el pez");
                        break;
                }
                estadisticas.registrarNacimiento(pecesCompatibles.get(pezRandom).getNombre());

            }
            Registros.registrarOpsOcultas(98, tanque.getFishfarmName(), 0);
        }
    }

    /**
     * Método que busca cuantas Piscifactorías de Mar hay
     * 
     * @return el número de Piscifactorías de Mar
     */
    public int getSeaFishfarms() {
        int seaFishfarms = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if (piscifactoria instanceof PiscifactoriaMar) {
                seaFishfarms += 1;
            }
        }
        return seaFishfarms;
    }

    /**
     * Método que busca cuantas Piscifactorías de Río hay
     * 
     * @return el número de Piscifactorías de Río
     */
    public int getRiverFishfarms() {
        int riverFishFarms = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if (piscifactoria instanceof PiscifactoriaRio) {
                riverFishFarms += 1;
            }
        }
        return riverFishFarms;
    }

    /**
     * Método que crea múltiples carpetas a partir de los nombres proporcionados.
     * 
     * @param nombresCarpetas
     */
    public static void crearCarpetas(String... nombresCarpetas) {
        for (String nombre : nombresCarpetas) {
            try {
                File carpeta = new File(nombre);
                if (!carpeta.exists()) {
                    boolean creada = carpeta.mkdir();
                    if (creada) {
                        System.out.println("Carpeta " + nombre + " creada");
                    } else {
                        System.out.println("No se ha podido crear la carpeta " + nombre);
                    }
                } else {
                    System.out.println("La carpeta " + nombre + " ya existe");
                }
            } catch (SecurityException se) {
                System.out.println("No se tienen los permisos necesarios para crear la carpeta: " + nombre);
            }

        }
    }

    /**
     * Método que implementa la opción 97
     */
    public void procesandoOpcion97() {

    }

    /**
     * Método que genera los pedidos
     */
    private void pedidilloAutomata() {
        Random rand = new Random();
        int idCliente = rand.nextInt(10) + 1;
        int indexPez = rand.nextInt(fishesNames.length);
        int idPez = indexPez + 1;

        // Verificar si existen en la BD antes de insertar
        if (!DAOPedidos.existeCliente(idCliente) || !DAOPedidos.existePez(idPez)) {
            System.out.println("Cliente o pez no encontrado en la base de datos. No se generó el pedido.");
            return;
        }

        int cantidad = rand.nextInt(41) + 10;
        PedidoDTO pedido = new PedidoDTO(0, idCliente, idPez, cantidad, 0);
        DAOPedidos.insertarPedido(pedido);
        System.out.println("Pedido automático generado: " + cantidad + " peces de tipo "
            + fishesNames[indexPez] + " para el cliente " + idCliente);
    }

    /**
     * Método que gestiona los pedidos
     */
    public void gestionarPedidos() {
        try {
            List<String> pedidosPendientes = DAOPedidos.listarPedidosPendientes();
            if (pedidosPendientes.isEmpty()) {
                System.out.println("No hay pedidos pendientes.");
                return;
            }
            System.out.println("Pedidos pendientes:");
            for (String p : pedidosPendientes) {
                System.out.println(p);
            }
            System.out.print("Ingrese el id del pedido a enviar (0 para cancelar): ");
            int idPedido = sc.nextInt();
            if (idPedido == 0) {
                return;
            }
        
            PedidoDTO pedido = DAOPedidos.obtenerPedidoPorId(idPedido);
            if (pedido == null) {
                System.out.println("Pedido no encontrado.");
                return;
            }
            if (pedido.getPecesEnviados() >= pedido.getCantidadPeces()) {
                System.out.println("El pedido ya está completado.");
                return;
            }
        
            // Seleccionar el tanque para enviar peces
            Tanque tanque = selectTank();
            if (tanque == null) {
                System.out.println("Operación cancelada.");
                return;
            }
        
            // Validar que el tanque tenga el tipo de pez requerido
            String nombrePezPedido = DAOPedidos.obtenerNombrePez(pedido.getIdPez());
            if (tanque.getFishType() != null && !tanque.getFishType().equals(nombrePezPedido)) {
                System.out.println("El tanque seleccionado no contiene el tipo de pez requerido para este pedido.");
                return;
            }
        
            // Obtener los peces maduros del tanque
            List<Pez> pecesMaduros = new ArrayList<>();
            for (Pez pez : tanque.getFishes()) {
                if (pez.isAlive() && pez.isMature()) {
                    pecesMaduros.add(pez);
                }
            }
            if (pecesMaduros.isEmpty()) {
                System.out.println("No hay peces maduros en el tanque para enviar.");
                return;
            }
        
            int pecesNecesarios = pedido.getCantidadPeces() - pedido.getPecesEnviados();
            int pecesAEnviar = Math.min(pecesNecesarios, pecesMaduros.size());
        
            for (int i = 0; i < pecesAEnviar; i++) {
                tanque.getFishes().remove(pecesMaduros.get(i));
            }
            pedido.setPecesEnviados(pedido.getPecesEnviados() + pecesAEnviar);
            DAOPedidos.actualizarPedido(pedido);
        
            System.out.println("Se han enviado " + pecesAEnviar + " peces para el pedido " + idPedido + ".");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida");
            sc.next();
        }
    }

    /**
     * @return devuelve el Almacén central del simulador
     */
    public AlmacenCentral getAlmacenCentral(){
        return this.centralWarehouse;
    }

    /**
     * Método que permite poner un Almacén Central al simulador
     * @param almacen almacén a poner
     */
    public void setAlmacenCentral(AlmacenCentral almacen){
        this.centralWarehouse = almacen;
    }

    /**
     * Método que permite añadir una piscifactoria al simulador
     * @param p piscifactoria a añadir
     */
    public void addPisci(Piscifactoria p){
        this.fishFarms.add(p);
    }

    /**
     * Método que permite seleccionar una Piscifactoría de Mar
     * @return Piscifactoría de Mar seleccionada
     */
    public Piscifactoria selectPisciMar(){
        List<Piscifactoria> piscisMar = new ArrayList<Piscifactoria>();
        System.out.println("--------------------------- Piscifactorías ---------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (int i = 0; i < fishFarms.size(); i++) {
            if(fishFarms.get(i) instanceof PiscifactoriaMar) {
                System.out.println((i + 1) + ".- " + fishFarms.get(i).getName() + "[" + fishFarms.get(i).fishesAlive() + "/"
                    + fishFarms.get(i).occuped() + "/" + fishFarms.get(i).maxFishes() + "]");
                piscisMar.add(fishFarms.get(i));
            }
        }
        System.out.println("0.- Cancelar");

        Piscifactoria selected = null;
        while (selected == null) {
            try {
                System.out.print("Seleccione una opción: ");
                int option = sc.nextInt();
                if (option == 0) {
                    return null;
                }
                if (option > 0 && option - 1 < piscisMar.size()) {
                    selected = piscisMar.get(option - 1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida");
                sc.next();
            }
        }
        return selected;
    }

    /**
     * Método que permite seleccionar una Piscifactoría de Río
     * @return Piscifactoría de Río seleccionada
     */
    public Piscifactoria selectPisciRio(){
        List<Piscifactoria> piscisRio = new ArrayList<Piscifactoria>();
        System.out.println("--------------------------- Piscifactorías ---------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for (int i = 0; i < fishFarms.size(); i++) {
            if(fishFarms.get(i) instanceof PiscifactoriaMar) {
                System.out.println((i + 1) + ".- " + fishFarms.get(i).getName() + "[" + fishFarms.get(i).fishesAlive() + "/"
                    + fishFarms.get(i).occuped() + "/" + fishFarms.get(i).maxFishes() + "]");
                piscisRio.add(fishFarms.get(i));
            }
        }
        System.out.println("0.- Cancelar");

        Piscifactoria selected = null;
        while (selected == null) {
            try {
                System.out.print("Seleccione una opción: ");
                int option = sc.nextInt();
                if (option == 0) {
                    return null;
                }
                if (option > 0 && option - 1 < piscisRio.size()) {
                    selected = piscisRio.get(option - 1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida");
                sc.next();
            }
        }
        return selected;
    }
    
    /**
     * Método que reparte una cantidad de comida animal entre las piscifactorias
     * @param cantidad cantidad de comida a añadir
     */
    public void repartirComidaAnimal(int cantidad) {
        int almacenes = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if(piscifactoria.getWarehouseA() != null){
                almacenes++;
            }
        }
        int reparto = (int)(cantidad / almacenes);
        for (Piscifactoria piscifactoria : fishFarms) {
            piscifactoria.getWarehouseA().addFood(reparto);
        }
    }

    /**
     * Método que reparte una cantidad de comida vegetal entre las piscifactorias
     * @param cantidad cantidad de comida a añadir
     */
    public void repartirComidaVegetal(int cantidad) {
        int almacenes = 0;
        for (Piscifactoria piscifactoria : fishFarms) {
            if(piscifactoria.getWarehouseV() != null){
                almacenes++;
            }
        }
        int reparto = (int)(cantidad / almacenes);
        for (Piscifactoria piscifactoria : fishFarms) {
            piscifactoria.getWarehouseV().addFood(reparto);
        }
    }

    /**
     * @return días del Simulador
     */
    public int getDays() {
        return days;
    }

    /**
     * @return Lista de Piscifactorias del Simulador
     */
    public List<Piscifactoria> getFishFarms() {
        return fishFarms;
    }

    /**
     * @return Nombre de la empresa/partida
     */
    public String getName() {
        return name;
    }

    /**
     * @return Monedas del sistema
     */
    public SISMonedas getMonedas() {
        return monedas;
    }

    /**
     * @return Almacén Central del Simulador
     */
    public AlmacenCentral getCentralWarehouse() {
        return centralWarehouse;
    }

    /**
     * @return Array con los peces disponibles
     */
    public static PecesDatos[] getPecesDisponibles() {
        return pecesDisponibles;
    }

    /**
     * @return Helper del sistema
     */
    public Helper getHelper() {
        return helper;
    }

    /**
     * @return Array con los nombres de los peces disponibles
     */
    public static String[] getFishesNames() {
        return fishesNames;
    }

    /**
     * @return Objeto para guardar y gestionar las Estadísticas
     */
    public static Estadisticas getEstadisticas() {
        return estadisticas;
    }

    /**
     * @return Objeto que genera y gestiona las recompensas del sistema
     */
    public GenerarRecompensa getGenerar() {
        return generar;
    }

    /**
     * @param nombre Nuevo nombre de empresa/partida
     */
    public void setName(String nombre){
        this.name = nombre;
    }

    /**
     * @param dia dia del Simulador
     */
    public void setDays(int dia) {
        this.days = dia;
    }

    /**
    * Método que permite establecer el sistema de monedas del simulador.
    * @param monedas La instancia de SISMonedas a establecer.
    */
    public void setMonedas(SISMonedas monedas) {
        this.monedas = monedas;
    }

    public void cargarPartida(String nombrePartida) {
        System.out.println("Cargando partida: " + nombrePartida);
    
        // Llamar al método de carga
        Cargado.load(this, nombrePartida);
    
        // Inicializar log y transcripciones después de cargar la partida
        Registros.getInstance().crearRegistro(this.name);
    
        // Registrar en el log que se ha cargado una partida
        Registros.registrarInicio(this.name, this.monedas.getMonedas(), fishesNames, fishFarms.get(0).getName());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Simulador sim = new Simulador();

        try {
            // Generar la base de datos si no existe
            GeneradorBD.generarBD();

            // Inicializar el sistema (cargar o crear una partida)
            sim.init();

            // Iniciar el menú principal
            sim.menu();

            // Guardar la partida antes de salir
            new Guardado(sim).guardarPartida();

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            Registros.cerrarRegistros();
            Conexion.cerrarConexion();
            sc.close();
        }
    }
}
