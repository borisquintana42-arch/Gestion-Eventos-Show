package service;

import model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventoService {

    private static final EventoService INSTANCIA = new EventoService();

    private final ArrayList<Evento> eventos;

    /**
     * Devuelve la instancia compartida de EventoServicio usada por todas las
     * pantallas de la aplicación, para que todas trabajen sobre la misma lista
     * de eventos durante una ejecución.
     *
     * @return la instancia única de EventoServicio
     */
    public static EventoService getInstance() {
        return INSTANCIA;
    }

    /**
     * Inicializa el servicio de eventos con una lista vacia.
     */
    public EventoService() {
        eventos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo evento a la lista de eventos.
     *
     * @param evento evento que se desea registrar
     */

    public void crearEvento(Evento evento) {

        if (evento == null) {
            throw new IllegalArgumentException("Favor rellene el espacio con su evento");
        }

        eventos.add(evento);
    }

    /**
     * Obtiene todos los eventos registrados.
     *
     * @return una copia de la lista de eventos
     */

    public ArrayList<Evento> obtenerEventos() {
        return new ArrayList<>(eventos);
    }

    /**
     * Busca un evento utilizando su identificador.
     *
     * @param id identificador del evento que se desea buscar
     * @return el evento encontrado o null si no existe
     */

    public Evento buscarEvento(int id) {
        for (Evento evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un evento existente.
     *
     * @param id identificador del evento
     * @param nombre nuevo nombre del evento
     * @param fecha nueva fecha del evento
     * @param hora nueva hora del evento
     * @param capacidadMaxima nueva capacidad maxima
     * @return true si el evento fue actualizado, false si no fue encontrado
     */

    public boolean actualizarEvento(int id, String nombre, LocalDate fecha,
                                    LocalTime hora, int capacidadMaxima) {

        Evento evento = buscarEvento(id);

        if (evento != null) {

            evento.setNombre(nombre);
            evento.setFecha(fecha);
            evento.setHora(hora);
            evento.setCapacidadMaxima(capacidadMaxima);

            return true;
        }

        return false;
    }

    /**
     * Elimina un evento utilizando su identificador.
     *
     * @param id identificador del evento que se desea eliminar
     * @return true si el evento fue eliminado, false si no fue encontrado
     */

    public boolean eliminarEvento(int id) {

        Evento evento = buscarEvento(id);

        if (evento != null) {

            eventos.remove(evento);

            return true;
        }

        return false;
    }
}