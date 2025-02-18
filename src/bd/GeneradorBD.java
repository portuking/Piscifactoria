package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import sistema.Simulador;

public class GeneradorBD {

    public static void crearTablas() {
        Connection conexion = Conexion.obtenerConexion();
        if (conexion != null) {
            try (Statement stm = conexion.createStatement()) {
                String peces = "CREATE TABLE IF NOT EXISTS peces(" +
                        "id INT AUTO_INCREMENT," +
                        "nombre VARCHAR(50) NOT NULL," +
                        "nombre_cientifico VARCHAR(50) NOT NULL," +
                        "PRIMARY KEY(id))";
                stm.execute(peces);

                String clientes = "CREATE TABLE IF NOT EXISTS clientes(" +
                        "id INT AUTO_INCREMENT," +
                        "nombre VARCHAR(50) NOT NULL," +
                        "nif VARCHAR(50) NOT NULL UNIQUE," +
                        "telefono VARCHAR(50) NOT NULL," +
                        "PRIMARY KEY(id))";
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

    public static void insertarPeces() {
        Connection conexion = Conexion.obtenerConexion();
        if (conexion != null)
            try (PreparedStatement ps = conexion
                    .prepareStatement("INSERT INTO peces (nombre, nombre_cientifico) VALUES (?,?)")) {
                for (String Nombrespez : Simulador.fishesNames) {
                    PecesDatos datos = AlmacenPropiedades.getPropByName(Nombrespez);
                    ps.setString(1, Nombrespez);
                    ps.setString(2, datos.getCientifico());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                System.err.println("Error al insertar los datos de peces: " + e.getMessage());
            }
    }

    public static void insertarClientes() {
        String[] nombres = {
                "Carlos", "Ana", "Luis", "María", "Javier",
                "Sofía", "Daniel", "Laura", "Pedro", "Elena"
        };
        String[] nifs = {
                "12345678A", "23456789B", "34567890C", "45678901D", "56789012E",
                "67890123F", "78901234G", "89012345H", "90123456I", "01234567J"
        };
        String[] telefonos = {
                "600123456", "611234567", "622345678", "633456789", "644567890",
                "655678901", "666789012", "677890123", "688901234", "699012345"
        };
        Connection conexion = Conexion.obtenerConexion();
        if (conexion != null)
            try (PreparedStatement ps = conexion
                    .prepareStatement("INSERT INTO clientes (nombre, nif, telefono) VALUES (?,?,?)")) {
                for (int i = 0; i < nombres.length; i++) {
                    ps.setString(1, nombres[i]);
                    ps.setString(2, nifs[i]);
                    ps.setString(3, telefonos[i]);
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                System.err.println("Error al insertar los datos de clientes: " + e.getMessage());
            }
    }

    public static void generarBD() {
        crearTablas();
        insertarPeces();
        //insertarClientes();
    }

}
