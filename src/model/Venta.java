package model;

/**
 * Representa una venta de boletos para un evento.
 * Guarda el evento, el boleto vendido, la cantidad de boletos
 * y el total a pagar (cantidad x precio unitario del boleto).
 */
public class Venta implements Imprimible {

    private final Evento evento;
    private final Boleto boleto;
    private final int cantidad;
    private final double totalPagar;

    public Venta(Evento evento, Boleto boleto, int cantidad) {
        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo.");
        }
        if (boleto == null) {
            throw new IllegalArgumentException("El boleto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        this.evento = evento;
        this.boleto = boleto;
        this.cantidad = cantidad;
        this.totalPagar = boleto.getPrecio() * cantidad;
    }

    public Evento getEvento() {
        return evento;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public String getTipoBoleto() {
        return boleto.getTipo();
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    @Override
    public String generarDetalleImpresion() {
        return "TICKET DE VENTA - Evento: " + evento.getNombre()
                + " | Boletos: " + cantidad + " (" + getTipoBoleto() + ")"
                + " | Total a pagar: $" + totalPagar;
    }

    @Override
    public String toString() {
        return cantidad + " boleto(s) " + boleto.getTipo()
                + " para \"" + evento.getNombre() + "\" - Total: $" + totalPagar;
    }
}