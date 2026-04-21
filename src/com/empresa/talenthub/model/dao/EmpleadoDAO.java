package com.empresa.talenthub.model.dao;

import com.empresa.talenthub.model.entity.Empleado;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO (Data Access Object) para la entidad Empleado.
 *
 * Define el contrato CRUD que debe cumplir cualquier implementación,
 * independientemente del motor de base de datos utilizado.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public interface EmpleadoDAO {

    /**
     * Inserta un nuevo empleado en la base de datos.
     * @param empleado Datos del empleado (sin id — lo genera la BD)
     * @return true si la inserción fue exitosa
     */
    boolean insertar(Empleado empleado);

    /**
     * Lista todos los empleados registrados.
     * @return Lista de empleados (vacía si no hay registros)
     */
    List<Empleado> listar();

    /**
     * Busca un empleado por su ID.
     * @param id Identificador del empleado
     * @return Optional con el empleado si existe, vacío si no
     */
    Optional<Empleado> buscarPorId(int id);

    /**
     * Actualiza los datos de un empleado existente.
     * @param empleado Empleado con los datos modificados (debe tener id)
     * @return true si se actualizó al menos un registro
     */
    boolean actualizar(Empleado empleado);

    /**
     * Elimina un empleado de la base de datos.
     * @param id Identificador del empleado a eliminar
     * @return true si se eliminó al menos un registro
     */
    boolean eliminar(int id);
}
