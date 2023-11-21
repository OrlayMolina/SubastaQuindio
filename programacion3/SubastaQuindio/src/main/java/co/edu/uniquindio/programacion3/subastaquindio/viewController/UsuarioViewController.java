package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.UsuarioController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.UsuarioUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.rolUsuarioLogeado;
import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.usuarioLogeado;

public class UsuarioViewController {

    UsuarioController usuarioControllerService;
    SubastaQuindio subastaQuindio;
    ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();
    ObservableList<String> listaRoles = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private Label lblContrasenia;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblUsuario;

    @FXML
    private Rectangle shape;

    @FXML
    private Rectangle shapeBotones;

    @FXML
    private TableColumn<UsuarioDto, String> colContrasenia;

    @FXML
    private TableColumn<UsuarioDto, String> colUsuario;

    @FXML
    private TableView<UsuarioDto> tableUsuarios;

    @FXML
    private PasswordField pwdContrasenia;

    @FXML
    private TextField txfUsuario;

    @FXML
    void actualizarUsuario(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void agregarUsuario(ActionEvent event) {
        crearUsuario();
    }

    @FXML
    void busquedaUsuario(ActionEvent event) {
        String usuario = txfUsuario.getText();
        String contrasenia = pwdContrasenia.getText();
        buscarUsuario(usuario, contrasenia);
    }

    @FXML
    void eliminarUsuario(ActionEvent event) {
        eliminarUsuario();
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        subastaQuindio = new SubastaQuindio();
        initView();
        cargarPestaniaSegunRol();
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuarios.getItems().clear();
        tableUsuarios.setItems(listaUsuarios);
        listenerSelection();
    }

    private void initDataBinding() {
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usuario()));
        colContrasenia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().contrasenia()));
    }

    private void listenerSelection() {
        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void crearUsuario() {

        UsuarioDto usuarioDto = construirUsuarioDto();

        if(datosValidos(usuarioDto)){
            if(usuarioControllerService.agregarUsuario(usuarioDto)){
                listaUsuarios.add(usuarioDto);
                mostrarMensaje("Notificación usuario", "Usuario creado", "El usuario se ha creado con éxito", Alert.AlertType.INFORMATION);
                registrarAcciones("Usuario creado",1, "Creación de un usuario, acción realizada por "  + usuarioLogeado);
                limpiarCamposUsuarios();

            }else{
                mostrarMensaje("Notificación usuario", "Usuario no creado", "El usuario no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarUsuario() {
        boolean usuarioEliminado = false;
        if(usuarioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al usuario?")){
                usuarioEliminado = usuarioControllerService.eliminarUsuario(usuarioSeleccionado.usuario());
                if(usuarioEliminado){
                    listaUsuarios.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tableUsuarios.getSelectionModel().clearSelection();
                    limpiarCamposUsuarios();
                    registrarAcciones("Usuario eliminado",1, "Usuario eliminado, acción realizada por " + usuarioLogeado);
                    mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito.", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Seleccionado un usuario de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarUsuario() {
        boolean usuarioActualizado = false;

        String usuario = usuarioSeleccionado.usuario();
        UsuarioDto usuarioDto = construirUsuarioDto();

        if(usuarioSeleccionado != null){

            if(datosValidos(usuarioSeleccionado)){
                usuarioActualizado = usuarioControllerService.actualizarUsuario(usuario, usuarioDto);
                if(usuarioActualizado){
                    listaUsuarios.remove(usuarioSeleccionado);
                    listaUsuarios.add(usuarioDto);
                    tableUsuarios.refresh();
                    mostrarMensaje("Notificación usuario", "Usuario actualizado", "El usuario se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuarios();
                    registrarAcciones("Usuario actualizado",1, "Usuario actualizado, acción realizada por " + usuarioLogeado);
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no actualizado", "El usuario no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Usuario no actualizado",1, "Actualizar usuario");
                }
            }else{
                mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void buscarUsuario(String usuario, String contrasenia) {

        Predicate<UsuarioDto> predicado = UsuarioUtil.buscarPorTodo(usuario, contrasenia);
        ObservableList<UsuarioDto> usuariosFiltrados = listaUsuarios.filtered(predicado);
        tableUsuarios.setItems(usuariosFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposUsuarios();
        tableUsuarios.getSelectionModel().clearSelection();
        tableUsuarios.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.addAll(usuarioControllerService.obtenerUsuarios());
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txfUsuario.setText(usuarioSeleccionado.usuario());
            pwdContrasenia.setText(usuarioSeleccionado.contrasenia());
        }
    }

    private void cargarPestaniaSegunRol(){
        if(rolUsuarioLogeado.equals(String.valueOf(Rol.Comprador)) ||
                rolUsuarioLogeado.equals(String.valueOf(Rol.Anunciante))){
            btnActualizar.setVisible(false);
            btnEliminar.setVisible(false);
            btnAgregar.setVisible(false);
            btnBuscar.setVisible(false);
            btnLimpiarCampos.setVisible(false);
            txfUsuario.setVisible(false);
            pwdContrasenia.setVisible(false);
            tableUsuarios.setVisible(false);
            lblContrasenia.setVisible(false);
            lblUsuario.setVisible(false);
            shape.setVisible(false);
            shapeBotones.setVisible(false);
            tableUsuarios.setVisible(false);

        }else{
            lblMensaje.setVisible(false);
        }
    }

    private UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(
                txfUsuario.getText(),
                pwdContrasenia.getText()
        );
    }

    private void limpiarCamposUsuarios() {
        txfUsuario.setText("");
        pwdContrasenia.setText("");
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        usuarioControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        String mensaje = "";

        if(usuarioDto.usuario() == null || usuarioDto.usuario() .equals(""))
            mensaje += "El nombre Usuario es invalido \n" ;
        if(usuarioDto.contrasenia() == null || usuarioDto.contrasenia() .equals(""))
            mensaje += "La Contraseña del usuario es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no creado", mensaje, Alert.AlertType.ERROR);
            return false;
        }
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


}
