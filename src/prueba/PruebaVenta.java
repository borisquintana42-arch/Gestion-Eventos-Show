package prueba;

import model.*;
import service.VentaService;

import java.time.LocalDate;
import java.time.LocalTime;

public class PruebaVenta {
    public static void main(String[] args) {
        // 1. Instanciamos el servicio de ventas
        VentaService ventaService = new VentaService();

        // 2. Creamos un evento de prueba (capacidad pequeña de 5 personas para probar aforo)
        Evento concierto = new Evento("Concierto de Prueba", LocalDate.now(), LocalTime.of(20, 0), 5);

        System.out.println("--- INICIANDO PRUEBAS DE VENTA ---");
        System.out.println("Evento: " + concierto.getNombre());
        System.out.println("Capacidad Máxima: " + concierto.getCapacidadMaxima());

        // 3. Crear tipos de boletos
        Boleto general = new BoletoGeneral();
        Boleto vip = new BoletoVIP();

        try {
            // Pruba A: Venta exitosa de 3 boletos generales
            System.out.println("\n1. Intentando vender 3 boletos Generales...");
            Venta v1 = ventaService.registrarCompra(concierto, general, 3);
            System.out.println("✅ Venta realizada con éxito. Total pagar: $" + v1.getTotalPagar());

            // Prueba B: Venta exitosa de 1 boleto VIP (Llevamos 4/5)
            System.out.println("\n2. Intentando vender 1 boleto VIP...");
            Venta v2 = ventaService.registrarCompra(concierto, vip, 1);
            System.out.println("✅ Venta realizada con éxito. Total pagar: $" + v2.getTotalPagar());

            // Prueba C: Intento de sobrepasar el aforo (Pedimos 2 boletos más, pero solo queda 1 espacio)
            System.out.println("\n3. Intentando vender 2 boletos más (debería fallar por aforo)...");
            Venta v3 = ventaService.registrarCompra(concierto, general, 2);
            System.out.println("✅ Venta realizada: " + v3);

        } catch (IllegalArgumentException e) {
            System.out.println("❌ ERROR ESPERADO (Control de aforo funciona): " + e.getMessage());
        }

        // 4. Verificar resumen
        System.out.println("\n--- RESUMEN FINAL ---");
        System.out.println("Entradas vendidas totales: " + ventaService.calcularEntradasVendidas(concierto));
        System.out.println("Aforo restante: " + (concierto.getCapacidadMaxima() - ventaService.calcularEntradasVendidas(concierto)));
    }
}