package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import model.Evento;
import repository.impl.VentaRepositoryImpl;
import service.EventoService;
import service.ReporteService;

/**
 * Controlador del resumen de eventos.
 * La pantalla ResumenEvento.fxml usa este controlador y rellena sus Labels
 * con los datos que ReporteService calcula a partir del VentaService
 * compartido con la venta de boletos.
 */
public class ReporteController {

    // Mismo repositorio de ventas que usa VentaController (instancia única compartida).
    private final ReporteService reporteService =
            new ReporteService(VentaController.getVentaRepository());

    private final EventoService EVENTO_SERVICE = new EventoService();

    private Pane contenedor;

    @FXML
    private Label LblNombreEvento;

    @FXML
    private Label LblFechaEvento;

    @FXML
    private Label LblCapacidad;

    @FXML
    private Label LblGeneralVendido;

    @FXML
    private Label LblVipVendidos;

    @FXML
    private Label LblMeetGreetVendidos;

    @FXML
    private Label LblTotalVendidos;

    @FXML
    private Label LblDisponible;

    @FXML
    private Label LblRecaudacion;

    /**
     * Carga los eventos actuales en el ComboBox del resumen.
     */
    public void cargarEventos(ComboBox<Evento> cmbEventoResumen) {
        try {
            cmbEventoResumen.getItems().setAll(EVENTO_SERVICE.obtenerEventos());
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error al cargar eventos: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    /**
     * Recibe el evento seleccionado junto con el panel contenedor y rellena
     * los Labels del resumen con las ventas reales registradas.
     */
    public void inicializarResumen(Evento evento, Pane contenedor) {

        this.contenedor = contenedor;

        try {
            LblNombreEvento.setText(evento.getNombre());
            LblFechaEvento.setText(String.valueOf(evento.getFecha()));
            LblCapacidad.setText(String.valueOf(evento.getCapacidadMaxima()));

            LblGeneralVendido.setText(
                    String.valueOf(reporteService.contarBoletosGeneral(evento))
            );
            LblVipVendidos.setText(
                    String.valueOf(reporteService.contarBoletosVIP(evento))
            );
            LblMeetGreetVendidos.setText(
                    String.valueOf(reporteService.contarBoletosMeetGreet(evento))
            );
            LblTotalVendidos.setText(
                    String.valueOf(reporteService.calcularTotalVendidos(evento))
            );
            LblDisponible.setText(
                    String.valueOf(reporteService.calcularEntradasDisponibles(evento))
            );
            LblRecaudacion.setText(
                    "$ " + reporteService.calcularRecaudacionTotal(evento)
            );
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error al cargar resumen: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void cerrarResumen() {
        if (contenedor != null) {
            contenedor.getChildren().clear();
        }
    }
}