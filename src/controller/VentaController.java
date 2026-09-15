package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Pane;
import model.Boleto;
import model.BoletoGeneral;
import model.BoletoMeetGreet;
import model.BoletoVIP;
import model.Evento;
import model.Venta;
import service.EventoServicio;
import service.VentaService;

/**
 * Controlador de la venta de boletos.
 * La pantalla de detalle (VentaDetalle.fxml) usa este controlador,
 * y MenuController delega aquí la carga de los ComboBox del menú.
 */
public class VentaController {

    // Servicio compartido con ReporteController para que el resumen
    // vea exactamente las mismas ventas realizadas.
    private static final VentaService ventaService = new VentaService();

    // Misma instancia que usan las pantallas de eventos.
    private static final EventoServicio eventoServicio = EventoServicio.getInstance();

    // Estado de la compra en curso (VentaDetalle.fxml).
    private Evento eventoSeleccionado;
    private Boleto boletoSeleccionado;
    private Pane contenedor;

    @FXML
    private Label LblBeneficios;

    @FXML
    private Label LblPrecio;

    @FXML
    private Label LblAforo;

    @FXML
    private Label LblTotal;

    @FXML
    private TextField txtCantidad;

    /**
     * Devuelve la instancia única de VentaService de la aplicación,
     * para que el reporte use exactamente las mismas ventas.
     */
    public static VentaService getVentaService() {
        return ventaService;
    }

    /**
     * Carga los tipos de boleto disponibles en el ComboBox indicado.
     */
    public void cargarTiposBoleto(ComboBox<Boleto> cmbBoleto) {
        cmbBoleto.getItems().setAll(
                new BoletoGeneral(),
                new BoletoVIP(),
                new BoletoMeetGreet()
        );
    }

    /**
     * Carga los eventos actuales en el ComboBox indicado, siempre desde
     * la misma lista compartida de eventos.
     */
    public void cargarEventos(ComboBox<Evento> cmbEvento) {
        cmbEvento.getItems().setAll(eventoServicio.obtenerEventos());
    }

    /**
     * Recibe el evento y el boleto seleccionados en el menú, junto con el
     * panel contenedor, y rellena el detalle de la compra (VentaDetalle.fxml):
     * beneficios, precio y aforo disponible. Además, actualiza el total
     * en vivo mientras el usuario escribe la cantidad.
     */
    public void inicializarDetalle(Evento evento, Boleto boleto, Pane contenedor) {

        this.eventoSeleccionado = evento;
        this.boletoSeleccionado = boleto;
        this.contenedor = contenedor;

        LblBeneficios.setWrapText(true);
        LblBeneficios.setTextOverrun(OverrunStyle.CLIP);
        LblBeneficios.setPrefWidth(430);
        LblBeneficios.setPrefHeight(100);

        LblBeneficios.setText(
                "• " + String.join("\n• ", boleto.getBeneficios())
        );
        LblPrecio.setText("$ " + boleto.getPrecio());

        int aforoDisponible = evento.getCapacidadMaxima()
                - ventaService.calcularEntradasVendidas(evento);
        LblAforo.setText(String.valueOf(aforoDisponible));

        LblTotal.setText("$ 0.0");

        // total = precio x cantidad, en vivo
        txtCantidad.textProperty().addListener((obs, antes, ahora) -> {
            try {
                int cantidad = Integer.parseInt(ahora.trim());
                LblTotal.setText(
                        "$ " + String.format("%.2f", cantidad * boleto.getPrecio())
                );
            } catch (NumberFormatException e) {
                LblTotal.setText("$ 0.0");
            }
        });
    }

    @FXML
    private void registrarCompra() {

        try {

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            // VentaService valida cantidad mayor a cero y comprueba el aforo.
            Venta venta = ventaService.registrarCompra(
                    eventoSeleccionado,
                    boletoSeleccionado,
                    cantidad
            );

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Compra exitosa");
            exito.setHeaderText(null);
            exito.setContentText(venta.generarDetalleImpresion());
            exito.showAndWait();

            cerrarCompra();

        } catch (NumberFormatException e) {

            mostrarError("Ingrese una cantidad válida (número entero).");

        } catch (IllegalArgumentException e) {

            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void cancelarCompra() {
        cerrarCompra();
    }

    @FXML
    private void cerrarVenta() {
        cerrarCompra();
    }

    private void cerrarCompra() {
        if (contenedor != null) {
            contenedor.getChildren().clear();
        }
    }

    private void mostrarError(String mensaje) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
}