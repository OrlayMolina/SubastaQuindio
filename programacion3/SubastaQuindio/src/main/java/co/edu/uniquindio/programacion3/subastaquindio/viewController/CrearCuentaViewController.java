package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.SubastaApplication;
import co.edu.uniquindio.programacion3.subastaquindio.controller.AnuncianteController;
import co.edu.uniquindio.programacion3.subastaquindio.controller.CompradorController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.usuarioLogeado;

public class CrearCuentaViewController {

    SubastaApplication app = new SubastaApplication();
    CompradorController compradorControllerService = new CompradorController();
    AnuncianteViewController anuncianteViewController = new AnuncianteViewController();
    AnuncianteController anuncianteControllerService = new AnuncianteController();
    ObservableList<String> listaRoles = FXCollections.observableArrayList();

    @FXML
    private TextField txfApellidos;

    @FXML
    private TextField txfCorreo;

    @FXML
    private TextField txfCedula;

    @FXML
    private TextField txfDireccion;

    @FXML
    private TextField txfFechaNacimiento;

    @FXML
    private TextField txfNombres;

    @FXML
    private TextField txfTelefono;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbRoles;

    @FXML
    void crearCuenta(ActionEvent event) {
        crearCuenta();
    }

    private void crearCuenta(){
        String rol = cmbRoles.getValue();
        if(rol.equals(String.valueOf(Rol.Anunciante))){
            AnuncianteDto anuncianteDto = construirAnuncianteDto();
            anuncianteControllerService.agregarAnunciante(anuncianteDto);
            mostrarMensaje("Notificación anunciante", "Anunciante creado", "El anunciante se ha creado con éxito", Alert.AlertType.INFORMATION);
            registrarAcciones("Anunciante creado",1, "Creación de un anunciante");
        }else {
            CompradorDto compradorDto = construirCompradorDto();
            compradorControllerService.agregarComprador(compradorDto);
            mostrarMensaje("Notificación comprador", "Comprador actualizado", "El comprador se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
            registrarAcciones("Comprador actualizado",1, "Comprador actualizado");

        }
        cerrarVentana(btnCrearCuenta);
        app.cargarTabuladores();
    }

    public AnuncianteDto construirAnuncianteDto() {
        String nombre = txfNombres.getText();
        String apellido = txfApellidos.getText();
        String cedula = txfCedula.getText();
        String telefono = txfTelefono.getText();
        String contrasenia = "123";
        String direccion = txfDireccion.getText();
        String rol = String.valueOf(Rol.Anunciante);
        String correo = txfCorreo.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbRoles.getValue());
        return new AnuncianteDto(nombre, apellido, cedula, telefono, contrasenia, direccion, rol, correo, fechaNacimiento, usuarioAsociado);
    }

    private CompradorDto construirCompradorDto() {
        String nombre = txfNombres.getText();
        String apellido = txfApellidos.getText();
        String cedula = txfCedula.getText();
        String telefono = txfTelefono.getText();
        String direccion = txfDireccion.getText();
        String contrasenia = "123";
        String rol = String.valueOf(Rol.Comprador);
        String correo = txfCorreo.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbRoles.getValue());
        return new CompradorDto(nombre, apellido, cedula, telefono, contrasenia, direccion, rol, correo, fechaNacimiento, usuarioAsociado);
    }

    @FXML
    void initialize() {
        mostrarRoles();
    }

    public void mostrarRoles(){
        listaRoles.add(String.valueOf(Rol.Anunciante));
        listaRoles.add(String.valueOf(Rol.Comprador));
        cmbRoles.setItems(listaRoles);
    }

    @FXML
    void volverInicioSesion(ActionEvent event) {
        cerrarVentana(btnVolver);
        app.cargarVentanaInicio();
    }

    public void cerrarVentana(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        anuncianteControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}
