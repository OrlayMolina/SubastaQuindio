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
        String usuarioAsociado = cmbUsuario.getValue();
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
    }

    private void initView() {
        initDataBinding();
        obtenerAnunciantes();
        tableAnunciantes.getItems().clear();
        tableAnunciantes.setItems(listaAnunciantes);
        listenerSelection();
    }

    private void initDataBinding() {
        colNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().apellido()));
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
            if(anuncianteControllerService.agregarAnunciante(anuncianteDto)){
                listaAnunciantes.add(anuncianteDto);
                mostrarMensaje("Notificación anunciante", "Anunciante creado", "El anunciante se ha creado con éxito", Alert.AlertType.INFORMATION);
                registrarAcciones("Anunciante creado",1, "Creación de un anunciante, acción realizada por " );
                limpiarCamposAnunciantes();

            }else{
                mostrarMensaje("Notificación anunciante", "Anunciante no creado", "El anunciante no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación anunciante", "Anunciante no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

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
                    registrarAcciones("Anunciante eliminado",1, "Anunciante eliminado, acción realizada por ");
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
                anuncianteActualizado = anuncianteControllerService.actualizarAnunciante(cedula, anuncianteDto);
                if(anuncianteActualizado){
                    listaAnunciantes.remove(anuncianteSeleccionado);
                    listaAnunciantes.add(anuncianteDto);
                    tableAnunciantes.refresh();
                    mostrarMensaje("Notificación anunciante", "Anunciante actualizado", "El anunciante se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposAnunciantes();
                    registrarAcciones("Anunciante actualizado",1, "Anunciante actualizado, acción realizada por ");
                }else{
                    mostrarMensaje("Notificación anunciante", "Anunciante no actualizado", "El anunciante no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Anunciante no actualizado",1, "Actualizar anunciante");
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
