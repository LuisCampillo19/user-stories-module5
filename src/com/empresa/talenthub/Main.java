package com.empresa.talenthub;

import com.empresa.talenthub.arquitectura.NotasArquitectura;
import com.empresa.talenthub.logica.ReglasNegocio;
import com.empresa.talenthub.modelo.Empleado;
import com.empresa.talenthub.modelo.EmpresaRecord;

public class Main {
    public static void main(String[] args) {
        // Text Block de Java 17
        // Estos eliminan los \n y las concatenaciones de los Strings
        String encabezado = """
                __________________________________
                    CORPORATE TALENT HUB v1.0
                    Sistema de Gestión de Talento
                __________________________________               
                """;
        System.out.println(encabezado);

        // Task 1: imprimir notas de arquitectura
        new NotasArquitectura().mostrar();

        // Task 2: instanciar Empleado (Java 8)
        Empleado empleado = new Empleado(
                (byte) 3,           // nivelAcceso
                (short) 101,        // codigoDepartamento
                1001,               // idEmpleado
                1098765432L,        // numeroDocumento
                0.05f,              // porcentajeDescuento
                3500000.00,         // salarioBase
                'M',                // genero
                true,               // esActivo
                "Luis Campillo"     // nombre
        );
        System.out.println("Empleado creado: " + empleado);

        // Task 2. instanciar con redcord (Java 17)
        EmpresaRecord empresa = new EmpresaRecord(
                "Corporate Talent Hub S.A.S",
                "900.123.456-7",
                2020
        );
        System.out.println("Empresa: " + empresa);

        // Task 3: motor de reglas y jerarquía de operadores
        System.out.println("\n=== MOTOR DE REGLAS ===");

        ReglasNegocio reglas = new ReglasNegocio(
                empleado.getIdEmpleo(),           // idEmpleado (par → tendrá bono extra)
                empleado.getSalarioBase(),     // salarioBase
                400000.00,      // bonoMensual
                90,             // puntajeTest
                27,             // edad
                1,              // idSede
                false           // esActivo
        );

        // Salario final
        double salarioFinal = reglas.calcularSalarioFinal();
        System.out.println("Salario final calculado: $" + salarioFinal);

        // Bono extra por ID par
        if (reglas.tieneBonoExtra()) {
            System.out.println("ID par detectado → aplicando bono extra");
            reglas.aplicarBonoExtra(500000.00);
        } else {
            System.out.println("ID impar → sin bono extra");
        }

        // Elegibilidad
        boolean esElegible = reglas.validarElegibilidad();
        System.out.println("¿Empleado elegible? " + esElegible);

        // Task 4: Laboratorio - Helpful nullPointerExceptions
        System.out.println("LABORATORIO NPE");

        /*
         * Asignamos null a un atributo String del empleado.
         * En Java, null significa que la referencia no apunta a ningún objeto en el Heap.
         * Los tipos primitivos (int, double, boolean...) NUNCA pueden ser null.
         * Solo los tipos de referencia (String, objetos) pueden serlo.
         */
        empleado.setNombre(null);

        try {
            /*
             * Intentamos llamar a un método sobre el String nulo.
             * Esto lanza NullPointerException en tiempo de ejecución.
             *
             * JAVA 8 mostraba solo:
             *   NullPointerException  (sin más contexto — difícil de depurar)
             *
             * JAVA 14+ con Helpful NPE muestra exactamente:
             *   Cannot invoke "String.length()" because the return value of
             *   "Empleado.getNombre()" is null
             *
             * Esto ahorra tiempo real de debugging: sabes qué variable
             * es null, qué método se intentó invocar y en qué línea.
             * En proyectos grandes con cadenas de llamadas esto es crítico.
             */
            int longitud = empleado.getNombre().length();
            System.out.println("Longitud del nombre: " + longitud);

        } catch (NullPointerException e) {
            System.out.println("NullPointerException capturada.");
            System.out.println("Mensaje (Java 14+ Helpful NPE): " + e.getMessage());
            System.out.println("En Java 8 este mensaje era null o vacio — sin contexto util.");
        }

        // Restauramos el nombre para continuar
        empleado.setNombre("Luis Campillo");

        // Task 4: Comparación de referencias con el ==
        System.out.println("LABORATORIO REFERENCIAS");

        Empleado empleado2 = new Empleado(
                (byte) 3,
                (short) 101,
                1002,
                1098765432L,
                0.05f,
                3500000.00,
                'M',
                true,
                "Luis Campillo"
        );

        /*
         * COMPARACIÓN CON == entre objetos:
         *
         * En Java, == sobre objetos NO compara el contenido (datos).
         * Compara las REFERENCIAS, es decir, si ambas variables apuntan
         * exactamente a la misma dirección de memoria en el Heap.
         *
         * empleado y empleado2 tienen los mismos datos, pero son dos objetos
         * distintos creados con 'new' → viven en posiciones diferentes del Heap
         * → == devuelve false.
         *
         * Para comparar contenido se debe usar .equals(), que compara
         * campo por campo si se sobreescribe correctamente.
         *
         * Heap:
         *   [0x1A3F] ← empleado apunta aquí
         *   [0x4B2C] ← empleado2 apunta aquí  (diferente dirección)
         */
        boolean mismaReferencia = (empleado == empleado2);
        System.out.println("empleado1 == empleado2 (misma referencia): " + mismaReferencia);
        System.out.println("Razon: son dos objetos distintos en el Heap,");
        System.out.println("aunque tengan los mismos datos internos.\n");

        /*
         * Si una variable apunta al MISMO objeto, == sí devuelve true.
         * empleado3 no es una copia — es la misma referencia del Heap.
         */
        Empleado empleado3 = empleado;
        boolean mismaReferencia2 = (empleado == empleado3);
        System.out.println("empleado1 == empleado3 (misma referencia): " + mismaReferencia2);
        System.out.println("Razon: empleado3 apunta a la misma direccion del Heap que empleado1.");

        System.out.println("\nFIN");
        }

    }