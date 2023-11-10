package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.AnuncioController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.EstadoAnuncios;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.AnuncioUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.usuarioLogeado;

public class AnuncioViewController {

    AnuncioController anuncioControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    ObservableList<String> listaEstados = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    ProductoDto productoDto;
    SubastaQuindio subastaQuindio;
    AnuncioDto anuncioSeleccionado;



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
        double valorInicial = Double.parseDouble(txfValorInicial.getText());
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
    void initialize() {
        anuncioControllerService = new AnuncioController();
        subastaQuindio = new SubastaQuindio();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerAnunciantes();
        obtenerProductos();
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
        colValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));

    }

    private void listenerSelection() {
        tableAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncioSeleccionado = newSelection;
            mostrarInformacionAnuncio(anuncioSeleccionado);
        });
    }

    public void mostrarProducto(){
        listaProductosDto.add(productoDto);
        cmbProducto.setItems(listaProductosDto);
    }

    public void mostrarAnunciantes(){
        listaAnunciantesDto.add(anuncianteDto);
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    public void mostrarEstados(){
        listaEstados.add(String.valueOf(EstadoAnuncios.Publicado));
        listaEstados.add(String.valueOf(EstadoAnuncios.Finalizado));
        listaEstados.add(String.valueOf(EstadoAnuncios.Cerrado_pagado_con_exito));
        cmbEstadoAnuncio.setItems(listaEstados);
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
                               double valorInicial, String descripcion, String estado) {

        Predicate<AnuncioDto> predicado = AnuncioUtil.buscarPorTodo(codigo, productoDto, anuncianteDto,
                fechaPublicacion, fechaFinPublicacion, valorInicial, descripcion, estado);
        ObservableList<AnuncioDto> anunciosFiltrados = listaAnuncioDto.filtered(predicado);
        tableAnuncios.setItems(anunciosFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposAnuncios();
        tableAnuncios.getSelectionModel().clearSelection();
        tableAnuncios.setItems(listaAnuncioDto);
        listenerSelection();
    }

    private void obtenerAnunciantes() {
        listaAnunciantesDto.addAll(anuncioControllerService.obtenerAnunciantes());
    }

    private void obtenerProductos() {
        listaProductosDto.addAll(anuncioControllerService.obtenerProductos());
    }

    private void mostrarInformacionAnuncio(AnuncioDto anuncioSeleccionado) {
        if(anuncioSeleccionado != null){
            txfCodigoAnuncio.setText(anuncioSeleccionado.codigo());
            cmbProducto.setValue(anuncioSeleccionado.getProductoDto());
            cmbAnunciante.setValue(anuncioSeleccionado.getAnuncianteDto());
            txfFechaPublicacion.setText(anuncioSeleccionado.fechaPublicacion());
            txfFechaFinPublicacion.setText(anuncioSeleccionado.fechaFinPublicacion());
            txfValorInicial.setText(String.valueOf(anuncioSeleccionado.valorInicial()));
            txaDescripcion.setText(anuncioSeleccionado.descripcion());
            cmbEstadoAnuncio.setValue(anuncioSeleccionado.estado());
        }
    }

    private AnuncioDto construirAnuncioDto() {
        String codigo = txfCodigoAnuncio.getText();
        String producto = String.valueOf(cmbProducto.getValue());
        String anunciante = String.valueOf(cmbAnunciante.getValue());
        String fechaPublicacion = txfFechaPublicacion.getText();
        String fechaFinPublicacion = txfFechaFinPublicacion.getText();
        double valorInicial = Double.parseDouble(txfValorInicial.getText());
        String descripcion = txaDescripcion.getText();
        String estado = cmbEstadoAnuncio.getValue();

        return new AnuncioDto(codigo, producto, anunciante,fechaPublicacion,
                fechaFinPublicacion, valorInicial, descripcion, estado);
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
        if(anuncioDto.valorInicial() == 0)
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
