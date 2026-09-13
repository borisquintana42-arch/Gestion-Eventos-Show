package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MenuController {

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
        System.out.println("Eliminar evento");
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
    private StackPane contenidoPane;
}