package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.SubastaApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PaginaPrincipalViewController {

    InicioViewController inicioViewController = new InicioViewController();

    SubastaApplication app = new SubastaApplication();

    @FXML
    private Button btnCrearPuja;


    @FXML
    void crearPuja(ActionEvent event) {
        if(!inicioViewController.sesionActiva()){
            cerrarVentana(btnCrearPuja);
            app.cargarVentanaInicio();
        }
    }

    @FXML
    void irAPujas(ActionEvent event) {
        if(!inicioViewController.sesionActiva()){
            cerrarVentana(btnCrearPuja);
            app.cargarVentanaInicio();
        }
    }

    public void cerrarVentana(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
