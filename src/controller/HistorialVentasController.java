package controller;

import javafx.scene.control.Alert;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import model.Venta;
import javafx.collections.FXCollections;

public class HistorialVentasController {

    private Pane contenedor;

    @FXML
    private TableView<Venta> tablaVentas;

    @FXML
    private TableColumn<Venta, String> colEvento;

    @FXML
    private TableColumn<Venta, String> colBoleto;

    @FXML
    private TableColumn<Venta, Integer> colCantidad;

    @FXML
    private TableColumn<Venta, Double> colTotal;

    @FXML
    private void initialize() {

        colEvento.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getEvento().getNombre()
                )
        );

        colBoleto.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getTipoBoleto()
                )
        );

        colCantidad.setCellValueFactory(datos ->
                new ReadOnlyObjectWrapper<>(
                        datos.getValue().getCantidad()
                )
        );

        colTotal.setCellValueFactory(datos ->
                new ReadOnlyObjectWrapper<>(
                        datos.getValue().getTotalPagar()
                )
        );
        actualizarHistorial();
    }

    public void setContenedor(Pane contenedor) {
        this.contenedor = contenedor;
    }

    @FXML
    private void cerrarHistorial() {
        if (contenedor != null) {
            contenedor.getChildren().clear();
        }
    }

    @FXML
    private void actualizarHistorial() {
        try {
            // Obtiene las ventas desde el servicio de César
            tablaVentas.setItems(
                    FXCollections.observableArrayList(
                            VentaController.getVentaService().obtenerHistorialVentas()
                    )
            );

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error al cargar ventas: " + e.getMessage());
            alerta.showAndWait();
        }
    }

}