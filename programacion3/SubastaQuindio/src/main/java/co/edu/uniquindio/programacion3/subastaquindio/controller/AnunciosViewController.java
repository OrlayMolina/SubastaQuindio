package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.SubastaApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AnunciosViewController {

    SubastaApplication app = new SubastaApplication();

    @FXML
    private Button btnCrearPuja;

    @FXML
    void crearPuja(ActionEvent event) {
        cerrarVentana(btnCrearPuja);
        app.cargarVentanaInicio();
    }

    public void cerrarVentana(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
