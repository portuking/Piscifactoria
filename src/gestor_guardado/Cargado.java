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
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que se encarga de cargar partidas guardadas
 * 
 * @author Adrián Ces López
 * @author Manuel Abalo Rietz
 * @author Pablo Dopazo Suárez
 */
public class Cargado {

     /**
     * Método para cargar una partida guardada.
     * @param simulador Instancia del simulador donde se cargará la partida.
     * @param nombrePartida Nombre de la partida que se desea cargar.
     */
    public static void load(Simulador simulador, String nombrePartida) {
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
            if (json.has("piscifactorias")) {
                JsonArray piscifactoriasArray = json.getAsJsonArray("piscifactorias");
                for (JsonElement piscifactoriaElement : piscifactoriasArray) {
                    JsonObject piscifactoriaJson = piscifactoriaElement.getAsJsonObject();
                    if (!piscifactoriaJson.has("nombre") || !piscifactoriaJson.has("tipo")) {
                        System.out.println("Advertencia: Piscifactoria con estructura incompleta. Ignorando...");
                        continue;
                    }

                    String nombrePiscifactoria = piscifactoriaJson.get("nombre").getAsString();
                    boolean esMar = piscifactoriaJson.get("tipo").getAsInt() == 1;
                    int comidaAnimal = 0;
                    int comidaVegetal = 0;
                    if (piscifactoriaJson.has("comida")) {
                        JsonObject comidaJson = piscifactoriaJson.getAsJsonObject("comida");
                        if (comidaJson.has("animal")) {
                            comidaAnimal = comidaJson.get("animal").getAsInt();
                        }
                        if (comidaJson.has("vegetal")) {
                            comidaVegetal = comidaJson.get("vegetal").getAsInt();
                        }
                    }
                    List<Tanque> tanquesCargados = new ArrayList<>();
                    if (piscifactoriaJson.has("tanques")) {
                        JsonArray tanquesArray = piscifactoriaJson.getAsJsonArray("tanques");
                        for (JsonElement tanqueElement : tanquesArray) {
                            JsonObject tanqueJson = tanqueElement.getAsJsonObject();
                            if (!tanqueJson.has("capacidad") || !tanqueJson.has("id") || !tanqueJson.has("esRio")) {
                                System.out.println("Advertencia: Tanque con estructura incompleta. Ignorando...");
                                continue;
                            }

                            int capacidad = tanqueJson.get("capacidad").getAsInt();
                            int id = tanqueJson.get("id").getAsInt();
                            boolean esRio = tanqueJson.get("esRio").getAsBoolean();
                            Tanque tanque = new Tanque(capacidad, id, esRio, null);
                            if (tanqueJson.has("peces")) {
                                JsonArray pecesArray = tanqueJson.getAsJsonArray("peces");
                                for (JsonElement pezElement : pecesArray) {
                                    JsonObject pezJson = pezElement.getAsJsonObject();
                                    if (!pezJson.has("nombre") || !pezJson.has("edad") || !pezJson.has("sexo") || !pezJson.has("vivo")) {
                                        System.out.println("Advertencia: Pez con estructura incompleta. Ignorando...");
                                        continue;
                                    }

                                    String nombrePez = pezJson.get("nombre").getAsString();
                                    int edad = pezJson.get("edad").getAsInt();
                                    boolean sexo = pezJson.get("sexo").getAsBoolean();
                                    boolean vivo = pezJson.get("vivo").getAsBoolean();
                                    Pez pez = crearPez(nombrePez, sexo);
                                    if (pez != null) {
                                        pez.setAge(edad);
                                        pez.setAlive(vivo);
                                        tanque.addFishes(pez);
                                    } else {
                                        System.out.println("Advertencia: Tipo de pez desconocido - " + nombrePez);
                                    }
                                }
                            }

                            tanquesCargados.add(tanque);
                        }
                    }
                    Piscifactoria piscifactoria;
                    if (esMar) {
                        piscifactoria = new PiscifactoriaMar(nombrePiscifactoria, tanquesCargados);
                    } else {
                        piscifactoria = new PiscifactoriaRio(nombrePiscifactoria, tanquesCargados);
                    }
                    piscifactoria.getWarehouseA().setStock(comidaAnimal);
                    piscifactoria.getWarehouseV().setStock(comidaVegetal);
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

    /**
     * Método para crear un pez según su tipo.
     * @param nombrePez Nombre del pez.
     * @param sexo Sexo del pez.
     * @return Instancia del pez creado.
     */
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
