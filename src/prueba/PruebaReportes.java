package prueba;

import model.Boleto;
import model.BoletoGeneral;
import model.BoletoMeetGreet;
import model.BoletoVIP;
import model.Evento;
import service.ReporteService;
import service.VentaService;

import java.time.LocalDate;
import java.time.LocalTime;

public class PruebaReportes {

    public static void main(String[] args) {

        // Servicio que registra las ventas
        VentaService ventaService = new VentaService();

        // El reporte usa exactamente el mismo servicio de ventas
        ReporteService reporteService = new ReporteService(ventaService);

        // Evento de prueba
        Evento evento = new Evento(
                "Concierto de Prueba",
                LocalDate.of(2026, 10, 20),
                LocalTime.of(19, 0),
                20
        );

        // Tipos de boletos
        Boleto general = new BoletoGeneral();
        Boleto vip = new BoletoVIP();
        Boleto meetGreet = new BoletoMeetGreet();

        // Registrar ventas
        ventaService.registrarCompra(evento, general, 4);
        ventaService.registrarCompra(evento, vip, 2);
        ventaService.registrarCompra(evento, meetGreet, 1);

        // Mostrar reporte
        System.out.println("===== RESUMEN DEL EVENTO =====");
        System.out.println("Evento: " + evento.getNombre());
        System.out.println("Capacidad: " + evento.getCapacidadMaxima());

        System.out.println("\n--- Ventas por localidad ---");
        System.out.println(
                "General: " + reporteService.contarBoletosGeneral(evento)
        );
        System.out.println(
                "VIP: " + reporteService.contarBoletosVIP(evento)
        );
        System.out.println(
                "Meet & Greet: " + reporteService.contarBoletosMeetGreet(evento)
        );

        System.out.println("\n--- Totales ---");
        System.out.println(
                "Total vendidos: " + reporteService.calcularTotalVendidos(evento)
        );
        System.out.println(
                "Entradas disponibles: " + reporteService.calcularEntradasDisponibles(evento)
        );
        System.out.println(
                "Recaudacion total: $" + reporteService.calcularRecaudacionTotal(evento)
        );
    }
}
