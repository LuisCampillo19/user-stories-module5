package com.empresa.talenthub;

import com.empresa.talenthub.menu.MenuPrincipal;
import com.empresa.talenthub.scanner.CapturaEmpleado;
import com.empresa.talenthub.matriz.Matrizdesempenio;
import com.empresa.talenthub.excepciones.ManejoExcepciones;

import com.empresa.talenthub.arquitectura.NotasArquitectura;
import com.empresa.talenthub.logica.ReglasNegocio;
import com.empresa.talenthub.modelo.Empleado;
import com.empresa.talenthub.modelo.EmpresaRecord;

import com.empresa.talenthub.colecciones.ConfiguracionCorporativa;
import com.empresa.talenthub.colecciones.GestorEmpleados;
import com.empresa.talenthub.colecciones.ReporteTalento;
import com.empresa.talenthub.colecciones.SecuenciaEmpleados;

import java.util.Scanner;

/**
 * CORPORATE TALENT HUB v3.0 - Semanas 1, 2 y 3
 *
 * SEMANA 1 (HU1) - Opcion 6 del menu:
 *   TASK 1: Notas de arquitectura y evolucion de Java
 *   TASK 2: Modelado con clase tradicional (Empleado) vs record (EmpresaRecord)
 *   TASK 3: Motor de reglas de negocio y jerarquia de operadores
 *   TASK 4: Laboratorio de Helpful NullPointerException y comparacion de referencias
 *
 * SEMANA 2 (HU2) - Opciones 1 a 5 del menu:
 *   TASK 1: Switch Legacy (Java 8) vs Switch Expression (Java 17/21)
 *   TASK 2: Scanner con var, do-while y validaciones con if/else
 *   TASK 3: Matriz de desempenio (double[][]), for anidados y casting double->int
 *   TASK 4: Manejo de excepciones (try-catch), diagnostico Java 17/21, operador ternario
 *
 * SEMANA 3 (HU3) - Opcion 7 del menu:
 *   TASK 1: ArrayList<Empleado> + HashMap<String, Empleado>
 *   TASK 2: Factory methods List.of() y Map.of() (inmutables)
 *   TASK 3: Sequenced Collections Java 21 (getFirst, getLast, reversed)
 *   TASK 4: removeIf + var + reporte de promedio de salarios
 *
 * @author Luis Campillo
 * @version 3.0
 */
public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        double[][] calificaciones = null;
        var sistemaActivo = true;

        System.out.println("========================================");
        System.out.println("  Bienvenido a Corporate Talent Hub     ");
        System.out.println("  Version 3.0 - Java 21                 ");
        System.out.println("========================================");

        // HU2 Task 4: demo de manejo de excepciones al inicio
        ManejoExcepciones.demostrarManejoErrores();

        do {
            MenuPrincipal.mostrarMenu();

            var opcion = ManejoExcepciones.leerEnteroSeguro(scanner, "");
            if (opcion == -1) {
                System.out.println("Intente de nuevo con una opcion valida.\n");
                continue;
            }

            System.out.println(">> " + MenuPrincipal.obtenerOpcionMenuLegacy(opcion) + "\n");

            switch (opcion) {
                case 1 -> CapturaEmpleado.registrarEmpleado(scanner);

                case 2 -> {
                    CapturaEmpleado.consultarEmpleados();
                    var total = CapturaEmpleado.getContadorEmpleados();
                    if (total > 0) {
                        var salarios = CapturaEmpleado.getSalariosEmpleados();
                        var nombres = CapturaEmpleado.getNombresEmpleados();
                        System.out.println("\n--- Categorias Salariales (Switch Expression Java 17/21) ---");
                        for (var i = 0; i < total; i++) {
                            System.out.println("  " + nombres[i] + ": "
                                    + MenuPrincipal.obtenerCategoriaSalarial(salarios[i]));
                        }
                    }
                }

                case 3 -> {
                    var total = CapturaEmpleado.getContadorEmpleados();
                    if (total == 0) {
                        System.out.println("Debe registrar al menos un empleado primero.");
                    } else {
                        calificaciones = Matrizdesempenio.capturarCalificaciones(
                                scanner, CapturaEmpleado.getNombresEmpleados(), total);
                        Matrizdesempenio.mostrarReporteDesempenio(
                                calificaciones, CapturaEmpleado.getNombresEmpleados(), total);

                        System.out.println("\n--- Evaluacion de Promocion (Operador Ternario) ---");
                        var umbral = 3.5;
                        for (var i = 0; i < total; i++) {
                            var suma = 0.0;
                            for (var j = 0; j < calificaciones[i].length; j++) {
                                suma += calificaciones[i][j];
                            }
                            var promedio = suma / calificaciones[i].length;
                            System.out.println("  " + CapturaEmpleado.getNombresEmpleados()[i] + ": "
                                    + ManejoExcepciones.evaluarPromocion(promedio, umbral));
                        }
                    }
                }

                case 4 -> {
                    System.out.print("Ingrese un salario para consultar su categoria: $");
                    var salarioConsulta = ManejoExcepciones.leerDecimalSeguro(scanner, "");
                    if (salarioConsulta >= 0) {
                        System.out.println("Resultado: " + MenuPrincipal.obtenerCategoriaSalarial(salarioConsulta));
                    }
                }

                case 5 -> {
                    sistemaActivo = false;
                    System.out.println("Cerrando Corporate Talent Hub. Hasta pronto!");
                }

                case 6 -> ejecutarDemoHU1();

                case 7 -> ejecutarDemoHU3();

                default -> System.out.println("Opcion no valida. Seleccione entre 1 y 7.");
            }

            System.out.println();

        } while (sistemaActivo);

        scanner.close();
    }

    // ================================================================
    // DEMO HU1 - Semana 1: arquitectura, modelado, reglas y NPE
    // ================================================================
    private static void ejecutarDemoHU1() {
        System.out.println("========================================");
        System.out.println(" DEMO HU1 - Arquitectura y modelado");
        System.out.println("========================================\n");

        // HU1 Task 1
        new NotasArquitectura().mostrar();

        // HU1 Task 2 - Empleado (clase tradicional Java 8)
        var empleado = new Empleado(
                (byte) 3, (short) 101, 1001, 1098765432L,
                0.05f, 3500000.00, 'M', true, "Luis Campillo");
        System.out.println("Empleado creado: " + empleado);

        // HU1 Task 2 - EmpresaRecord (Java 17)
        var empresa = new EmpresaRecord("Corporate Talent Hub S.A.S", "900.123.456-7", 2020);
        System.out.println("Empresa: " + empresa);

        // HU1 Task 3 - Motor de reglas
        System.out.println("\n=== MOTOR DE REGLAS ===");
        var reglas = new ReglasNegocio(
                empleado.getIdEmpleo(), empleado.getSalarioBase(),
                400000.00, 90, 27, 1, false);

        var salarioFinal = reglas.calcularSalarioFinal();
        System.out.println("Salario final calculado: $" + salarioFinal);

        if (reglas.tieneBonoExtra()) {
            System.out.println("ID par detectado -> aplicando bono extra");
            reglas.aplicarBonoExtra(500000.00);
        } else {
            System.out.println("ID impar -> sin bono extra");
        }
        System.out.println("Empleado elegible: " + reglas.validarElegibilidad());

        // HU1 Task 4 - Laboratorio NPE (Helpful NullPointerException Java 14+)
        System.out.println("\n=== LABORATORIO NPE ===");
        empleado.setNombre(null);
        try {
            var longitud = empleado.getNombre().length();
            System.out.println("Longitud: " + longitud);
        } catch (NullPointerException e) {
            System.out.println("NPE capturada.");
            System.out.println("Mensaje (Java 14+ Helpful NPE): " + e.getMessage());
            System.out.println("En Java 8 este mensaje era null o vacio, sin contexto util.");
        }
        empleado.setNombre("Luis Campillo");

        // HU1 Task 4 - Comparacion de referencias
        System.out.println("\n=== LABORATORIO REFERENCIAS ===");
        var empleado2 = new Empleado(
                (byte) 3, (short) 101, 1002, 1098765432L,
                0.05f, 3500000.00, 'M', true, "Luis Campillo");

        System.out.println("empleado == empleado2 (misma referencia): " + (empleado == empleado2));
        System.out.println("Razon: son dos objetos distintos en el Heap.");

        var empleado3 = empleado;
        System.out.println("empleado == empleado3 (misma referencia): " + (empleado == empleado3));
        System.out.println("Razon: empleado3 apunta a la misma direccion del Heap.");

        System.out.println("\n[FIN DEMO HU1]");
    }

    // ================================================================
    // DEMO HU3 - Semana 3: Java Collections Framework
    // ================================================================
    private static void ejecutarDemoHU3() {
        System.out.println("========================================");
        System.out.println(" DEMO HU3 - Java Collections Framework");
        System.out.println("========================================\n");

        // HU3 Task 1
        System.out.println("### TASK 1: ArrayList + HashMap ###");
        var gestor = new GestorEmpleados();
        for (var e : SecuenciaEmpleados.crearDemo()) {
            gestor.agregar(e);
        }
        gestor.listar();

        System.out.println("\nBusqueda O(1) por HashMap (ID=1003):");
        var encontrado = gestor.buscarPorId("1003");
        System.out.println("  -> " + (encontrado != null ? encontrado.getNombre() : "no encontrado"));

        System.out.println("\nEliminando ID=1002...");
        gestor.eliminar("1002");
        gestor.listar();

        // HU3 Task 2
        System.out.println("\n### TASK 2: Factory Methods (List.of, Map.of) ###");
        ConfiguracionCorporativa.imprimirCatalogo();

        // HU3 Task 3
        System.out.println("\n### TASK 3: Sequenced Collections (Java 21) ###");
        SecuenciaEmpleados.demostrar(gestor.getEmpleados());

        // HU3 Task 4
        System.out.println("\n### TASK 4: removeIf + var + reporte ###");
        var salarioMinimo = 2000000.0;
        System.out.println("Filtrando empleados con salario < " + salarioMinimo);
        var eliminados = ReporteTalento.filtrarPorSalarioMinimo(gestor.getEmpleados(), salarioMinimo);
        System.out.println("Eliminados por filtro: " + eliminados);
        gestor.listar();
        ReporteTalento.imprimirReporte(gestor.getEmpleados());

        System.out.println("\n[FIN DEMO HU3]");
    }
}