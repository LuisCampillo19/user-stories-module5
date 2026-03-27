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
                1002,           // idEmpleado (par → tendrá bono extra)
                3500000.00,     // salarioBase
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
        }
    }