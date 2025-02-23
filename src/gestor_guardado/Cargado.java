 package gestor_guardado;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaMar;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
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
import sistema.Simulador;

/**
 * Clase que se encarga de cargar partidas guardadas
 * 
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class Cargado {

    public static void cargarPartida(Simulador simulador, String nombrePartida) {
        String nombreArchivo = "saves/" + nombrePartida + ".save";
        File archivo = new File(nombreArchivo);
    
        if (!archivo.exists()) {
            System.out.println("No se encontró una partida guardada con el nombre: " + nombrePartida);
            return;
        }
    
        try (FileReader reader = new FileReader(archivo)) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
    
            if (json == null) {
                System.out.println("El archivo de guardado está vacío o corrupto.");
                return;
            }
    
            // Cargar datos generales del simulador
            if (json.has("empresa")) {
                simulador.setName(json.get("empresa").getAsString());
            } else {
                System.out.println("Advertencia: No se encontró el nombre de la empresa. Usando valor predeterminado.");
                simulador.setName("Empresa Desconocida");
            }
    
            if (json.has("dia")) {
                simulador.setDays(json.get("dia").getAsInt());
            } else {
                System.out.println("Advertencia: No se encontró el día. Iniciando desde el día 1.");
                simulador.setDays(1);
            }
    
            if (json.has("monedas")) {
                simulador.getMonedas().setMonedas(json.get("monedas").getAsInt());
            } else {
                System.out.println("Advertencia: No se encontraron monedas. Iniciando con 100 monedas.");
                simulador.getMonedas().setMonedas(100);
            }
    
            // Cargar piscifactorías
            if (json.has("piscifactorias")) {
                JsonArray piscifactoriasArray = json.getAsJsonArray("piscifactorias");
                for (JsonElement piscifactoriaElement : piscifactoriasArray) {
                    JsonObject piscifactoriaJson = piscifactoriaElement.getAsJsonObject();
    
                    // Verificar campos obligatorios
                    if (!piscifactoriaJson.has("nombre") || !piscifactoriaJson.has("tipo")) {
                        System.out.println("Advertencia: Piscifactoria con estructura incompleta. Ignorando...");
                        continue;
                    }
    
                    String nombrePiscifactoria = piscifactoriaJson.get("nombre").getAsString();
                    boolean esMar = piscifactoriaJson.get("tipo").getAsInt() == 1;
    
                    // Crear la piscifactoría según su tipo
                    Piscifactoria piscifactoria = esMar ? new PiscifactoriaMar(nombrePiscifactoria) : new PiscifactoriaRio(nombrePiscifactoria);
    
                    // Cargar almacenes de comida
                    if (piscifactoriaJson.has("comida")) {
                        JsonObject comidaJson = piscifactoriaJson.getAsJsonObject("comida");
                        if (comidaJson.has("animal") && comidaJson.has("vegetal")) {
                            int comidaAnimal = comidaJson.get("animal").getAsInt();
                            int comidaVegetal = comidaJson.get("vegetal").getAsInt();
    
                            // Asegurarse de que las cantidades no excedan la capacidad máxima
                            comidaAnimal = Math.min(comidaAnimal, piscifactoria.getWarehouseA().getMaxCap());
                            comidaVegetal = Math.min(comidaVegetal, piscifactoria.getWarehouseV().getMaxCap());
    
                            piscifactoria.getWarehouseA().setStock(comidaAnimal);
                            piscifactoria.getWarehouseV().setStock(comidaVegetal);
                        } else {
                            System.out.println("Advertencia: Almacenes de comida incompletos. Iniciando con stock 0.");
                        }
                    }
    
                    // Cargar tanques
                    if (piscifactoriaJson.has("tanques")) {
                        JsonArray tanquesArray = piscifactoriaJson.getAsJsonArray("tanques");
                        for (JsonElement tanqueElement : tanquesArray) {
                            JsonObject tanqueJson = tanqueElement.getAsJsonObject();
    
                            // Verificar campos obligatorios del tanque
                            if (!tanqueJson.has("num") || !tanqueJson.has("pez")) {
                                System.out.println("Advertencia: Tanque con estructura incompleta. Ignorando...");
                                continue;
                            }
    
                            int numeroTanque = tanqueJson.get("num").getAsInt();
                            String tipoPez = tanqueJson.get("pez").getAsString();
                            boolean esRioTanque = !esMar; // Asumir que el tipo de tanque depende de la piscifactoria
    
                            // Buscar el tanque existente o crear uno nuevo si no existe
                            Tanque tanque = piscifactoria.getTanquePorNumero(numeroTanque);
                            if (tanque == null) {
                                tanque = new Tanque(25, numeroTanque, esRioTanque, piscifactoria);
                                piscifactoria.addTanque(tanque);
                            }
    
                            // Cargar peces
                            if (tanqueJson.has("peces")) {
                                JsonArray pecesArray = tanqueJson.getAsJsonArray("peces");
                                for (JsonElement pezElement : pecesArray) {
                                    JsonObject pezJson = pezElement.getAsJsonObject();
    
                                    // Verificar campos obligatorios del pez
                                    if (!pezJson.has("edad") || !pezJson.has("sexo") || !pezJson.has("vivo")) {
                                        System.out.println("Advertencia: Pez con estructura incompleta. Ignorando...");
                                        continue;
                                    }
    
                                    int edad = pezJson.get("edad").getAsInt();
                                    boolean sexo = pezJson.get("sexo").getAsBoolean();
                                    boolean vivo = pezJson.get("vivo").getAsBoolean();
                                    boolean maduro = pezJson.has("maduro") ? pezJson.get("maduro").getAsBoolean() : false;
                                    boolean fertil = pezJson.has("fertil") ? pezJson.get("fertil").getAsBoolean() : false;
                                    boolean alimentado = pezJson.has("alimentado") ? pezJson.get("alimentado").getAsBoolean() : false;
                                    boolean enfermo = pezJson.has("enfermo") ? pezJson.get("enfermo").getAsBoolean() : false;

    
                                    // Crear el pez según su tipo
                                    Pez pez = crearPez(tipoPez, sexo);
                                    if (pez != null) {
                                        pez.setAlive(vivo);
                                        pez.setAge(edad);
                                        pez.setMature(maduro);
                                        pez.setFertile(fertil);
                                        pez.setEat(alimentado); // Restaurar el estado de alimentación
                                        pez.setEnfermo(enfermo);
                                        tanque.addFishes(pez);
                                    } else {
                                        System.out.println("Advertencia: Tipo de pez desconocido - " + tipoPez);
                                    }
                                }
                            }
                        }
                    }
    
                    // Agregar la piscifactoría al simulador
                    simulador.addPisci(piscifactoria);
                }
            } else {
                System.out.println("Advertencia: No se encontraron piscifactorías en el archivo.");
            }
    
            System.out.println("Partida cargada con éxito: " + nombrePartida);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de guardado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar la partida: " + e.getMessage());
        }
    }
    
    private static Pez crearPez(String nombrePez, boolean sexo) {
        switch (nombrePez) {
            case "Besugo":
                return new Besugo(sexo);
            case "Caballa":
                return new Caballa(sexo);
            case "Carpa plateada":
                return new CarpaPlateada(sexo);
            case "Lenguado europeo":
                return new LenguadoEuropeo(sexo);
            case "Lubina europea":
                return new LubinaEuropea(sexo);
            case "Lubina rayada":
                return new LubinaRayada(sexo);
            case "Lucio del norte":
                return new LucioDelNorte(sexo);
            case "Pejerrey":
                return new Pejerrey(sexo);
            case "Perca europea":
                return new PercaEuropea(sexo);
            case "Róbalo":
                return new Robalo(sexo);
            case "Salmón atlántico":
                return new SalmonAtlantico(sexo);
            case "Salmón chinook":
                return new SalmonChinook(sexo);
            case "Dorada":
                return new Dorada(sexo);
            default:
                return null;
        }
    }
}
