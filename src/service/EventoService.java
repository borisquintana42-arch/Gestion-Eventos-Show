package service;

import model.Evento;
import repository.EventoRepository;
import repository.impl.EventoRepositoryImpl;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de eventos con persistencia JDBC.
 * Delegar el almacenamiento en EventoRepository en lugar de una lista en memoria.
 * Todas las operaciones lanzan Exception para que el controller decida cómo mostrar el error.
 */
public class EventoService {

    private final EventoRepository repository;

    public EventoService() {
        this.repository = new EventoRepositoryImpl();
    }

    // Constructor para pruebas o inyección de dependencias (opcional)
    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea un nuevo evento en la base de datos.
     *
     * @param evento evento que se desea registrar
     * @throws Exception si ocurre un error de persistencia o validación
     */
    public void crearEvento(Evento evento) throws Exception {
        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo.");
        }
        repository.crear(evento);
    }

    /**
     * Obtiene todos los eventos registrados en la base de datos.
     *
     * @return lista de eventos
     * @throws Exception si ocurre un error de consulta
     */
    public List<Evento> obtenerEventos() throws Exception {
        return repository.obtenerTodos();
    }

    /**
     * Busca un evento por su identificador.
     *
     * @param id identificador del evento
     * @return Optional con el evento si existe, vacío si no
     * @throws Exception si ocurre un error de consulta
     */
    public Optional<Evento> buscarEvento(int id) throws Exception {
        return repository.buscarPorId(id);
    }

    /**
     * Actualiza los datos de un evento existente.
     *
     * @param id             identificador del evento
     * @param nombre         nuevo nombre
     * @param fecha          nueva fecha
     * @param hora           nueva hora
     * @param capacidadMaxima nueva capacidad máxima
     * @return true si se actualizó, false si no se encontró
     * @throws Exception si ocurre un error de persistencia
     */
    public boolean actualizarEvento(int id, String nombre, java.time.LocalDate fecha,
                                    java.time.LocalTime hora, int capacidadMaxima) throws Exception {

        Optional<Evento> opt = repository.buscarPorId(id);
        if (opt.isEmpty()) {
            return false;
        }

        Evento e = opt.get();
        e.setNombre(nombre);
        e.setFecha(fecha);
        e.setHora(hora);
        e.setCapacidadMaxima(capacidadMaxima);

        return repository.actualizar(e);
    }

    /**
     * Elimina un evento por su identificador.
     *
     * @param id identificador del evento
     * @return true si se eliminó, false si no existía
     * @throws Exception si ocurre un error de persistencia
     */
    public boolean eliminarEvento(int id) throws Exception {
        return repository.eliminar(id);
    }

    /**
     * Retorna la cantidad de entradas vendidas para un evento (desde la BD).
     *
     * @param eventoId identificador del evento
     * @return cantidad de entradas vendidas
     * @throws Exception si ocurre un error de consulta
     */
    public int entradasVendidas(int eventoId) throws Exception {
        return repository.contarEntradasVendidas(eventoId);
    }
}
