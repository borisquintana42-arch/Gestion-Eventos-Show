package prueba;

import java.time.LocalDate;
import java.time.LocalTime;
import service.EventoService;
import model.Evento;

import java.util.Optional;

public class PruebaEventos {
    public static void main(String[] args) throws Exception {
        EventoService servicio = new EventoService();

        Evento evento = new Evento(
                "Evento Boris",
                LocalDate.of(2008, 8, 28),
                LocalTime.of(8, 30),
                500
        );

        Evento evento2 = new Evento(
                "Evento Erick",
                LocalDate.of(2015, 3, 27),
                LocalTime.of(3, 00),
                500
        );

        servicio.crearEvento(evento);
        servicio.crearEvento(evento2);

        Optional<Evento> optActualizado = servicio.buscarEvento(1);
        if (optActualizado.isPresent()) {
            Evento eventoActualizado = optActualizado.get();
            System.out.println("ID: " + eventoActualizado.getId());
            System.out.println("Nombre: " + eventoActualizado.getNombre());
            System.out.println("Fecha: " + eventoActualizado.getFecha());
            System.out.println("Hora: " + eventoActualizado.getHora());
            System.out.println("Capacidad: " + eventoActualizado.getCapacidadMaxima());
        } else {
            System.out.println("No se encontró el evento con id 1");
        }

        for (Evento e : servicio.obtenerEventos()) {
            System.out.println(e);
        }

        Optional<Evento> optEncontrado = servicio.buscarEvento(99);

        if (optEncontrado.isPresent()) {
            System.out.println("Evento encontrado: " + optEncontrado.get().getNombre());
        } else {
            System.out.println("Evento no encontrado");
        }

        boolean actualizado = servicio.actualizarEvento(
                1,
                "No es Boris",
                LocalDate.of(2008, 4, 20),
                LocalTime.of(6, 48),
                499
        );

        if (actualizado) {
            Optional<Evento> optConsultado = servicio.buscarEvento(1);
            if (optConsultado.isPresent()) {
                System.out.println("Nuevo nombre: " + optConsultado.get().getNombre());
            }
        } else {
            System.out.println("No se pudo actualizar");
        }

        boolean eliminado = servicio.eliminarEvento(2);

        if (eliminado) {
            System.out.println("Evento eliminado correctamente");

            Optional<Evento> optComprobacion = servicio.buscarEvento(2);
            if (optComprobacion.isEmpty()) {
                System.out.println("Confirmado: el evento ya no existe");
            }

        } else {
            System.out.println("No se pudo eliminar el evento");
        }

        for (Evento e : servicio.obtenerEventos()) {
            System.out.println(e);
        }
    }
}
