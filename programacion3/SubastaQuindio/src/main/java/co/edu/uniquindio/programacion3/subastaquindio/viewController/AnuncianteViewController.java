package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.AnuncianteController;
import co.edu.uniquindio.programacion3.subastaquindio.controller.UsuarioController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.AnuncianteUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

public class AnuncianteViewController {

    AnuncianteController anuncianteControllerService;
    SubastaQuindio subastaQuindio;
    ObservableList<AnuncianteDto> listaAnunciantes = FXCollections.observableArrayList();
    ObservableList<String> listaRoles = FXCollections.observableArrayList();
    AnuncianteDto anuncianteSeleccionado;

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
    private ComboBox<String> cmbUsuario;

    @FXML
    private TableColumn<AnuncianteDto, String> colApellidos;

    @FXML
    private TableColumn<AnuncianteDto, String> colCedula;

    @FXML
    private TableColumn<AnuncianteDto, String> colCorreo;

    @FXML
    private TableColumn<AnuncianteDto, String> colDireccion;

    @FXML
    private TableColumn<AnuncianteDto, String> colFechaNacimiento;

    @FXML
    private TableColumn<AnuncianteDto, String> colNombres;

    @FXML
    private TableColumn<AnuncianteDto, String> colTelefono;

    @FXML
    private TableColumn<AnuncianteDto, String> colUsuarioAsociado;

    @FXML
    private TextField txfFechaNacimiento;

    @FXML
    private TableView<AnuncianteDto> tableAnunciantes;

    @FXML
    private TextField txfApellidoAnunciante;

    @FXML
    private TextField txfCedula;

    @FXML
    private TextField txfCorreo;

    @FXML
    private TextField txfDireccion;

    @FXML
    private TextField txfNombreAnunciante;

    @FXML
    private TextField txfTelefono;

    @FXML
    void actualizarAnunciante(ActionEvent event) {

    }

    @FXML
    void agregarAnunciante(ActionEvent event) {

    }

    @FXML
    void busquedaAnunciante(ActionEvent event) {

    }

    @FXML
    void eliminarAnunciante(ActionEvent event) {

    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {

    }

    @FXML
    void initialize() {
        anuncianteControllerService = new AnuncianteController();
        subastaQuindio = new SubastaQuindio();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerAunciantes();
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
            if(anuncianteControllerService.agregarUsuario(usuarioDto)){
                listaUsuarios.add(usuarioDto);
                mostrarMensaje("Notificación usuario", "Usuario creado", "El usuario se ha creado con éxito", Alert.AlertType.INFORMATION);
                registrarAcciones("Usuario creado",1, "Creación de un usuario, acción realizada por " );
                limpiarCamposUsuarios();

            }else{
                mostrarMensaje("Notificación usuario", "Usuario no creado", "El usuario no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarUsuario() {
        boolean estudianteEliminado = false;
        if(usuarioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al usuario?")){
                estudianteEliminado = anuncianteControllerService.eliminarUsuario(usuarioSeleccionado.usuario());
                if(estudianteEliminado){
                    listaUsuarios.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tableUsuarios.getSelectionModel().clearSelection();
                    limpiarCamposUsuarios();
                    registrarAcciones("Usuario eliminado",1, "Usuario eliminado, acción realizada por ");
                    mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito.", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación usuario", "Estudiante no seleccionado", "Seleccionado un usuario de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarUsuario() {
        boolean estudianteActualizado = false;

        String usuario = usuarioSeleccionado.usuario();
        UsuarioDto usuarioDto = construirUsuarioDto();

        if(usuarioSeleccionado != null){

            if(datosValidos(usuarioSeleccionado)){
                estudianteActualizado = anuncianteControllerService.actualizarUsuario(usuario, usuarioDto);
                if(estudianteActualizado){
                    listaUsuarios.remove(usuarioSeleccionado);
                    listaUsuarios.add(usuarioDto);
                    tableUsuarios.refresh();
                    mostrarMensaje("Notificación usuario", "Usuario actualizado", "El usuario se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuarios();
                    registrarAcciones("Usuario actualizado",1, "Usuario actualizado, acción realizada por ");
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no actualizado", "El usuario no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Usuario no actualizado",1, "Actualizar usuario");
                }
            }else{
                mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void buscarAnunciante(String usuario, String contrasenia) {

        Predicate<UsuarioDto> predicado = AnuncianteUtil.buscarPorTodo(usuario, contrasenia);
        ObservableList<UsuarioDto> usuariosFiltrados = listaUsuarios.filtered(predicado);
        tableAnunciantes.setItems(usuariosFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposUsuarios();
        tableUsuarios.getSelectionModel().clearSelection();
        tableUsuarios.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerAnunciantes() {
        listaAnunciantes.addAll(anuncianteControllerService.obtenerAnunciantes());
    }

    private void mostrarInformacionAnunciante(AnuncianteDto anuncianteSeleccionado) {
        if(anuncianteSeleccionado != null){
            txfNombreAnunciante.setText(anuncianteSeleccionado.nombre());
            txfApellidoAnunciante.setText(anuncianteSeleccionado.apellido());
            txfCedula.setText(anuncianteSeleccionado.cedula());
            txfTelefono.setText(anuncianteSeleccionado.telefono());
            txfDireccion.setText(anuncianteSeleccionado.direccion());
            txfCorreo.setText(anuncianteSeleccionado.correo());
            txfFechaNacimiento.setText(anuncianteSeleccionado.fechaNacimiento());
            cmbUsuario.setValue(anuncianteSeleccionado.usuarioAsociado());
        }
    }

    private AnuncianteDto construirAnuncianteDto() {
        return new AnuncianteDto(
                txfNombreAnunciante.getText(),
                txfApellidoAnunciante.getText(),
                txfCedula.getText(),
                txfTelefono.getText(),
                txfDireccion.getText(),
                txfCorreo.getText(),
                txfFechaNacimiento.getText(),
                cmbUsuario.getValue(),
                "Anunciante"

        );
    }

    private void limpiarCamposUsuarios() {
        txfNombreAnunciante.setText("");
        txfApellidoAnunciante.setText("");
        txfCedula.setText("");
        txfTelefono.setText("");
        txfDireccion.setText("");
        txfCorreo.setText("");
        txfFechaNacimiento.setText("");
        cmbUsuario.setValue(null);
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        anuncianteControllerService.registrarAcciones(mensaje, nivel, accion);
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
