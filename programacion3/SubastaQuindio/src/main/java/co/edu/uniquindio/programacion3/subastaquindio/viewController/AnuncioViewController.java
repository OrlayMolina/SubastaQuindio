package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.AnuncioController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.EstadoAnuncios;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.AnuncioUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.*;

public class AnuncioViewController {

    AnuncioController anuncioControllerService;
    PujaViewController pujaViewController;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    ObservableList<String> listaEstados = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    ProductoDto productoDto;
    SubastaQuindio subastaQuindio;
    AnuncioDto anuncioSeleccionado;

    String foto;

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
    private ImageView imagen;

    @FXML
    private Label lblAnunciante;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblFechaFin;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblProducto;

    @FXML
    private Label lblValor;

    @FXML
    private Rectangle shapeFoto;

    @FXML
    private ComboBox<AnuncianteDto> cmbAnunciante;

    @FXML
    private ComboBox<String> cmbEstadoAnuncio;

    @FXML
    private ComboBox<ProductoDto> cmbProducto;

    @FXML
    private TableColumn<AnuncioDto, String> colAnunciante;

    @FXML
    private TableColumn<AnuncioDto, String> colCodigo;

    @FXML
    private TableColumn<AnuncioDto, String> colDescripcion;

    @FXML
    private TableColumn<AnuncioDto, String> colEstado;

    @FXML
    private TableColumn<AnuncioDto, String> colFechaFinPublicacion;

    @FXML
    private TableColumn<AnuncioDto, String> colFechaPublicacion;

    @FXML
    private TableColumn<AnuncioDto, String> colProducto;

    @FXML
    private TableColumn<AnuncioDto, String> colValorInicial;

    @FXML
    private TextField txfFechaFinPublicacion;

    @FXML
    private TextField txfFechaPublicacion;

    @FXML
    private TableView<AnuncioDto> tableAnuncios;

    @FXML
    private TextArea txaDescripcion;

    @FXML
    private TextField txfCodigoAnuncio;

    @FXML
    private TextField txfValorInicial;

    @FXML
    void actualizarAnuncio(ActionEvent event) {
        actualizarAnuncio();
    }

    @FXML
    void agregarAnuncio(ActionEvent event) {
        crearAnuncio();
    }

    @FXML
    void busquedaAnuncio(ActionEvent event) {
        String codigo = txfCodigoAnuncio.getText();
        ProductoDto productoDto = cmbProducto.getValue();
        AnuncianteDto anuncianteDto = cmbAnunciante.getValue();
        String fechaPublicacion = txfFechaPublicacion.getText();
        String fechaFinPublicacion = txfFechaFinPublicacion.getText();
        String valorInicial = txfValorInicial.getText();
        String descripcion = txaDescripcion.getText();
        String estado = cmbEstadoAnuncio.getValue();
        buscarAnuncio(codigo, productoDto, anuncianteDto, fechaPublicacion,
                fechaFinPublicacion, valorInicial, descripcion, estado);
    }

    @FXML
    void eliminarAnuncio(ActionEvent event) {
        eliminarAnuncio();
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }


    @FXML
    void seleccionImagen(ActionEvent event) {
        String producto = String.valueOf(cmbProducto.getValue());
        String traerImagen = producto.split("  ")[0];
        foto = anuncioControllerService.obtenerProducto(traerImagen);
        mostrarImagen(foto);
    }

    @FXML
    void initialize() {
        anuncioControllerService = new AnuncioController();
        pujaViewController = new PujaViewController();
        subastaQuindio = new SubastaQuindio();
        initView();
        cmbAnunciante.setValue(obtenerAnunciante(cedulaUsuario));
        cmbEstadoAnuncio.setValue(String.valueOf(EstadoAnuncios.Publicado));
        cargarPestaniaSegunRol();
    }

    private void initView() {
        initDataBinding();
        obtenerAnunciantes();
        obtenerProductos();
        obtenerAnuncios();
        mostrarProducto();
        mostrarAnunciantes();
        mostrarEstados();
        tableAnuncios.getItems().clear();
        tableAnuncios.setItems(listaAnuncioDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductoDto().toString()));
        colAnunciante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnuncianteDto().toString()));
        colFechaPublicacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaPublicacion().toString()));
        colFechaFinPublicacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaFinPublicacion().toString()));
        colValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().valorInicial()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));

    }

    private void listenerSelection() {
        tableAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncioSeleccionado = newSelection;
            mostrarInformacionAnuncio(anuncioSeleccionado);
        });
    }

    private void cargarPestaniaSegunRol(){
        if(rolUsuarioLogeado.equals(String.valueOf(Rol.Comprador))){
            btnActualizar.setVisible(false);
            btnEliminar.setVisible(false);
            btnAgregar.setVisible(false);
            btnBuscar.setVisible(false);
            btnLimpiarCampos.setVisible(false);
            shapeFoto.setVisible(false);
            lblAnunciante.setVisible(false);
            txfCodigoAnuncio.setVisible(false);
            lblCodigo.setVisible(false);
            lblEstado.setVisible(false);
            lblFecha.setVisible(false);
            lblFechaFin.setVisible(false);
            lblProducto.setVisible(false);
            lblValor.setVisible(false);
            txfFechaPublicacion.setVisible(false);
            txfFechaFinPublicacion.setVisible(false);
            txfValorInicial.setVisible(false);
            txaDescripcion.setVisible(false);
            tableAnuncios.setVisible(false);
            cmbAnunciante.setVisible(false);
            cmbProducto.setVisible(false);
            cmbEstadoAnuncio.setVisible(false);

        }else{
            lblMensaje.setVisible(false);
        }
    }

    public void mostrarProducto(){
        cmbProducto.setItems(listaProductosDto);
    }

    public void mostrarAnunciantes(){
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    public void mostrarEstados(){
        listaEstados.add(String.valueOf(EstadoAnuncios.Publicado));
        listaEstados.add(String.valueOf(EstadoAnuncios.Finalizado));
        listaEstados.add(String.valueOf(EstadoAnuncios.Cerrado_pagado_con_exito));
        cmbEstadoAnuncio.setItems(listaEstados);
    }

    private AnuncianteDto obtenerAnunciante(String cedula){
        return anuncioControllerService.obtenerAnunciante(cedula);
    }

    public void recargarInformacion(){

        cmbProducto.getItems().clear();
        obtenerProductos();
        cmbProducto.setItems(listaProductosDto);
        cmbAnunciante.getItems().clear();
        obtenerAnunciantes();
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    private void crearAnuncio() {

        AnuncioDto anuncioDto = construirAnuncioDto();

        if(datosValidos(anuncioDto)){
            if(anuncioControllerService.agregarAnuncio(anuncioDto)){
                listaAnuncioDto.add(anuncioDto);
                mostrarMensaje("Notificación anuncio", "Anuncio creado", "El anuncio se ha creado con éxito", Alert.AlertType.INFORMATION);
                registrarAcciones("Anuncio creado",1, "Creación de un usuario, acción realizada por "  + usuarioLogeado);
                limpiarCamposAnuncios();

            }else{
                mostrarMensaje("Notificación anuncio", "Anuncio no creado", "El anuncio no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación anuncio", "Anuncio no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarAnuncio() {
        boolean anuncioEliminado = false;
        if(anuncioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar el anuncio?")){
                anuncioEliminado = anuncioControllerService.eliminarAnuncio(anuncioSeleccionado.codigo());
                if(anuncioEliminado){
                    listaAnuncioDto.remove(anuncioSeleccionado);
                    anuncioSeleccionado = null;
                    tableAnuncios.getSelectionModel().clearSelection();
                    limpiarCamposAnuncios();
                    registrarAcciones("Anuncio eliminado",1, "anuncio eliminado, acción realizada por " + usuarioLogeado);
                    mostrarMensaje("Notificación anuncio", "Anuncio eliminado", "El anuncio se ha eliminado con éxito.", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación anuncio", "Anuncio no eliminado", "El anuncio no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación anuncio", "Anuncio no seleccionado", "Seleccionado un anuncio de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarAnuncio() {
        boolean anuncioActualizado = false;

        String usuario = anuncioSeleccionado.codigo();
        AnuncioDto anuncioDto = construirAnuncioDto();

        if(anuncioSeleccionado != null){

            if(datosValidos(anuncioSeleccionado)){
                anuncioActualizado = anuncioControllerService.actualizarAnuncio(usuario, anuncioDto);
                if(anuncioActualizado){
                    listaAnuncioDto.remove(anuncioSeleccionado);
                    listaAnuncioDto.add(anuncioDto);
                    tableAnuncios.refresh();
                    mostrarMensaje("Notificación anuncio", "Anuncio actualizado", "El anuncio se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposAnuncios();
                    registrarAcciones("Anuncio actualizado",1, "Anuncio actualizado, acción realizada por " + usuarioLogeado);
                }else{
                    mostrarMensaje("Notificación anuncio", "Anuncio no actualizado", "El anuncio no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Anuncio no actualizado",1, "Actualizar anuncio");
                }
            }else{
                mostrarMensaje("Notificación anuncio", "Anuncio no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void buscarAnuncio(String codigo, ProductoDto productoDto, AnuncianteDto anuncianteDto,
                               String fechaPublicacion, String fechaFinPublicacion,
                               String valorInicial, String descripcion, String estado) {

        Predicate<AnuncioDto> predicado = AnuncioUtil.buscarPorTodo(codigo, productoDto, anuncianteDto,
                fechaPublicacion, fechaFinPublicacion, valorInicial, descripcion, estado);
        ObservableList<AnuncioDto> anunciosFiltrados = listaAnuncioDto.filtered(predicado);
        tableAnuncios.setItems(anunciosFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposAnuncios();
        tableAnuncios.getSelectionModel().clearSelection();
        tableAnuncios.setItems(listaAnuncioDto);
        recargarInformacion();
        cmbAnunciante.setValue(obtenerAnunciante(cedulaUsuario));
        cmbEstadoAnuncio.setValue(String.valueOf(EstadoAnuncios.Publicado));
        listenerSelection();
    }

    private void obtenerAnunciantes() {
        listaAnunciantesDto.addAll(anuncioControllerService.obtenerAnunciantes());
    }

    private void obtenerProductos() {
        listaProductosDto.addAll(anuncioControllerService.obtenerProductos());
    }

    private void obtenerAnuncios() {
        listaAnuncioDto.addAll(anuncioControllerService.obtenerAnuncios());
    }
    private void mostrarInformacionAnuncio(AnuncioDto anuncioSeleccionado) {
        if (anuncioSeleccionado != null) {
            txfCodigoAnuncio.setText(anuncioSeleccionado.codigo());
            cmbProducto.setValue(anuncioSeleccionado.getProductoDto());
            cmbAnunciante.setValue(anuncioSeleccionado.getAnuncianteDto());
            txfFechaPublicacion.setText(anuncioSeleccionado.fechaPublicacion());
            txfFechaFinPublicacion.setText(anuncioSeleccionado.fechaFinPublicacion());
            txfValorInicial.setText(String.valueOf(anuncioSeleccionado.valorInicial()));
            txaDescripcion.setText(anuncioSeleccionado.descripcion());
            cmbEstadoAnuncio.setValue(anuncioSeleccionado.estado());

            String rutaImagen = anuncioSeleccionado.foto();

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                File file = new File(rutaImagen);
                Image image = new Image(file.toURI().toString());
                imagen.setImage(image);
            }else{
                imagen.setImage(null);
            }

        }
    }


    private AnuncioDto construirAnuncioDto() {
        String codigo = txfCodigoAnuncio.getText();
        String producto = String.valueOf(cmbProducto.getValue());
        String anunciante = String.valueOf(cmbAnunciante.getValue());
        String fechaPublicacion = txfFechaPublicacion.getText();
        String fechaFinPublicacion = txfFechaFinPublicacion.getText();
        String valorInicial = txfValorInicial.getText();
        String descripcion = txaDescripcion.getText();

        String traerImagen = producto.split("  ")[0];
        foto = anuncioControllerService.obtenerProducto(traerImagen);

        String estado = cmbEstadoAnuncio.getValue();

        return new AnuncioDto(codigo, producto, anunciante,fechaPublicacion,
                fechaFinPublicacion, valorInicial, descripcion, foto, estado);
    }


    private void limpiarCamposAnuncios() {
        txfCodigoAnuncio.setText("");
        cmbProducto.setValue(null);
        cmbAnunciante.setValue(null);
        txfFechaPublicacion.setText("");
        txfFechaFinPublicacion.setText("");
        txfValorInicial.setText("");
        txaDescripcion.setText("");
        cmbEstadoAnuncio.setValue(null);
        imagen.setImage(null);
    }

    private void mostrarImagen(String rutaImagen) {
        try {
            File file = new File(rutaImagen);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());

                imagen.setImage(image);
            } else {
                imagen.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        anuncioControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    private boolean datosValidos(AnuncioDto anuncioDto) {
        String mensaje = "";

        if(anuncioDto.codigo() == null || anuncioDto.codigo() .equals(""))
            mensaje += "El código del Anuncio es invalido \n" ;
        if(anuncioDto.producto() == null || anuncioDto.producto() .equals(""))
            mensaje += "El producto del anuncio es invalido \n" ;
        if(anuncioDto.fechaPublicacion() == null || anuncioDto.fechaPublicacion() .equals(""))
            mensaje += "La fecha de Publicación del anuncio es invalido \n" ;
        if(anuncioDto.fechaFinPublicacion() == null || anuncioDto.fechaFinPublicacion() .equals(""))
            mensaje += "La fecha fin de Publicación  del anuncio es invalido \n" ;
        if(anuncioDto.valorInicial() == null || anuncioDto.valorInicial() .equals(""))
            mensaje += "El valor inicial del anuncio del  es invalido \n" ;
        if(anuncioDto.descripcion() == null || anuncioDto.descripcion() .equals(""))
            mensaje += "La descripción del anuncio es invalido \n" ;
        if(anuncioDto.estado() == null || anuncioDto.estado() .equals(""))
            mensaje += "El estado del anuncio es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación anuncio", "Anuncio no creado", mensaje, Alert.AlertType.ERROR);
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
