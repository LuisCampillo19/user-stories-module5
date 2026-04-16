package com.empresa.talenthub.logica;

import com.empresa.talenthub.personas.ConsultorExterno;
import com.empresa.talenthub.personas.Desarrollador;
import com.empresa.talenthub.personas.Empleado;
import com.empresa.talenthub.personas.Gerente;
import com.empresa.talenthub.personas.Persona;

/**
 * TASK 3 - Polimorfismo y Pattern Matching for instanceof.
 *
 * Esta clase contrasta tres estilos de inspeccion de tipos que han ido
 * evolucionando en Java:
 *
 *   1. LEGACY  (Java 8)    -> instanceof + casting manual obligatorio.
 *   2. MODERNO (Java 17)   -> Pattern Matching for instanceof.
 *   3. AVANZADO (Java 21)  -> Switch Pattern Matching sobre sealed classes.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class ValidadorRoles {

    /**
     * ESTILO LEGACY (Java 8): instanceof + casting manual.
     *
     * Problemas de este estilo:
     *   - El nombre del tipo aparece DOS veces (en el instanceof y en el cast),
     *     lo que multiplica el sitio donde hay que tocar si se renombra la clase.
     *   - Cada rama necesita una linea extra solo para hacer el cast antes
     *     de poder usar los metodos especificos del subtipo.
     *   - Si el cast se escribe mal (por ejemplo en refactors masivos),
     *     el codigo lanza ClassCastException en tiempo de ejecucion.
     *   - Es verboso: mucho ruido visual para una operacion conceptualmente
     *     simple ("quiero saber que tipo es y usar sus metodos").
     */
    public static String describirRolLegacy(Persona p) {
        String descripcion;

        if (p instanceof Desarrollador) {
            // Casting manual OBLIGATORIO en Java 8 - sin esto no hay acceso
            // a getLenguajePrincipal().
            Desarrollador dev = (Desarrollador) p;
            descripcion = "Dev que programa en " + dev.getLenguajePrincipal();
        } else if (p instanceof Gerente) {
            Gerente g = (Gerente) p;
            descripcion = "Gerente con presupuesto de $"
                    + String.format("%,.0f", g.getPresupuestoMensual());
        } else if (p instanceof ConsultorExterno) {
            ConsultorExterno c = (ConsultorExterno) p;
            descripcion = "Consultor externo de " + c.getEmpresaProveedora();
        } else if (p instanceof Empleado) {
            Empleado e = (Empleado) p;
            descripcion = "Empleado generico con salario $"
                    + String.format("%,.0f", e.getSalarioBase());
        } else {
            descripcion = "Persona sin rol definido";
        }

        return descripcion;
    }

    /**
     * ESTILO MODERNO (Java 16/17+): Pattern Matching for instanceof.
     *
     * Ventajas frente al estilo legacy:
     *   - La variable tipada se declara dentro del propio instanceof
     *     (ej: `p instanceof Desarrollador dev`). Ya no hay cast manual.
     *   - El compilador garantiza la seguridad del cast: no puede lanzar
     *     ClassCastException.
     *   - La variable solo existe en el scope donde el tipo esta probado,
     *     por lo que no contamina el resto del metodo.
     *   - Menos lineas, menos repeticion, misma intencion mas clara.
     */
    public static String describirRolModerno(Persona p) {
        // Fijate: `Desarrollador dev` se declara directamente en el instanceof.
        // No hay `(Desarrollador) p` por ninguna parte.
        if (p instanceof Desarrollador dev) {
            return "Dev que programa en " + dev.getLenguajePrincipal();
        }
        if (p instanceof Gerente g) {
            return "Gerente con presupuesto de $"
                    + String.format("%,.0f", g.getPresupuestoMensual());
        }
        if (p instanceof ConsultorExterno c) {
            return "Consultor externo de " + c.getEmpresaProveedora();
        }
        if (p instanceof Empleado e) {
            return "Empleado generico con salario $"
                    + String.format("%,.0f", e.getSalarioBase());
        }
        return "Persona sin rol definido";
    }

    /**
     * ESTILO AVANZADO (Java 21): Switch Pattern Matching sobre sealed class.
     *
     * Combina dos caracteristicas modernas:
     *   - Switch como expresion (Java 14).
     *   - Pattern matching en los `case` (Java 21 estable).
     *
     * Ventaja clave: al ser Persona una `sealed class`, el compilador SABE
     * que solo existen Empleado y ConsultorExterno como hijos directos,
     * y Desarrollador/Gerente como hijos de Empleado. Por eso exige cubrir
     * TODOS los casos: si manana anadieramos un nuevo hijo al `permits` de
     * Persona y olvidaramos actualizar este switch, el codigo no compilaria.
     * Ese contrato a nivel de compilador es lo que hace tan segura la
     * combinacion sealed + switch pattern matching.
     */
    public static String describirRolSwitchPattern(Persona p) {
        return switch (p) {
            case Desarrollador dev      -> "Dev que programa en " + dev.getLenguajePrincipal();
            case Gerente g              -> "Gerente con presupuesto de $"
                    + String.format("%,.0f", g.getPresupuestoMensual());
            case ConsultorExterno c     -> "Consultor externo de " + c.getEmpresaProveedora();
            case Empleado e             -> "Empleado generico con salario $"
                    + String.format("%,.0f", e.getSalarioBase());
        };
        // No hace falta `default`: al ser sealed la jerarquia, el compilador
        // verifica que cubrimos todas las variantes posibles (exhaustividad).
    }
}