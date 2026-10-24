package repository;

import model.Evento;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de persistencia para Evento.
 * El resto de la app depende de esta interfaz, no de SQL directamente.
 * Patrón Repository / DAO.
 */
public interface EventoRepository {

    void crear(Evento evento) throws Exception;

    List<Evento> obtenerTodos() throws Exception;

    Optional<Evento> buscarPorId(int id) throws Exception;

    boolean actualizar(Evento evento) throws Exception;

    boolean eliminar(int id) throws Exception;

    int contarEntradasVendidas(int eventoId) throws Exception;
}
