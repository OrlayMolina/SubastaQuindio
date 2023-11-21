package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.AnuncianteController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.AnuncianteUtil;
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

public class AnuncianteViewController {

    AnuncianteController anuncianteControllerService;
    SubastaQuindio subastaQuindio;

    UsuarioDto usuarioDto;
    ObservableList<AnuncianteDto> listaAnunciantes = FXCollections.observableArrayList();

    ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();
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
    private ComboBox<UsuarioDto> cmbUsuario;

    @FXML
    private PasswordField tpdContrasenia;

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
    private Label lblApellido;

    @FXML
    private Label lblCedula;

    @FXML
    private Label lblContrasenia;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblDireccion;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblUsuario;

    @FXML
    private Rectangle shape;

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
        actualizarAnunciante();
    }

    @FXML
    void agregarAnunciante(ActionEvent event) {
        crearAnunciante();
    }

    @FXML
    void busquedaAnunciante(ActionEvent event) {
        String cedula = txfCedula.getText();
        String nombres = txfNombreAnunciante.getText();
        String apellidos = txfApellidoAnunciante.getText();
        String correo = txfCorreo.getText();
        String telefono = txfTelefono.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbUsuario.getValue());
        buscarAnunciante(cedula, nombres, apellidos, correo, telefono, fechaNacimiento, usuarioAsociado);
    }

    @FXML
    void eliminarAnunciante(ActionEvent event) {
        eliminarAnunciante();
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        anuncianteControllerService = new AnuncianteController();
        subastaQuindio = new SubastaQuindio();
        initView();
        if(rolUsuarioLogeado.equals(String.valueOf(Rol.Anunciante))){
            cmbUsuario.setDisable(true);
        }
        cargarPestaniaSegunRol();
    }

    private void initView() {
        initDataBinding();
        obtenerAnunciantes();
        mostrarUsuarios();
        getListaUsuarios();
        tableAnunciantes.getItems().clear();
        tableAnunciantes.setItems(listaAnunciantes);
        listenerSelection();
    }

    private void initDataBinding() {
        colNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().apellido()));
        colCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cedula()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        colFechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaNacimiento()));
        colUsuarioAsociado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuarioAsociado().toString()));
    }

    private void listenerSelection() {
        tableAnunciantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncianteSeleccionado = newSelection;
            mostrarInformacionAnunciante(anuncianteSeleccionado);
        });
    }

    private void crearAnunciante() {

        AnuncianteDto anuncianteDto = construirAnuncianteDto();

        if(datosValidos(anuncianteDto)){
            if(validarEdadAnunciante(anuncianteDto)){
                if(anuncianteControllerService.agregarAnunciante(anuncianteDto)){
                    listaAnunciantes.add(anuncianteDto);
                    mostrarMensaje("Notificación anunciante", "Anunciante creado", "El anunciante se ha creado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Anunciante creado",1, "Creación de un anunciante, acción realizada por "  + usuarioLogeado);
                    limpiarCamposAnunciantes();

                }else{
                    mostrarMensaje("Notificación anunciante", "Anunciante no creado", "El anunciante no se ha creado", Alert.AlertType.ERROR);
                }
            }else {
                mostrarMensaje("Notificación anunciante", "Anunciante no creado", "El Anunciante es menor de edad", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación anunciante", "Anunciante no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    public boolean validarEdadAnunciante(AnuncianteDto anuncianteDto){
        return anuncianteControllerService.validarEdadAnunciante(anuncianteDto);
    }

    private void eliminarAnunciante() {
        boolean anuncianteEliminado = false;
        if(anuncianteSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al anunciante?")){
                anuncianteEliminado = anuncianteControllerService.eliminarAnunciante(anuncianteSeleccionado.cedula());
                if(anuncianteEliminado){
                    listaAnunciantes.remove(anuncianteSeleccionado);
                    anuncianteSeleccionado = null;
                    tableAnunciantes.getSelectionModel().clearSelection();
                    limpiarCamposAnunciantes();
                    registrarAcciones("Anunciante eliminado",1, "Anunciante eliminado, acción realizada por " + usuarioLogeado);
                    mostrarMensaje("Notificación anunciante", "Anunciante eliminado", "El anunciante se ha eliminado con éxito.", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación anunciante", "Anunciante no eliminado", "El anunciante no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación anunciante", "Anunciante no seleccionado", "Seleccionado un anunciante de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarAnunciante() {
        boolean anuncianteActualizado = false;

        String cedula = anuncianteSeleccionado.cedula();
        AnuncianteDto anuncianteDto = construirAnuncianteDto();

        if(anuncianteSeleccionado != null){

            if(datosValidos(anuncianteSeleccionado)){
                if(validarEdadAnunciante(anuncianteDto)){
                    anuncianteActualizado = anuncianteControllerService.actualizarAnunciante(cedula, anuncianteDto);
                    if(anuncianteActualizado){
                        listaAnunciantes.remove(anuncianteSeleccionado);
                        listaAnunciantes.add(anuncianteDto);
                        tableAnunciantes.refresh();
                        mostrarMensaje("Notificación anunciante", "Anunciante actualizado", "El anunciante se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                        limpiarCamposAnunciantes();
                        registrarAcciones("Anunciante actualizado",1, "Anunciante actualizado, acción realizada por " + usuarioLogeado);
                    }else{
                        mostrarMensaje("Notificación anunciante", "Anunciante no actualizado", "El anunciante no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                        registrarAcciones("Anunciante no actualizado",1, "Actualizar anunciante");
                    }
                }else {
                    mostrarMensaje("Notificación anunciante", "Anunciante no creado", "El Anunciante es menor de edad", Alert.AlertType.ERROR);
                }
            }else{
                mostrarMensaje("Notificación anunciante", "Anunciante no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void buscarAnunciante(String cedula, String nombres, String apellidos,
                                  String correo, String telefono, String fechaNacimiento, String usuarioAsociado) {

        Predicate<AnuncianteDto> predicado = AnuncianteUtil.buscarPorTodo(cedula, nombres, apellidos, correo, telefono, fechaNacimiento, usuarioAsociado);
        ObservableList<AnuncianteDto> anunciantesFiltrados = listaAnunciantes.filtered(predicado);
        tableAnunciantes.setItems(anunciantesFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposAnunciantes();
        tableAnunciantes.getSelectionModel().clearSelection();
        tableAnunciantes.setItems(listaAnunciantes);
        recargarInformacion();
        listenerSelection();
    }

    private void cargarPestaniaSegunRol(){
        if(rolUsuarioLogeado.equals(String.valueOf(Rol.Comprador))){
            btnActualizar.setVisible(false);
            btnEliminar.setVisible(false);
            btnAgregar.setVisible(false);
            btnBuscar.setVisible(false);
            btnLimpiarCampos.setVisible(false);
            shape.setVisible(false);
            lblApellido.setVisible(false);
            lblCedula.setVisible(false);
            lblContrasenia.setVisible(false);
            lblCorreo.setVisible(false);
            lblDireccion.setVisible(false);
            lblFecha.setVisible(false);
            lblNombre.setVisible(false);
            lblTelefono.setVisible(false);
            lblUsuario.setVisible(false);
            tableAnunciantes.setVisible(false);
            txfTelefono.setVisible(false);
            txfCorreo.setVisible(false);
            txfDireccion.setVisible(false);
            txfApellidoAnunciante.setVisible(false);
            txfCedula.setVisible(false);
            txfFechaNacimiento.setVisible(false);
            cmbUsuario.setVisible(false);
            tpdContrasenia.setVisible(false);
            txfNombreAnunciante.setVisible(false);
        }else{
            lblMensaje.setVisible(false);
            cmbUsuario.setEditable(false);
        }
    }

    public void mostrarUsuarios(){
        cmbUsuario.setItems(listaUsuarios);
    }

    private void obtenerAnunciantes() {
        listaAnunciantes.addAll(anuncianteControllerService.obtenerAnunciantes());
    }

    public void recargarInformacion(){
        cmbUsuario.getItems().clear();
        getListaUsuarios();
        cmbUsuario.setItems(listaUsuarios);
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
            cmbUsuario.setValue(anuncianteSeleccionado.getUsuarioAsociado());
        }
    }

    public AnuncianteDto construirAnuncianteDto() {
        String nombre = txfNombreAnunciante.getText();
        String apellido = txfApellidoAnunciante.getText();
        String cedula = txfCedula.getText();
        String telefono = txfTelefono.getText();
        String contrasenia = tpdContrasenia.getText();
        String direccion = txfDireccion.getText();
        String rol = String.valueOf(Rol.Anunciante);
        String correo = txfCorreo.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbUsuario.getValue());
        return new AnuncianteDto(nombre, apellido, cedula, telefono, contrasenia,direccion, rol,  correo, fechaNacimiento, usuarioAsociado);
    }

    private void limpiarCamposAnunciantes() {
        txfNombreAnunciante.setText("");
        txfApellidoAnunciante.setText("");
        txfCedula.setText("");
        txfTelefono.setText("");
        txfDireccion.setText("");
        txfCorreo.setText("");
        txfFechaNacimiento.setText("");
        cmbUsuario.setValue(null);
    }

    public ObservableList<UsuarioDto> getListaUsuarios() {
        listaUsuarios.addAll(anuncianteControllerService.obtenerUsuarios());
        return listaUsuarios;
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        anuncianteControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    private boolean datosValidos(AnuncianteDto anuncianteDto) {
        String mensaje = "";

        if(anuncianteDto.cedula() == null || anuncianteDto.cedula().equals(""))
            mensaje += "La cédula del anunciante es invalido \n" ;
        if(anuncianteDto.nombre() == null || anuncianteDto.nombre() .equals(""))
            mensaje += "Los nombres del anunciante es invalido \n" ;
        if(anuncianteDto.apellido() == null || anuncianteDto.apellido() .equals(""))
            mensaje += "los apellidos del anunciante es invalido \n" ;
        if(anuncianteDto.telefono() == null || anuncianteDto.telefono() .equals(""))
            mensaje += "El teléfono del anunciante es invalido \n" ;
        if(anuncianteDto.direccion() == null || anuncianteDto.direccion() .equals(""))
            mensaje += "El edad del anunciante es invalido \n" ;
        if(anuncianteDto.correo() == null || anuncianteDto.correo() .equals(""))
            mensaje += "El correo del anunciante es invalido \n" ;
        if(anuncianteDto.fechaNacimiento() == null || anuncianteDto.fechaNacimiento() .equals(""))
            mensaje += "El teléfono del anunciante es invalido \n" ;
        if(anuncianteDto.usuarioAsociado() == null || anuncianteDto.usuarioAsociado() .equals(""))
            mensaje += "El usuario asociado del anunciante es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación anunciante", "Anunciante no creado", mensaje, Alert.AlertType.ERROR);
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
