package com.empresa.talenthub.colecciones;

import com.empresa.talenthub.modelo.Empleado;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TASK 1 - Migracion a ArrayList y HashMap (Legacy Java 8/11).
 *
 * EVOLUCION: en la Semana 2 guardabamos empleados en un arreglo fijo
 * (Empleado[10]), lo que obliga a conocer el tamanio de antemano y desperdicia
 * memoria si no se llena. Aqui sustituimos ese arreglo por las dos colecciones
 * dinamicas mas usadas del Java Collections Framework:
 *
 *   - ArrayList<Empleado>: crece automaticamente, permite iteracion en orden
 *     de insercion y acceso por indice en O(1).
 *   - HashMap<String, Empleado>: busqueda instantanea (O(1) promedio) por
 *     clave unica (el ID del empleado). Util para no tener que recorrer la
 *     lista completa cada vez que necesitamos un registro concreto.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class GestorEmpleados {

    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final HashMap<String, Empleado> indicePorId = new HashMap<>();

    /** Agrega un empleado a ambas colecciones manteniendolas sincronizadas. */
    public void agregar(Empleado e) {
        var clave = String.valueOf(e.getIdEmpleo());
        if (indicePorId.containsKey(clave)) {
            System.out.println("[AVISO] Ya existe un empleado con ID " + clave + ", no se agrega duplicado.");
            return;
        }
        empleados.add(e);
        indicePorId.put(clave, e);
    }

    /** Lista todos los empleados en el orden en que fueron agregados. */
    public void listar() {
        if (empleados.isEmpty()) {
            System.out.println("(No hay empleados registrados)");
            return;
        }
        System.out.println("--- Listado de empleados (" + empleados.size() + ") ---");
        for (var e : empleados) {
            System.out.println("  ID " + e.getIdEmpleo() + " | " + e.getNombre()
                    + " | salario base: " + e.getSalarioBase());
        }
    }

    /** Elimina un empleado por ID, tanto del ArrayList como del HashMap. */
    public boolean eliminar(String id) {
        var removido = indicePorId.remove(id);
        if (removido == null) return false;
        empleados.remove(removido);
        return true;
    }

    /** Busqueda O(1) promedio gracias al HashMap. */
    public Empleado buscarPorId(String id) {
        return indicePorId.get(id);
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
}