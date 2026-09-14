package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Evento;
import service.EventoServicio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventoController {

    private static final EventoServicio eventoServicio = EventoServicio.getInstance();

    @FXML
    private TextField txtNombre;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private void crearEvento() {
        try {
            String nombre = txtNombre.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());

            // Hora esperada en formato HH:mm, por ejemplo 19:30
            LocalTime hora = LocalTime.parse(txtHora.getText());

            Evento evento = new Evento(
                    nombre,
                    dpFecha.getValue(),
                    hora,
                    capacidad
            );

            eventoServicio.crearEvento(evento);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Evento creado");
            alerta.setHeaderText(null);
            alerta.setContentText("El evento fue creado correctamente.");
            alerta.showAndWait();

            limpiarCampos();

        } catch (Exception e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo crear el evento");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        dpFecha.setValue(null);
        txtHora.clear();
        txtCapacidad.clear();
    }

    @FXML
    private AnchorPane rootCrearEvento;

    @FXML
    private void cerrarVista() {

        // Esta vista puede ser CrearEvento (rootCrearEvento inyectado)
        // o EditarEventoDatos (contenedor asignado por setContenedor).
        if (contenedor != null) {
            contenedor.getChildren().clear();
        } else if (rootCrearEvento != null
                && rootCrearEvento.getParent() instanceof Pane parent) {
            parent.getChildren().remove(rootCrearEvento);
        }
    }

    @FXML
    private TextField txtIdEditar;

    @FXML
    private TextField txtNombreEditar;

    @FXML
    private DatePicker dpFechaEditar;

    @FXML
    private TextField txtHoraEditar;

    @FXML
    private TextField txtCapacidadEditar;

    private Pane contenedor;

    private Evento eventoEnEdicion;

    @FXML
    private TableView<Evento> tablaEventos;

    @FXML
    private TableColumn<Evento, Integer> colId;

    @FXML
    private TableColumn<Evento, String> colNombre;

    @FXML
    private TableColumn<Evento, LocalDate> colFecha;

    @FXML
    private TableColumn<Evento, LocalTime> colHora;

    @FXML
    private TableColumn<Evento, Integer> colCapacidad;

    @FXML
    private TextField txtIdEliminar;

    @FXML
    private TextField txtNombreEliminar;

    @FXML
    private DatePicker dpFechaEliminar;

    @FXML
    private TextField txtHoraEliminar;

    @FXML
    private TextField txtCapacidadEliminar;

    public void setContenedor(Pane contenedor) {
        this.contenedor = contenedor;
    }

    @FXML
    private void buscarParaEditar() {
        try {

            int id = Integer.parseInt(txtIdEditar.getText());

            Evento evento = eventoServicio.buscarEvento(id);

            if (evento == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("No existe un evento con ese ID.");
                alerta.showAndWait();
                return;
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/EditarEventoDatos.fxml")
            );

            Parent vista = loader.load();
            vista.setId("editarEventoDatosView");

            EventoController controller = loader.getController();

            controller.setContenedor(contenedor);
            controller.cargarEvento(evento);

            contenedor.getChildren().setAll(vista);

        } catch (NumberFormatException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Ingrese un ID válido.");
            alerta.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEvento(Evento evento) {

        this.eventoEnEdicion = evento;

        txtNombreEditar.setText(evento.getNombre());
        dpFechaEditar.setValue(evento.getFecha());
        txtHoraEditar.setText(evento.getHora().toString());

        txtCapacidadEditar.setText(
                String.valueOf(evento.getCapacidadMaxima())
        );
    }

    @FXML
    private void cerrarVistaEliminar() {
        if (contenedor != null) {
            contenedor.getChildren().clear();
        }
    }

    @FXML
    private void buscarParaEliminar() {
        try {

            int id = Integer.parseInt(txtIdEliminar.getText());

            Evento evento = eventoServicio.buscarEvento(id);

            if (evento == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("No existe un evento con ese ID.");
                alerta.showAndWait();
                return;
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/EliminarEventoDatos.fxml")
            );

            Parent vista = loader.load();
            vista.setId("eliminarEventoDatosView");

            EventoController controller = loader.getController();

            controller.setContenedor(contenedor);
            controller.cargarEventoEliminar(evento);

            contenedor.getChildren().setAll(vista);

        } catch (NumberFormatException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Ingrese un ID válido.");
            alerta.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEventoEliminar(Evento evento) {

        this.eventoEnEdicion = evento;

        txtNombreEliminar.setText(evento.getNombre());
        dpFechaEliminar.setValue(evento.getFecha());
        txtHoraEliminar.setText(evento.getHora().toString());

        txtCapacidadEliminar.setText(
                String.valueOf(evento.getCapacidadMaxima())
        );
    }

    @FXML
    private void eliminarEvento() {
        try {

            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Confirmar eliminación");
            confirmar.setHeaderText(null);
            confirmar.setContentText(
                    "¿Seguro que desea eliminar el evento \""
                            + eventoEnEdicion.getNombre() + "\"?"
            );

            if (confirmar.showAndWait().filter(ButtonType.OK::equals).isEmpty()) {
                return;
            }

            boolean eliminado = eventoServicio.eliminarEvento(
                    eventoEnEdicion.getId()
            );

            Alert alerta;
            if (eliminado) {
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Evento eliminado");
                alerta.setHeaderText(null);
                alerta.setContentText("El evento fue eliminado correctamente.");
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("No se pudo eliminar el evento.");
            }
            alerta.showAndWait();

            if (contenedor != null) {
                contenedor.getChildren().clear();
            }

        } catch (Exception e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void volverBuscarEditar() {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/EditarEvento.fxml")
            );

            Parent vista = loader.load();
            vista.setId("editarEventoBusquedaView");

            EventoController controller = loader.getController();
            controller.setContenedor(contenedor);

            contenedor.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCambiosEditar() {
        txtNombreEditar.clear();
        dpFechaEditar.setValue(null);
        txtHoraEditar.clear();
        txtCapacidadEditar.clear();
    }

    @FXML
    private void cerrarVistaEditar() {
        contenedor.getChildren().clear();
    }

    @FXML
    private void actualizarEvento() {
        try {

            String nombre = txtNombreEditar.getText();
            LocalTime hora = LocalTime.parse(txtHoraEditar.getText());
            int capacidad = Integer.parseInt(txtCapacidadEditar.getText());

            boolean actualizado = eventoServicio.actualizarEvento(
                    eventoEnEdicion.getId(),
                    nombre,
                    dpFechaEditar.getValue(),
                    hora,
                    capacidad
            );

            if (actualizado) {

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setHeaderText(null);
                alerta.setContentText("Evento actualizado correctamente.");
                alerta.showAndWait();

            } else {

                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("No se pudo actualizar el evento.");
                alerta.showAndWait();
            }

        } catch (Exception e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void initialize() {

        if (tablaEventos != null) {

            colId.setCellValueFactory(
                    new PropertyValueFactory<>("id")
            );

            colNombre.setCellValueFactory(
                    new PropertyValueFactory<>("nombre")
            );

            colFecha.setCellValueFactory(
                    new PropertyValueFactory<>("fecha")
            );

            colHora.setCellValueFactory(
                    new PropertyValueFactory<>("hora")
            );

            colCapacidad.setCellValueFactory(
                    new PropertyValueFactory<>("capacidadMaxima")
            );

            tablaEventos.setItems(
                    FXCollections.observableArrayList(
                            eventoServicio.obtenerEventos()
                    )
            );
        }
    }

    @FXML
    private void cerrarVerEventos() {
        if (contenedor != null) {
            contenedor.getChildren().clear();
        }
    }

    public void cargarEventos() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colHora.setCellValueFactory(
                new PropertyValueFactory<>("hora")
        );

        colCapacidad.setCellValueFactory(
                new PropertyValueFactory<>("capacidadMaxima")
        );

        tablaEventos.setItems(
                FXCollections.observableArrayList(
                        eventoServicio.obtenerEventos()
                )
        );

        System.out.println(
                "Eventos encontrados: " + eventoServicio.obtenerEventos().size()
        );
    }

}