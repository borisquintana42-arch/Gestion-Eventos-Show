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
import javafx.concurrent.Task;
import java.util.List;

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

        // Ejecuta la consulta a MySQL en segundo plano
        Task<List<Venta>> tarea = new Task<>() {

            @Override
            protected List<Venta> call() throws Exception {
                return VentaController.getVentaService()
                        .obtenerHistorialVentas();
            }
        };

        // Cuando termina, actualiza la tabla
        tarea.setOnSucceeded(evento -> {
            tablaVentas.setItems(
                    FXCollections.observableArrayList(tarea.getValue())
            );
        });

        // Si ocurre un error, lo muestra
        tarea.setOnFailed(evento -> {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Error al cargar ventas: "
                            + tarea.getException().getMessage()
            );
            alerta.showAndWait();
        });

        // Inicia el Task en otro hilo
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
    }

}