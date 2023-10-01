package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.SubastaApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioViewController {

    SubastaApplication app = new SubastaApplication();

    @FXML
    private Button btnContinuar;

    @FXML
    void siguienteVentana(ActionEvent event) {
        cerrarVentana(btnContinuar);
        app.cargarTabuladores();
    }

    public void cerrarVentana(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
