package prueba;

import java.time.LocalDate;
import java.time.LocalTime;
import servicio.EventoServicio;
import model.Evento;

public class PruebaEventos {
    public static void main(String[] args) {
        EventoServicio servicio = new EventoServicio();

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

        Evento eventoActualizado = servicio.buscarEvento(1);
        System.out.println("ID: " + eventoActualizado.getId());
        System.out.println("Nombre: " + eventoActualizado.getNombre());
        System.out.println("Fecha: " + eventoActualizado.getFecha());
        System.out.println("Hora: " + eventoActualizado.getHora());
        System.out.println("Capacidad: " + eventoActualizado.getCapacidadMaxima());



        for (Evento e : servicio.obtenerEventos()) {
            System.out.println(e);
        }

        Evento encontrado = servicio.buscarEvento(99);

        if (encontrado != null) {
            System.out.println("Evento encontrado: " + encontrado.getNombre());
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
            Evento eventoConsultado  = servicio.buscarEvento(1);

            System.out.println("Nuevo nombre: " + eventoConsultado.getNombre());
        } else {
            System.out.println("No se pudo actualizar");
        }

        System.out.println(eventoActualizado);
        System.out.println("ID: " + eventoActualizado.getId());
        System.out.println("Nombre: " + eventoActualizado.getNombre());
        System.out.println("Fecha: " + eventoActualizado.getFecha());
        System.out.println("Hora: " + eventoActualizado.getHora());
        System.out.println("Capacidad: " + eventoActualizado.getCapacidadMaxima());

        boolean eliminado = servicio.eliminarEvento(2);

        if (eliminado) {
            System.out.println("Evento eliminado correctamente");

            Evento comprobacion = servicio.buscarEvento(2);

            if (comprobacion == null) {
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
