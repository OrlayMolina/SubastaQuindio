package co.edu.uniquindio.programacion3.subastaquindio.viewController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class TabuladorViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PujaView.fxml"));


        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                Node content = newTab.getContent();

                // Verificar si el contenido del tab es de tipo PujaViewController
                if (content != null && content.getUserData() instanceof FXMLLoader) {
                    PujaViewController pujaController = ((PujaViewController) ((FXMLLoader) content.getUserData()).getController());
                    pujaController.cancelarBusqueda();
                }
            }
        });
    }

}
