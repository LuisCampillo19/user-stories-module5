package com.empresa.talenthub.model.dao;

import com.empresa.talenthub.config.ConexionDB;
import com.empresa.talenthub.model.entity.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC del DAO de Empleado.
 *
 * HU5 — TASK 2: CRUD seguro con PreparedStatement
 *
 * SEGURIDAD — ¿Por qué PreparedStatement y NO Statement?
 *
 * Statement construye la SQL concatenando strings directamente:
 *     String sql = "SELECT * FROM empleados WHERE nombre = '" + input + "'";
 * Si el usuario escribe: ' OR '1'='1' -- 
 * la consulta se convierte en:
 *     SELECT * FROM empleados WHERE nombre = '' OR '1'='1' --'
 * Esto devuelve TODOS los registros → ataque de Inyección SQL exitoso.
 *
 * PreparedStatement usa placeholders (?) que se envían separados de la SQL:
 *     PreparedStatement ps = conn.prepareStatement("SELECT * FROM empleados WHERE nombre = ?");
 *     ps.setString(1, input);
 * El driver JDBC escapa automáticamente cualquier carácter especial en el input,
 * haciendo IMPOSIBLE la inyección de SQL. El input siempre se trata como dato,
 * nunca como parte de la instrucción SQL.
 *
 * Además, PreparedStatement es más eficiente: la BD compila el plan de ejecución
 * una sola vez y lo reutiliza para diferentes valores de parámetros.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    // ==================== CREATE ====================

    /**
     * Inserta un nuevo empleado usando PreparedStatement para prevenir SQL Injection.
     * Usa try-with-resources para cerrar Connection y PreparedStatement automáticamente.
     */
    @Override
    public boolean insertar(Empleado empleado) {
        String sql = """
                INSERT INTO empleados (nombre, documento, departamento, salario_base, genero, es_activo, fecha_ingreso)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        // try-with-resources: conn y ps se cierran automáticamente
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setLong(2, empleado.getDocumento());
            ps.setString(3, empleado.getDepartamento());
            ps.setDouble(4, empleado.getSalarioBase());
            ps.setString(5, String.valueOf(empleado.getGenero()));
            ps.setBoolean(6, empleado.isEsActivo());
            ps.setString(7, empleado.getFechaIngreso());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
        // conn y ps ya están cerrados aquí — sin Memory Leaks
    }

    // ==================== READ (Listar todos) ====================

    /**
     * Lista todos los empleados.
     * try-with-resources cierra los tres recursos: Connection, PreparedStatement y ResultSet.
     */
    @Override
    public List<Empleado> listar() {
        String sql = "SELECT * FROM empleados ORDER BY id";
        List<Empleado> empleados = new ArrayList<>();

        // Tres recursos en el try-with-resources: se cierran en orden inverso (rs → ps → conn)
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return empleados;
    }

    // ==================== READ (Buscar por ID) ====================

    /**
     * Busca un empleado por ID usando PreparedStatement con parámetro.
     * Retorna Optional para evitar NullPointerException en el código cliente.
     */
    @Override
    public Optional<Empleado> buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            // ResultSet también dentro de try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }

        return Optional.empty();
    }

    // ==================== UPDATE ====================

    /**
     * Actualiza un empleado existente.
     * Todos los campos editables se pasan como parámetros de PreparedStatement.
     */
    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = """
                UPDATE empleados
                SET nombre = ?, documento = ?, departamento = ?,
                    salario_base = ?, genero = ?, es_activo = ?, fecha_ingreso = ?
                WHERE id = ?
                """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setLong(2, empleado.getDocumento());
            ps.setString(3, empleado.getDepartamento());
            ps.setDouble(4, empleado.getSalarioBase());
            ps.setString(5, String.valueOf(empleado.getGenero()));
            ps.setBoolean(6, empleado.isEsActivo());
            ps.setString(7, empleado.getFechaIngreso());
            ps.setInt(8, empleado.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    /**
     * Elimina un empleado por ID.
     * El parámetro ? previene que un atacante inyecte SQL adicional.
     */
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    // ==================== Mapper privado ====================

    /**
     * Mapea una fila del ResultSet a un objeto Empleado.
     * Método reutilizado por listar() y buscarPorId() para evitar duplicación.
     */
    private Empleado mapearResultSet(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getLong("documento"),
                rs.getString("departamento"),
                rs.getDouble("salario_base"),
                rs.getString("genero").charAt(0),
                rs.getBoolean("es_activo"),
                rs.getString("fecha_ingreso")
        );
    }
}
