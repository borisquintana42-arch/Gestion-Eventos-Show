package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import model.Boleto;
import model.Evento;
import service.EventoService;

import java.io.IOException;

public class MenuController {

    private final VentaController ventaController = new VentaController();
    private final ReporteController reporteController = new ReporteController();

    // Misma instancia compartida que usan las pantallas de EventoController.
    private static final EventoService EVENTO_SERVICE = EventoService.getInstance();

    @FXML
    private StackPane contenidoPane;

    @FXML
    private Accordion menuAccordion;

    @FXML
    private TitledPane paneAdministrar;

    @FXML
    private TitledPane paneVenta;

    @FXML
    private TitledPane paneResumen;

    @FXML
    private ComboBox<Evento> cmbEvento;

    @FXML
    private ComboBox<Boleto> cmbBoleto;

    @FXML
    private ComboBox<Evento> cmbEventoResumen;

    @FXML
    private void initialize() {

        ventaController.cargarTiposBoleto(cmbBoleto);

        // Carga inicial
        ventaController.cargarEventos(cmbEvento);
        reporteController.cargarEventos(cmbEventoResumen);

        // Refrescar eventos al abrir los ComboBox
        cmbEvento.setOnShowing(event ->
                ventaController.cargarEventos(cmbEvento)
        );

        cmbEventoResumen.setOnShowing(event ->
                reporteController.cargarEventos(cmbEventoResumen)
        );

        // Al abrir cualquiera de las tres secciones,
        // se limpia la pantalla que esté abierta a la derecha.

        paneAdministrar.expandedProperty().addListener(
                (observable, valorAnterior, estaAbierto) -> {
                    if (estaAbierto) {
                        contenidoPane.getChildren().clear();
                    }
                }
        );

        paneVenta.expandedProperty().addListener(
                (observable, valorAnterior, estaAbierto) -> {
                    if (estaAbierto) {
                        contenidoPane.getChildren().clear();
                    }
                }
        );

        paneResumen.expandedProperty().addListener(
                (observable, valorAnterior, estaAbierto) -> {
                    if (estaAbierto) {
                        contenidoPane.getChildren().clear();
                    }
                }
        );
    }

    /**
     * Refresca los combos de venta y resumen con los eventos actuales,
     * para que siempre reflejen los cambios hechos en otras pantallas.
     */
    private void cargarEventosEnCombos() {
        ventaController.cargarEventos(cmbEvento);
        reporteController.cargarEventos(cmbEventoResumen);
    }

    @FXML
    private void abrirCrearEvento() {
        try {

            // Si CrearEvento ya está abierto, lo cerramos
            if (!contenidoPane.getChildren().isEmpty()
                    && "crearEventoView".equals(
                    contenidoPane.getChildren().get(0).getId())) {

                contenidoPane.getChildren().clear();
                return;
            }

            // Si no está abierto, lo cargamos
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/CrearEvento.fxml")
            );

            Parent vista = loader.load();

            // Le ponemos un identificador para saber qué vista está abierta
            vista.setId("crearEventoView");

            contenidoPane.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirEditarEvento() {
        try {

            // Si ya estamos dentro de alguna pantalla de edición, la cerramos
            if (!contenidoPane.getChildren().isEmpty()) {

                String idVista = contenidoPane.getChildren().get(0).getId();

                if (idVista != null && idVista.startsWith("editarEvento")) {
                    contenidoPane.getChildren().clear();
                    return;
                }
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/EditarEvento.fxml")
            );

            Parent vista = loader.load();
            vista.setId("editarEventoBusquedaView");

            EventoController controller = loader.getController();
            controller.setContenedor(contenidoPane);

            contenidoPane.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirEliminarEvento() {
        try {

            if (!contenidoPane.getChildren().isEmpty()) {

                String idVista = contenidoPane.getChildren().get(0).getId();

                if (idVista != null && idVista.startsWith("eliminarEvento")) {
                    contenidoPane.getChildren().clear();
                    return;
                }
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/EliminarEvento.fxml")
            );

            Parent vista = loader.load();
            vista.setId("eliminarEventoBusquedaView");

            EventoController controller = loader.getController();
            controller.setContenedor(contenidoPane);

            contenidoPane.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirVerEventos() {
        try {

            if (!contenidoPane.getChildren().isEmpty()
                    && "verEventosView".equals(
                    contenidoPane.getChildren().get(0).getId())) {

                contenidoPane.getChildren().clear();
                return;
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/VerEventos.fxml")
            );

            Parent vista = loader.load();
            vista.setId("verEventosView");

            EventoController controller = loader.getController();

            controller.setContenedor(contenidoPane);

            // AQUÍ cargamos los eventos explícitamente
            controller.cargarEventos();

            contenidoPane.getChildren().setAll(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarVenta() {
        cargarEventosEnCombos();

        Evento evento = cmbEvento.getValue();
        Boleto boleto = cmbBoleto.getValue();

        if (evento == null) {
            mostrarError("Seleccione un evento.");
            return;
        }
        if (boleto == null) {
            mostrarError("Seleccione un tipo de boleto.");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/VentaDetalle.fxml")
            );

            Parent vista = loader.load();
            vista.setId("ventaDetalleView");

            VentaController controller = loader.getController();
            controller.inicializarDetalle(evento, boleto, contenidoPane);

            contenidoPane.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarResumenEvento() {
        cargarEventosEnCombos();

        Evento evento = cmbEventoResumen.getValue();

        if (evento == null) {
            mostrarError("Seleccione un evento.");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ResumenEvento.fxml")
            );

            Parent vista = loader.load();
            vista.setId("resumenEventoView");

            ReporteController controller = loader.getController();
            controller.inicializarResumen(evento, contenidoPane);

            contenidoPane.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
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