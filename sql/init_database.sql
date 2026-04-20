-- ============================================================
-- Corporate Talent Hub — Script de inicialización
-- HU5: Persistencia relacional con JDBC
-- Autor: LuisCampillo19
-- ============================================================

CREATE DATABASE IF NOT EXISTS talent_hub_db;
USE talent_hub_db;

-- Tabla principal de empleados
CREATE TABLE IF NOT EXISTS empleados (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100)    NOT NULL,
    documento       BIGINT          NOT NULL UNIQUE,
    departamento    VARCHAR(60)     NOT NULL,
    salario_base    DOUBLE          NOT NULL,
    genero          CHAR(1)         NOT NULL,
    es_activo       BOOLEAN         NOT NULL DEFAULT TRUE,
    fecha_ingreso   DATE            NOT NULL
);

-- Datos de prueba
INSERT INTO empleados (nombre, documento, departamento, salario_base, genero, es_activo, fecha_ingreso)
VALUES
    ('Carlos Mejía',      1001234567, 'Desarrollo',       4500000, 'M', true,  '2024-01-15'),
    ('Laura García',      1009876543, 'Recursos Humanos',  3800000, 'F', true,  '2023-06-10'),
    ('Andrés López',      1005551234, 'QA',               3200000, 'M', true,  '2025-02-01'),
    ('María Rodríguez',   1007778899, 'Desarrollo',       5100000, 'F', false, '2022-11-20'),
    ('Santiago Torres',    1003214567, 'Infraestructura',  4000000, 'M', true,  '2024-08-05');
