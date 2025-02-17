package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import propiedades.PecesDatos;
import sistema.Simulador;

public class GeneradorBD {


    public static void crearTablas() {
        Connection conexion = Conexion.obtenerConexion();
        if (conexion != null) {
            try (Statement stm = conexion.createStatement()) {
                String peces= "CREATE TABLE IF NOT EXISTS peces(" +
                    "id INT AUTO_INCREMENT," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "nombre_ciente VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY(id)";
                stm.execute(peces);

                String clientes = "CREATE TABLE IF NOT EXISTS clientes(" +
                    "id INT AUTO_INCREMENT," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "nif VARCHAR(50) NOT NULL UNIQUE," +
                    "telefono VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY(id)";
                stm.execute(clientes);

                String pedidos = "CREATE TABLE IF NOT EXISTS pedidos(" +
                    "id INT AUTO_INCREMENT," +
                    "id_cliente INT NOT NULL," +
                    "id_pez INT NOT NULL," +
                    "cantidad_peces INT NOT NULL," +
                    "peces_enviados INT NOT NULL," +
                    "PRIMARY KEY(id)," +
                    "FOREIGN KEY(id_cliente) REFERENCES clientes(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(id_pez) REFERENCES peces(id) ON DELETE CASCADE" +
                    ")";
                stm.execute(pedidos);
                System.out.println("Tablas creadas con éxito");
            } catch (SQLException e) {
                System.err.println("Error al crear las tablas: " + e.getMessage());
            } finally {
                Conexion.cerrarConexion();
            }
        }
    }
    
    public static void insertarPeces(){
        Connection conexion = Conexion.obtenerConexion();
        if(conexion !=null)
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO peces (nombre, nombre_cliente) VALUES (?,?)")) {
            for (String pez : Simulador.getFishesNames()) {
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void insertarClientes(){

    }

    public static void insertarDatos(){

    }

}
