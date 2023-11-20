package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.CompradorController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.CompradorUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.usuarioLogeado;

public class CompradorViewController {

    CompradorController compradorControllerService;
    SubastaQuindio subastaQuindio;
    UsuarioDto usuarioDto;
    ObservableList<CompradorDto> listaCompradores = FXCollections.observableArrayList();
    ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();
    CompradorDto compradorSeleccionado;

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
    private TableColumn<CompradorDto, String> colApellidos;

    @FXML
    private PasswordField tpdContrasenia;

    @FXML
    private TableColumn<CompradorDto, String> colCedula;

    @FXML
    private TableColumn<CompradorDto, String> colCorreo;

    @FXML
    private TableColumn<CompradorDto, String> colDireccion;

    @FXML
    private TableColumn<CompradorDto, String> colFechaNacimiento;

    @FXML
    private TableColumn<CompradorDto, String> colNombres;

    @FXML
    private TableColumn<CompradorDto, String> colTelefono;

    @FXML
    private TableColumn<CompradorDto, String> colUsuarioAsociado;

    @FXML
    private TableView<CompradorDto> tableCompradores;

    @FXML
    private TextField txfApellidosComprador;

    @FXML
    private TextField txfCedula;

    @FXML
    private TextField txfCorreo;

    @FXML
    private TextField txfDireccion;

    @FXML
    private TextField txfFechaNacimiento;

    @FXML
    private TextField txfNombreComprador;

    @FXML
    private TextField txfTelefono;

    @FXML
    void actualizarComprador(ActionEvent event) {
        actualizarAnunciante();
    }

    @FXML
    void agregarComprador(ActionEvent event) {
        crearAnunciante();
    }

    @FXML
    void busquedaComprador(ActionEvent event) {
        String cedula = txfCedula.getText();
        String nombres = txfNombreComprador.getText();
        String apellidos = txfApellidosComprador.getText();
        String correo = txfCorreo.getText();
        String telefono = txfTelefono.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbUsuario.getValue());
        buscarAnunciante(cedula, nombres, apellidos, correo, telefono, fechaNacimiento, usuarioAsociado);
    }

    @FXML
    void eliminarComprador(ActionEvent event) {
        eliminarAnunciante();
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        compradorControllerService = new CompradorController();
        subastaQuindio = new SubastaQuindio();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCompradores();
        mostrarUsuarios();
        getListaUsuarios();
        tableCompradores.getItems().clear();
        tableCompradores.setItems(listaCompradores);
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
        tableCompradores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            compradorSeleccionado = newSelection;
            mostrarInformacionComprador(compradorSeleccionado);
        });
    }

    private void crearAnunciante() {

        CompradorDto compradorDto = construirCompradorDto();

        if(datosValidos(compradorDto)){
            if(validarEdadComprador(compradorDto)){
                if(compradorControllerService.agregarComprador(compradorDto)){
                    listaCompradores.add(compradorDto);
                    mostrarMensaje("Notificación comprador", "Comprador creado", "El comprador se ha creado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Comprador creado",1, "Creación de un comprador, acción realizada por " + usuarioLogeado );
                    limpiarCamposAnunciantes();

                }else{
                    mostrarMensaje("Notificación comprador", "Comprador no creado", "El comprador no se ha creado", Alert.AlertType.ERROR);
                }
            }else {
                mostrarMensaje("Notificación comprador", "Comprador no creado", "El comprador es menor de edad", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación comprador", "Comprador no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarAnunciante() {
        boolean compradorEliminado = false;
        if(compradorSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al comprador?")){
                compradorEliminado = compradorControllerService.eliminarComprador(compradorSeleccionado.cedula());
                if(compradorEliminado){
                    listaCompradores.remove(compradorSeleccionado);
                    compradorSeleccionado = null;
                    tableCompradores.getSelectionModel().clearSelection();
                    limpiarCamposAnunciantes();
                    registrarAcciones("Comprador eliminado",1, "Comprador eliminado, acción realizada por " + usuarioLogeado);
                    mostrarMensaje("Notificación comprador", "Comprador eliminado", "El comprador se ha eliminado con éxito.", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación comprador", "Comprador no eliminado", "El comprador no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación comprador", "Comprador no seleccionado", "Seleccionado un comprador de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarAnunciante() {
        boolean compradorActualizado = false;

        String cedula = compradorSeleccionado.cedula();
        CompradorDto compradorDto = construirCompradorDto();

        if(compradorSeleccionado != null){

            if(datosValidos(compradorSeleccionado)){
                if(validarEdadComprador(compradorDto)){
                    compradorActualizado = compradorControllerService.actualizarAnunciante(cedula, compradorDto);
                    if(compradorActualizado){
                        listaCompradores.remove(compradorSeleccionado);
                        listaCompradores.add(compradorDto);
                        tableCompradores.refresh();
                        mostrarMensaje("Notificación comprador", "Comprador actualizado", "El comprador se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                        limpiarCamposAnunciantes();
                        registrarAcciones("Comprador actualizado",1, "Comprador actualizado, acción realizada por " + usuarioLogeado);
                    }else{
                        mostrarMensaje("Notificación comprador", "Comprador no actualizado", "El comprador no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                        registrarAcciones("Comprador no actualizado",1, "Actualizar anunciante");
                    }
                }else {
                    mostrarMensaje("Notificación comprador", "Comprador no creado", "El comprador es menor de edad", Alert.AlertType.ERROR);
                }
            }else{
                mostrarMensaje("Notificación comprador", "Comprador no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void buscarAnunciante(String cedula, String nombres, String apellidos,
                                  String correo, String telefono, String fechaNacimiento, String usuarioAsociado) {

        Predicate<CompradorDto> predicado = CompradorUtil.buscarPorTodo(cedula, nombres, apellidos, correo, telefono, fechaNacimiento, usuarioAsociado);
        ObservableList<CompradorDto> compradoresFiltrados = listaCompradores.filtered(predicado);
        tableCompradores.setItems(compradoresFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposAnunciantes();
        tableCompradores.getSelectionModel().clearSelection();
        tableCompradores.setItems(listaCompradores);
        listenerSelection();
    }

    public void mostrarUsuarios(){
        cmbUsuario.setItems(listaUsuarios);
    }

    private void obtenerCompradores() {
        listaCompradores.addAll(compradorControllerService.obtenerCompradores());
    }

    private void mostrarInformacionComprador(CompradorDto anuncianteSeleccionado) {
        if(anuncianteSeleccionado != null){
            txfNombreComprador.setText(anuncianteSeleccionado.nombre());
            txfApellidosComprador.setText(anuncianteSeleccionado.apellido());
            txfCedula.setText(anuncianteSeleccionado.cedula());
            txfTelefono.setText(anuncianteSeleccionado.telefono());
            txfDireccion.setText(anuncianteSeleccionado.direccion());
            txfCorreo.setText(anuncianteSeleccionado.correo());
            txfFechaNacimiento.setText(anuncianteSeleccionado.fechaNacimiento());
            cmbUsuario.setValue(anuncianteSeleccionado.getUsuarioAsociado());
        }
    }

    private CompradorDto construirCompradorDto() {
        String nombre = txfNombreComprador.getText();
        String apellido = txfApellidosComprador.getText();
        String cedula = txfCedula.getText();
        String telefono = txfTelefono.getText();
        String contrasenia = tpdContrasenia.getText();
        String direccion = txfDireccion.getText();
        String rol = String.valueOf(Rol.Comprador);
        String correo = txfCorreo.getText();
        String fechaNacimiento = txfFechaNacimiento.getText();
        String usuarioAsociado = String.valueOf(cmbUsuario.getValue());
        return new CompradorDto(nombre, apellido, cedula, telefono, contrasenia, direccion, rol,correo, fechaNacimiento, usuarioAsociado);
    }

    private void limpiarCamposAnunciantes() {
        txfNombreComprador.setText("");
        txfApellidosComprador.setText("");
        txfCedula.setText("");
        txfTelefono.setText("");
        txfDireccion.setText("");
        txfCorreo.setText("");
        txfFechaNacimiento.setText("");
        cmbUsuario.setValue(null);
    }

    public ObservableList<UsuarioDto> getListaUsuarios() {
        listaUsuarios.addAll(compradorControllerService.obtenerUsuarios());
        return listaUsuarios;
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        compradorControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    public boolean validarEdadComprador(CompradorDto compradorDto){
        return compradorControllerService.validarEdadComprador(compradorDto);
    }

    private boolean datosValidos(CompradorDto compradorDto) {
        String mensaje = "";

        if(compradorDto.cedula() == null || compradorDto.cedula().equals(""))
            mensaje += "La cédula del comprador es invalido \n" ;
        if(compradorDto.nombre() == null || compradorDto.nombre() .equals(""))
            mensaje += "Los nombres del comprador es invalido \n" ;
        if(compradorDto.apellido() == null || compradorDto.apellido() .equals(""))
            mensaje += "los apellidos del comprador es invalido \n" ;
        if(compradorDto.telefono() == null || compradorDto.telefono() .equals(""))
            mensaje += "El teléfono del comprador es invalido \n" ;
        if(compradorDto.direccion() == null || compradorDto.direccion() .equals(""))
            mensaje += "El edad del comprador es invalido \n" ;
        if(compradorDto.correo() == null || compradorDto.correo() .equals(""))
            mensaje += "El correo del comprador es invalido \n" ;
        if(compradorDto.fechaNacimiento() == null || compradorDto.fechaNacimiento() .equals(""))
            mensaje += "El teléfono del comprador es invalido \n" ;
        if(compradorDto.usuarioAsociado() == null || compradorDto.usuarioAsociado() .equals(""))
            mensaje += "El usuario asociado del comprador es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación comprador", "Comprador no creado", mensaje, Alert.AlertType.ERROR);
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
