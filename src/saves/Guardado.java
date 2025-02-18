package saves;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edificios.almacenes.AlmacenCentral;
import edificios.piscifactoria.Piscifactoria;
import edificios.piscifactoria.PiscifactoriaRio;
import edificios.tanque.Tanque;
import estadisticas.Estadisticas;
import peces.Pez;
import sistema.SISMonedas;
import sistema.Simulador;

/**
 * Clase para guardar el estado del simulador mediante JSON
 */
public class Guardado {

    private Simulador simulador;

    public Guardado(Simulador simulador) {
        this.simulador = simulador;
    }

    public void guardarPartida() {
        JsonObject estructuraJson = new JsonObject();
        Estadisticas stats = simulador.getEstadisticas();
        //peces implementados
        JsonArray pecesImplementados = new JsonArray();
        for (String pez : simulador.getFishesNames()) {
            pecesImplementados.add(pez);
        }
        estructuraJson.add("implementados", pecesImplementados);

        //información general
        estructuraJson.addProperty("empresa", simulador.getName());
        estructuraJson.addProperty("dia", simulador.getDays());
        estructuraJson.addProperty("monedas", simulador.getMonedas().getMonedas());
        estructuraJson.addProperty("orca", stats.exportarDatos(simulador.getFishesNames()));
        
        //almacén central
        JsonObject edificiosObjeto = new JsonObject();
        JsonObject almacenesObjeto = new JsonObject();
        JsonObject almacenObjeto = new JsonObject();
        AlmacenCentral almacenCentral = simulador.getCentralWarehouse();
        if (almacenCentral != null) {
            almacenObjeto.addProperty("disponible", true);
            almacenObjeto.addProperty("capacidad", almacenCentral.getMaxCap());
            JsonObject comidaObjeto = new JsonObject();
            comidaObjeto.addProperty("general", almacenCentral.getStock());
            almacenObjeto.add("comida", comidaObjeto);
        } else {
            almacenObjeto.addProperty("disponible", false);
            almacenObjeto.addProperty("capacidad", 200);
            JsonObject comidaObjeto = new JsonObject();
            comidaObjeto.addProperty("general", 0);
            almacenObjeto.add("comida", comidaObjeto);
        }
        almacenesObjeto.add("almacen", almacenObjeto);
        edificiosObjeto.add("almacen", almacenesObjeto);
        
        //piscifactorías
        JsonArray piscifactoriasArray = new JsonArray();
        for (Piscifactoria piscifactoria : simulador.getFishFarms()) {
            JsonObject piscifactoriaObjeto = new JsonObject();
            piscifactoriaObjeto.addProperty("nombre", piscifactoria.getName());
            piscifactoriaObjeto.addProperty("tipo", piscifactoria instanceof edificios.piscifactoria.PiscifactoriaMar ? 1 : 0);
            piscifactoriaObjeto.addProperty("capacidad", piscifactoria.getMaxFood());
            JsonObject comidaPiscifactoria = new JsonObject();
            comidaPiscifactoria.addProperty("general", piscifactoria.getCurrentFood());
            piscifactoriaObjeto.add("comida", comidaPiscifactoria);
            
            JsonArray tanquesArray = new JsonArray();
            for (Tanque tanque : piscifactoria.getTanques()) {
                JsonObject tanqueObjeto = new JsonObject();
                tanqueObjeto.addProperty("pez", tanque.getFishes().isEmpty() ? "" : tanque.getFishes().get(0).getName());
                tanqueObjeto.addProperty("num", tanque.getFishes().size());
                
                JsonObject datosTanque = new JsonObject();
                datosTanque.addProperty("vivos", tanque.fishesAlive());
                datosTanque.addProperty("maduros", tanque.matureFishes());
                datosTanque.addProperty("fertiles", tanque.fertiles());
                tanqueObjeto.add("datos", datosTanque);
                
                JsonArray pecesArray = new JsonArray();
                for (Pez pez : tanque.getFishes()) {
                    JsonObject pezObjeto = new JsonObject();
                    pezObjeto.addProperty("edad", pez.getAge());
                    pezObjeto.addProperty("sexo", pez.isFemale());
                    pezObjeto.addProperty("vivo", pez.isAlive());
                    pezObjeto.addProperty("maduro", pez.isMature());
                    pezObjeto.addProperty("fertil", pez.isFertile());
                    pezObjeto.addProperty("ciclo", pez.getReproductionCycle());
                    pezObjeto.addProperty("alimentado", false);
                    JsonObject extra = new JsonObject();
                    extra.addProperty("k", "v");
                    pezObjeto.add("extra", extra);
                    pecesArray.add(pezObjeto);
                }
                tanqueObjeto.add("peces", pecesArray);
                tanquesArray.add(tanqueObjeto);
            }
            piscifactoriaObjeto.add("tanques", tanquesArray);
            piscifactoriasArray.add(piscifactoriaObjeto);
        }
        
        estructuraJson.add("piscifactorias", piscifactoriasArray);
        estructuraJson.add("edificios", edificiosObjeto);
        
        // Guardado en archivo
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(estructuraJson);
        String nombreArchivo = "saves/" + simulador.getName() + ".save";
        
        File archivo = new File(nombreArchivo);
        archivo.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(json);
            System.out.println("Partida guardada en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}