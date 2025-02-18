package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.PedidoDTO;

public class DAOPedidos {
/**
     * Inserta un nuevo pedido en la tabla 'pedidos'.
     * Se asume que el pedido viene con 'peces_enviados' inicializado a 0.
     */
    public static void insertarPedido(PedidoDTO pedido) {
        Connection conn = Conexion.obtenerConexion();
        String sql = "INSERT INTO pedidos (id_cliente, id_pez, cantidad_peces, peces_enviados) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdPez());
            ps.setInt(3, pedido.getCantidadPeces());
            ps.setInt(4, pedido.getPecesEnviados());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getInt(1)); 
                }
            }
            System.out.println("Pedido insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar pedido: " + e.getMessage());
        }
    }

    /**
     * Actualiza el campo 'peces_enviados' de un pedido.
     */
    public static void actualizarPedido(PedidoDTO pedido) {
        Connection conn = Conexion.obtenerConexion();
        String sql = "UPDATE pedidos SET peces_enviados = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getPecesEnviados());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    /**
     * Devuelve una lista de cadenas que representan los pedidos pendientes (no completados),
     * ordenados por el nombre del pez (se realiza un JOIN con las tablas 'clientes' y 'peces').
     * Formato: [ref] Nombre cliente: nombre pez enviados/solicitado (X%)
     */
    public static List<String> listarPedidosPendientes() {
        List<String> lista = new ArrayList<>();
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT p.id, c.nombre AS cliente, pe.nombre AS pez, p.cantidad_peces, p.peces_enviados " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.id_cliente = c.id " +
                     "JOIN peces pe ON p.id_pez = pe.id " +
                     "WHERE p.peces_enviados < p.cantidad_peces " +
                     "ORDER BY pe.nombre";
        try (Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String cliente = rs.getString("cliente");
                String pez = rs.getString("pez");
                int cantidad = rs.getInt("cantidad_peces");
                int enviados = rs.getInt("peces_enviados");
                int porcentaje = (int) ((enviados * 100.0) / cantidad);
                String pedidoStr = "[" + id + "] " + cliente + ": " + pez + " " + enviados + "/" + cantidad + " (" + porcentaje + "%)";
                lista.add(pedidoStr);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos pendientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Devuelve una lista con los pedidos completados (aquellos en los que
     * peces_enviados >= cantidad_peces), ordenados por el id (orden de inserción).
     */
    public static List<String> listarPedidosCompletados() {
        List<String> lista = new ArrayList<>();
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT p.id, c.nombre AS cliente, pe.nombre AS pez, p.cantidad_peces, p.peces_enviados " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.id_cliente = c.id " +
                     "JOIN peces pe ON p.id_pez = pe.id " +
                     "WHERE p.peces_enviados >= p.cantidad_peces " +
                     "ORDER BY p.id";
        try (Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String cliente = rs.getString("cliente");
                String pez = rs.getString("pez");
                int cantidad = rs.getInt("cantidad_peces");
                int enviados = rs.getInt("peces_enviados");
                int porcentaje = (int) ((enviados * 100.0) / cantidad);
                String pedidoStr = "[" + id + "] " + cliente + ": " + pez + " " + enviados + "/" + cantidad + " (" + porcentaje + "%)";
                lista.add(pedidoStr);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos completados: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Borra todos los pedidos de la tabla 'pedidos' (opción oculta para pruebas).
     */
    public static void borrarPedidos() {
        Connection conn = Conexion.obtenerConexion();
        String sql = "TRUNCATE TABLE pedidos";
        try (Statement stm = conn.createStatement()) {
            stm.executeUpdate(sql);
            System.out.println("Todos los pedidos han sido borrados.");
        } catch (SQLException e) {
            System.err.println("Error al borrar pedidos: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene un pedido en función de su id.
     */
    public static PedidoDTO obtenerPedidoPorId(int idPedido) {
        PedidoDTO pedido = null;
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int idCliente = rs.getInt("id_cliente");
                    int idPez = rs.getInt("id_pez");
                    int cantidad = rs.getInt("cantidad_peces");
                    int enviados = rs.getInt("peces_enviados");
                    pedido = new PedidoDTO(id, idCliente, idPez, cantidad, enviados);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedido por id: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion();
        }
        return pedido;
    }
    
    /**
     * Método auxiliar que, dado el id de un pez, devuelve su nombre.
     */
    public static String obtenerNombrePez(int idPez) {
        String nombre = null;
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT nombre FROM peces WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPez);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre del pez: " + e.getMessage());
        }
        return nombre;
    }

    public static boolean existeCliente(int idCliente) {
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT COUNT(*) FROM clientes WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cliente: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean existePez(int idPez) {
        Connection conn = Conexion.obtenerConexion();
        String sql = "SELECT COUNT(*) FROM peces WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPez);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar pez: " + e.getMessage());
        }
        return false;
    }
}
