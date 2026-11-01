package service;

import model.Boleto;
import model.Evento;
import model.Venta;
import repository.VentaRepository;
import repository.impl.VentaRepositoryImpl;

import java.util.List;

/**
 * Servicio de ventas con persistencia JDBC y control de aforo.
 * Delegar el almacenamiento en VentaRepository en lugar de una lista en memoria.
 */
public class VentaService {

    private final VentaRepository repository;
    private final EventoService eventoService;

    public VentaService() {
        this.repository = new VentaRepositoryImpl();
        this.eventoService = new EventoService();
    }

    public VentaService(VentaRepository repository, EventoService eventoService) {
        this.repository = repository;
        this.eventoService = eventoService;
    }

    /**
     * Registra una compra de boletos previo control de aforo.
     *
     * @param evento  evento al que se venden boletos
     * @param boleto  tipo de boleto
     * @param cantidad cantidad de boletos
     * @return la venta registrada
     * @throws Exception si faltan datos, no hay aforo o error de BD
     */
    /**
     * Retorna la cantidad de entradas vendidas para un evento (desde la BD).
     */
    public int entradasVendidas(int eventoId) throws Exception {
        return eventoService.entradasVendidas(eventoId);
    }

    public Venta registrarCompra(Evento evento, Boleto boleto, int cantidad) throws Exception {
        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo.");
        }
        if (boleto == null) {
            throw new IllegalArgumentException("El boleto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        // Control de aforo usando datos persistidos en MySQL
        int vendidas = eventoService.entradasVendidas(evento.getId());
        int disponible = evento.getCapacidadMaxima() - vendidas;
        if (cantidad > disponible) {
            throw new IllegalArgumentException(
                    "No hay aforo suficiente. Disponible: " + disponible
                            + ", solicitado: " + cantidad
            );
        }

        Venta venta = new Venta(evento, boleto, cantidad);
        repository.registrar(venta);
        return venta;
    }

    /**
     * Obtiene todas las ventas de un evento específico.
     *
     * @param evento evento consultado
     * @return lista de ventas
     * @throws Exception si ocurre un error de consulta
     */
    public List<Venta> obtenerVentasPorEvento(Evento evento) throws Exception {
        return repository.obtenerPorEvento(evento.getId());
    }

    /**
     * Obtiene el historial completo de ventas.
     *
     * @return lista de todas las ventas
     * @throws Exception si ocurre un error de consulta
     */
    public List<Venta> obtenerHistorialVentas() throws Exception {
        return repository.obtenerHistorial();
    }
}
