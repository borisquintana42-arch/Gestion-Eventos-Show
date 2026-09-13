package service;

import model.Evento;
import model.Venta;

public class ReporteService {

    private final VentaService ventaService;

    public ReporteService(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public int contarBoletosGeneral(Evento evento) {
        int total = 0;

        for (Venta venta : ventaService.getVentasRegistradas()) {

            if (venta.getEvento().getId() == evento.getId()
                    && venta.getTipoBoleto().equals("GENERAL")) {

                total += venta.getCantidad();
            }
        }

        return total;
    }

    public int contarBoletosVIP(Evento evento) {
        int total = 0;

        for (Venta venta : ventaService.getVentasRegistradas()) {

            if (venta.getEvento().getId() == evento.getId()
                    && venta.getTipoBoleto().equals("VIP")) {

                total += venta.getCantidad();
            }
        }

        return total;
    }

    public int contarBoletosMeetGreet(Evento evento) {
        int total = 0;

        for (Venta venta : ventaService.getVentasRegistradas()) {

            if (venta.getEvento().getId() == evento.getId()
                    && venta.getTipoBoleto().equals("MEET & GREET")) {

                total += venta.getCantidad();
            }
        }

        return total;
    }

    public int calcularTotalVendidos(Evento evento) {
        int total = 0;

        for (Venta venta : ventaService.getVentasRegistradas()) {

            if (venta.getEvento().getId() == evento.getId()) {
                total += venta.getCantidad();
            }
        }

        return total;
    }

    public int calcularEntradasDisponibles(Evento evento) {
        return evento.getCapacidadMaxima() - calcularTotalVendidos(evento);
    }

    public double calcularRecaudacionTotal(Evento evento) {
        double total = 0;

        for (Venta venta : ventaService.getVentasRegistradas()) {

            if (venta.getEvento().getId() == evento.getId()) {
                total += venta.getTotalPagar();
            }
        }

        return total;
    }
}