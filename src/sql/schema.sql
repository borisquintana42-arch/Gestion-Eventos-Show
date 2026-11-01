
-- Base de datos: Gestión de Eventos y Shows
-- Proyecto 2 - Programación III

CREATE DATABASE IF NOT EXISTS gestion_eventos_show
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE gestion_eventos_show;

-- Tabla de eventos
CREATE TABLE IF NOT EXISTS eventos (
                                       id INT NOT NULL AUTO_INCREMENT,
                                       nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    capacidad_maxima INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_eventos_nombre (nombre),
    INDEX idx_eventos_fecha (fecha)
    ) ENGINE=InnoDB;

-- Tabla de ventas
CREATE TABLE IF NOT EXISTS ventas (
                                      id INT NOT NULL AUTO_INCREMENT,
                                      evento_id INT NOT NULL,
                                      tipo_boleto VARCHAR(30) NOT NULL,
    cantidad INT NOT NULL,
    total_pagar DECIMAL(10,2) NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_ventas_evento (evento_id),
    CONSTRAINT fk_ventas_evento
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    ) ENGINE=InnoDB;

-- Comprobar que las tablas existen
SHOW TABLES;

-- Consultar los registros
SELECT * FROM eventos;
SELECT * FROM ventas;
