package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class ProductoViewController {

    @FXML
    private Rectangle ImageView;

    @FXML
    private ComboBox<?> cmbTipoProducto;

    @FXML
    private DatePicker dateFechaFinPublicacion;

    @FXML
    private DatePicker dateFechaPublicación;

    @FXML
    private TextArea txaDescripción;

    @FXML
    private TextField txfAnunciante;

    @FXML
    private TextField txfNombreProducto;

    @FXML
    private TextField txfValorInicial;

    @FXML
    void actualizarProducto(ActionEvent event) {

    }

    @FXML
    void agregarProducto(ActionEvent event) {

    }

    @FXML
    void eliminarProducto(ActionEvent event) {

    }

}
