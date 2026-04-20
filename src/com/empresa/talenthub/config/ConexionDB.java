package com.empresa.talenthub.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión JDBC hacia MySQL.
 *
 * HU5 — TASK 1: Gestión de conexiones y recursos (Legacy vs Modern)
 *
 * ============================================================
 * ANÁLISIS: SINTAXIS LEGACY (Java 8 hacia atrás)
 * ============================================================
 *
 * En versiones anteriores a Java 7, las conexiones JDBC se manejaban
 * manualmente con bloques try-catch-finally. El patrón típico era:
 *
 * <pre>{@code
 *     Connection conn = null;
 *     PreparedStatement ps = null;
 *     ResultSet rs = null;
 *     try {
 *         conn = DriverManager.getConnection(URL, USER, PASS);
 *         ps = conn.prepareStatement("SELECT * FROM empleados");
 *         rs = ps.executeQuery();
 *         // procesar resultados
 *     } catch (SQLException e) {
 *         e.printStackTrace();
 *     } finally {
 *         // Cierre manual: propenso a errores y fugas de memoria
 *         try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
 *         try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
 *         try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
 *     }
 * }</pre>
 *
 * PROBLEMAS DEL ENFOQUE LEGACY:
 * 1. Si el desarrollador olvida cerrar algún recurso → FUGA DE MEMORIA (Memory Leak).
 *    La conexión queda abierta en el pool y eventualmente el sistema se queda sin conexiones.
 * 2. El bloque finally requiere su propio try-catch por cada recurso → código verboso.
 * 3. Si close() del ResultSet lanza excepción, el PreparedStatement y Connection
 *    nunca se cierran → cadena de fugas.
 * 4. El orden de cierre importa (rs → ps → conn) y es responsabilidad del dev.
 *
 * ============================================================
 * SINTAXIS MODERNA: TRY-WITH-RESOURCES (Java 7+, obligatorio en Java 17/21)
 * ============================================================
 *
 * El bloque try-with-resources cierra automáticamente cualquier objeto que
 * implemente {@link java.lang.AutoCloseable} al salir del bloque, incluso
 * si ocurre una excepción.
 *
 * <pre>{@code
 *     try (Connection conn = ConexionDB.getConnection();
 *          PreparedStatement ps = conn.prepareStatement("SELECT * FROM empleados");
 *          ResultSet rs = ps.executeQuery()) {
 *
 *         // procesar resultados — conn, ps y rs se cierran automáticamente
 *     } catch (SQLException e) {
 *         e.printStackTrace();
 *     }
 *     // No se necesita bloque finally: el compilador inyecta el cierre.
 * }</pre>
 *
 * POR QUÉ PREVIENE FUGAS DE MEMORIA:
 * 1. El compilador genera el código de cierre en orden inverso al de apertura
 *    (rs → ps → conn), garantizando el orden correcto.
 * 2. Si close() de un recurso lanza excepción, los demás recursos se cierran
 *    de todas formas — la excepción se agrega como "suppressed exception".
 * 3. Es IMPOSIBLE olvidar cerrar un recurso: si está en el paréntesis del try,
 *    el compilador garantiza su cierre.
 * 4. Reduce el código de ~20 líneas (legacy) a ~5 líneas (moderno).
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class ConexionDB {

    // Configuración de conexión a MySQL
    private static final String URL  = "jdbc:mysql://localhost:3306/talent_hub_db";
    private static final String USER = "root";
    private static final String PASS = "";

    // Constructor privado — clase de utilidad, no se instancia
    private ConexionDB() {}

    /**
     * Obtiene una conexión a la base de datos.
     *
     * Esta conexión debe usarse SIEMPRE dentro de un try-with-resources:
     * <pre>{@code
     *     try (Connection conn = ConexionDB.getConnection()) {
     *         // usar conn
     *     }
     * }</pre>
     *
     * @return Connection activa hacia talent_hub_db
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Prueba rápida de conectividad.
     * Demuestra try-with-resources: la conexión se cierra automáticamente
     * al salir del bloque, sin necesidad de finally.
     */
    public static void testConnection() {
        // MODERNO (Java 17/21): try-with-resources
        // La Connection se cierra automáticamente al salir del bloque
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexión exitosa a la base de datos: " + conn.getCatalog());
            System.out.println("   Driver: " + conn.getMetaData().getDriverName());
            System.out.println("   URL:    " + conn.getMetaData().getURL());
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            System.out.println("   Verifique que MySQL esté ejecutándose y la BD 'talent_hub_db' exista.");
        }
        // Aquí conn ya está cerrada — garantizado por try-with-resources.
    }
}
