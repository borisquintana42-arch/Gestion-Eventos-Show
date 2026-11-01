package repository;

import model.Venta;
import java.util.List;

/**
 * Contrato de persistencia para Venta.
 * El resto de la app depende de esta interfaz, no de SQL directamente.
 * Patrón Repository / DAO.
 */
public interface VentaRepository {

    void registrar(Venta venta) throws Exception;

    List<Venta> obtenerPorEvento(int eventoId) throws Exception;

    List<Venta> obtenerHistorial() throws Exception;

    int contarPorTipoYEvento(int eventoId, String tipoBoleto) throws Exception;
}
