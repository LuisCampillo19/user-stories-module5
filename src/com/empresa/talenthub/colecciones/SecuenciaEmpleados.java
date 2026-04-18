package com.empresa.talenthub.colecciones;

import com.empresa.talenthub.modelo.Empleado;

import java.util.ArrayList;
import java.util.List;

/**
 * TASK 3 - El salto a Java 21: Sequenced Collections (LTS moderno).
 *
 * Java 21 introdujo la interfaz SequencedCollection, que agrega a List,
 * Deque y LinkedHashSet tres metodos directos: getFirst(), getLast() y
 * reversed(). Esto resuelve problemas clasicos del Java Legacy.
 *
 * COMPARACION 8/11 vs 21:
 *
 *   Legacy (Java 8/11):
 *     Empleado primero = lista.get(0);
 *     Empleado ultimo  = lista.get(lista.size() - 1);
 *     Collections.reverse(lista);  // muta la lista original, peligroso
 *
 *   Moderno (Java 21):
 *     Empleado primero = lista.getFirst();
 *     Empleado ultimo  = lista.getLast();
 *     var inversa      = lista.reversed();  // vista, no muta el original
 *
 * MEJORA EN LEGIBILIDAD Y PREVENCION DE ERRORES:
 *   - getFirst()/getLast() expresan la intencion directamente. Con indices
 *     manuales un "size() - 1" mal escrito produce IndexOutOfBoundsException
 *     silenciosos en produccion.
 *   - reversed() devuelve una VISTA inversa sin modificar la coleccion
 *     original, a diferencia de Collections.reverse() que muta in-place.
 *   - Si la lista esta vacia, getFirst()/getLast() lanzan NoSuchElementException
 *     de forma explicita y descriptiva, en vez de un IndexOutOfBoundsException
 *     generico.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class SecuenciaEmpleados {

    public static void demostrar(List<Empleado> empleados) {
        if (empleados.isEmpty()) {
            System.out.println("(Lista vacia, no se puede demostrar Sequenced Collections)");
            return;
        }

        System.out.println("\n--- Sintaxis Legacy (Java 8/11) ---");
        Empleado primeroLegacy = empleados.get(0);
        Empleado ultimoLegacy  = empleados.get(empleados.size() - 1);
        System.out.println("Primero (get(0)):            " + primeroLegacy.getNombre());
        System.out.println("Ultimo  (get(size()-1)):     " + ultimoLegacy.getNombre());

        System.out.println("\n--- Sintaxis Moderna (Java 21) ---");
        var primeroModerno = empleados.getFirst();
        var ultimoModerno  = empleados.getLast();
        System.out.println("Primero (getFirst()):        " + primeroModerno.getNombre());
        System.out.println("Ultimo  (getLast()):         " + ultimoModerno.getNombre());

        System.out.println("\n--- Lista en orden inverso con reversed() ---");
        // reversed() devuelve una vista; la lista original NO se altera.
        var inversa = empleados.reversed();
        for (var e : inversa) {
            System.out.println("  " + e.getIdEmpleo() + " - " + e.getNombre());
        }

        // Verificacion: la lista original sigue intacta despues de reversed()
        System.out.println("\nOrden original conservado (primer elemento sigue siendo "
                + empleados.getFirst().getNombre() + ")");
    }

    /** Pequenio helper para crear un ArrayList de prueba con datos demo. */
    public static ArrayList<Empleado> crearDemo() {
        var lista = new ArrayList<Empleado>();
        lista.add(new Empleado((byte) 1, (short) 10, 1001, 1020304050L, 0.05f, 2500000.0, 'M', true, "Ana"));
        lista.add(new Empleado((byte) 2, (short) 10, 1002, 1020304051L, 0.05f, 3200000.0, 'F', true, "Bruno"));
        lista.add(new Empleado((byte) 3, (short) 20, 1003, 1020304052L, 0.07f, 1800000.0, 'F', true, "Carla"));
        lista.add(new Empleado((byte) 2, (short) 20, 1004, 1020304053L, 0.05f, 4100000.0, 'M', true, "Diego"));
        return lista;
    }
}