package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HistorialViewController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private ComboBox<?> cmbComprador;

    @FXML
    private ComboBox<?> cmbEstado;

    @FXML
    private ComboBox<?> cmbProducto;

    @FXML
    private TableColumn<?, ?> colCodigoAnuncio;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colProducto;

    @FXML
    private TableColumn<?, ?> colPuja;

    @FXML
    private TableColumn<?, ?> colValorOferta;

    @FXML
    private TableView<?> tableHistorial;

    @FXML
    private TextField txfCodigoAnuncio;

    @FXML
    private TextField txfCodigoPuja;

    @FXML
    private TextField txfValorOferta;

    @FXML
    void busquedaComprador(ActionEvent event) {

    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {

    }

}
