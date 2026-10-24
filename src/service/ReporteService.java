package service;

import model.Evento;
import model.Venta;
import repository.VentaRepository;
import repository.impl.VentaRepositoryImpl;

import java.util.List;

/**
 * ReporteService adaptado a JDBC. Consulta el repositorio de ventas en MySQL.
 */
public class ReporteService {

    private final VentaRepository repository;

    public ReporteService() {
        this.repository = new VentaRepositoryImpl();
    }

    public ReporteService(VentaRepository repository) {
        this.repository = repository;
    }

    /**
     * Cuenta boletos GENERAL vendidos para un evento.
     */
    public int contarBoletosGeneral(Evento evento) throws Exception {
        return repository.contarPorTipoYEvento(evento.getId(), "GENERAL");
    }

    /**
     * Cuenta boletos VIP vendidos para un evento.
     */
    public int contarBoletosVIP(Evento evento) throws Exception {
        return repository.contarPorTipoYEvento(evento.getId(), "VIP");
    }

    /**
     * Cuenta boletos MEET & GREET vendidos para un evento.
     */
    public int contarBoletosMeetGreet(Evento evento) throws Exception {
        return repository.contarPorTipoYEvento(evento.getId(), "MEET & GREET");
    }

    /**
     * Calcula el total de entradas vendidas para un evento.
     */
    public int calcularTotalVendidos(Evento evento) throws Exception {
        int general = contarBoletosGeneral(evento);
        int vip = contarBoletosVIP(evento);
        int meet = contarBoletosMeetGreet(evento);
        return general + vip + meet;
    }

    /**
     * Calcula entradas disponibles (capacidad menos vendidos).
     */
    public int calcularEntradasDisponibles(Evento evento) throws Exception {
        return evento.getCapacidadMaxima() - calcularTotalVendidos(evento);
    }

    /**
     * Calcula la recaudación total de un evento.
     */
    public double calcularRecaudacionTotal(Evento evento) throws Exception {
        List<Venta> ventas = repository.obtenerPorEvento(evento.getId());
        return ventas.stream().mapToDouble(Venta::getTotalPagar).sum();
    }
}
