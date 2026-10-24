-- ============================================
-- Base de datos: Gestion de Eventos y Shows
-- Proyecto 2 - Programación III (EIF206)
-- Integrantes: Boris (GUI/JavaFX) y César (BD/JDBC)
-- ============================================

CREATE DATABASE IF NOT EXISTS gestion_eventos_show
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE gestion_eventos_show;

-- Tabla: eventos
-- Almacena la información básica de cada evento/show.
CREATE TABLE eventos (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    capacidad_maxima INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla: ventas
-- Registra cada venta de boletos vinculada a un evento (FK).
CREATE TABLE ventas (
    id INT NOT NULL AUTO_INCREMENT,
    evento_id INT NOT NULL,
    tipo_boleto VARCHAR(30) NOT NULL,
    cantidad INT NOT NULL,
    total_pagar DECIMAL(10,2) NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,eventosventaseventosnombreeventos
    PRIMARY KEY (id),
    CONSTRAINT fk_ventas_evento
        FOREIGN KEY (evento_id) REFERENCES eventos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Índices para mejorar búsquedas (funcionalidades nuevas: historial y filtros)
CREATE INDEX idx_ventas_evento ON ventas(evento_id);
CREATE INDEX idx_eventos_nombre ON eventos(nombre);
CREATE INDEX idx_eventos_fecha ON eventos(fecha);

USE gestion_eventos_show;ventas
SELECT * FROM eventos;

SELECT * FROM ventas;eventos

