package service;

import model.Boleto;
import model.Evento;
import model.Venta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio encargado de la lógica de negocio de la venta de boletos.
 * Lleva el historial de todas las ventas y controla el aforo de cada evento,
 * ya que la clase {@link Evento} solo conoce su capacidad máxima.
 */
public class VentaService {

    private final List<Venta> ventasRegistradas = new ArrayList<>();

    /**
     * Verifica si la cantidad solicitada, sumada a las entradas ya vendidas
     * para el evento, no supera su capacidad máxima.
     */
    public boolean verificarAforo(Evento evento, int cantidadSolicitada) {
        validarEntrada(evento, cantidadSolicitada);

        int entradasVendidas = calcularEntradasVendidas(evento);
        return entradasVendidas + cantidadSolicitada <= evento.getCapacidadMaxima();
    }

    /**
     * Registra la compra de boletos para un evento, previa validación de aforo.
     *
     * @return la {@link Venta} creada y agregada al historial.
     * @throws IllegalArgumentException si el aforo del evento no es suficiente.
     */
    public Venta registrarCompra(Evento evento, Boleto boleto, int cantidad) {
        validarEntrada(evento, cantidad);

        if (boleto == null) {
            throw new IllegalArgumentException("El boleto no puede ser nulo.");
        }

        int entradasVendidas = calcularEntradasVendidas(evento);
        int aforoDisponible = evento.getCapacidadMaxima() - entradasVendidas;

        if (!verificarAforo(evento, cantidad)) {
            throw new IllegalArgumentException(
                    "No hay aforo suficiente para el evento \"" + evento.getNombre()
                            + "\". Aforo disponible: " + aforoDisponible
                            + " de " + evento.getCapacidadMaxima()
                            + " entradas. Se solicitaron " + cantidad + "."
            );
        }

        Venta venta = new Venta(evento, boleto, cantidad);
        ventasRegistradas.add(venta);
        return venta;
    }

    /**
     * Suma las cantidades de boletos vendidos para un evento en particular,
     * recorriendo todo el historial de ventas.
     */
    public int calcularEntradasVendidas(Evento evento) {
        return ventasRegistradas.stream()
                .filter(venta -> venta.getEvento() == evento)
                .mapToInt(Venta::getCantidad)
                .sum();
    }

    /**
     * Retorna el historial de ventas de forma de solo lectura.
     */
    public List<Venta> getVentasRegistradas() {
        return Collections.unmodifiableList(ventasRegistradas);
    }

    private void validarEntrada(Evento evento, int cantidadSolicitada) {
        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo.");
        }
        if (cantidadSolicitada <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
    }
}