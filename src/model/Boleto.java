package model;

import java.util.List;

/**
 * Clase abstracta base para todos los tipos de boletos del sistema.
 * Define las propiedades comunes (tipo y precio) que comparten las
 * distintas categorías de boleto.
 */
public abstract class Boleto {

    private final String tipo;
    private final double precio;

    /**
     * Constructor protegido: las subclases son las encargadas de
     * asignar el tipo y el precio concreto de cada boleto.
     */
    protected Boleto(String tipo, double precio) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de boleto no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return tipo + " ($" + precio + ")";
    }

    /**
     * Devuelve la lista de beneficios incluidos en este tipo de boleto.
     *
     * @return lista con los beneficios del boleto
     */
    public abstract List<String> getBeneficios();
}