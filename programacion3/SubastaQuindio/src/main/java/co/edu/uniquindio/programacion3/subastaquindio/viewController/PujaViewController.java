package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.PujaController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.PujaDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.ListaAnuncioUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.Predicate;

public class PujaViewController {

    PujaController pujaControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    ObservableList<PujaDto> listaOfertasDto = FXCollections.observableArrayList();
    ObservableList<String> listaTipoProducto = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnunciosDto = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    ProductoDto productoDto;
    AnuncioDto anuncioDto;
    SubastaQuindio subastaQuindio;
    AnuncioDto anuncioSeleccionado;
    PujaDto ofertaSeleccionada;

    @FXML
    private Button btnHacerOferta;

    @FXML
    private ComboBox<AnuncianteDto> cmbAnunciante;

    @FXML
    private ComboBox<ProductoDto> cmbProducto;

    @FXML
    private TableColumn<AnuncioDto, String> colCodigoAnuncio;

    @FXML
    private TableColumn<PujaDto, String> colCodigoOferta;

    @FXML
    private TableColumn<PujaDto, String> colComprador;

    @FXML
    private TableColumn<AnuncioDto, String> colDescripcion;

    @FXML
    private TableColumn<AnuncioDto, String> colFechaFinAnuncio;

    @FXML
    private TableColumn<PujaDto, String> colOferta;

    @FXML
    private TableColumn<AnuncioDto, String> colProducto;

    @FXML
    private TableColumn<AnuncioDto, String> colValorInicial;

    @FXML
    private TableView<AnuncioDto> tableAnuncios;

    @FXML
    private TableView<PujaDto> tableOfertas;

    @FXML
    private TextField txfCodigoAnuncio;

    @FXML
    private TextField txfFechaFinPuja;

    @FXML
    private TextField txfOferta;

    @FXML
    private TextField txfValorInicial;

    @FXML
    void busquedaPersonalizada(ActionEvent event) {
        String codigo = txfCodigoAnuncio.getText();
        buscarListaAnuncio(codigo);
    }

    @FXML
    void cancelarFiltro(ActionEvent event) {
        cancelarBusqueda();
    }
    @FXML
    void hacerOferta(ActionEvent event) {

    }

    @FXML
    void initialize() {
        pujaControllerService = new PujaController();
        subastaQuindio = new SubastaQuindio();
        initView();
    }

    private void initView() {
        initDataBinding();
        initDataBindingOferta();
        obtenerAnunciantes();
        obtenerProductos();
        obtenerAnuncios();
        obtenerOfertas();
        mostrarProducto();
        mostrarAnunciantes();
        mostrarProducto();
        tableAnuncios.getItems().clear();
        tableAnuncios.setItems(listaAnunciosDto);
        tableOfertas.getItems().clear();
        tableOfertas.setItems(listaOfertasDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCodigoAnuncio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductoDto().toString()));
        colFechaFinAnuncio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnuncianteDto().toString()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        colValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));

    }

    private void initDataBindingOferta() {
        colCodigoOferta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        colComprador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompradorDto().toString()));
        colOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));

    }

    private void listenerSelection() {
        tableAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncioSeleccionado = newSelection;
            mostrarInformacionAnuncio(anuncioSeleccionado);
        });
    }


    public void mostrarProducto(){
        cmbProducto.setItems(listaProductosDto);
    }

    public void mostrarAnunciantes(){
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    private void buscarListaAnuncio(String codigo) {

        Predicate<AnuncioDto> predicado = ListaAnuncioUtil.buscarPorTodo(codigo);
        ObservableList<AnuncioDto> anunciosFiltrados = listaAnunciosDto.filtered(predicado);
        tableAnuncios.setItems(anunciosFiltrados);
    }

    private void cancelarBusqueda(){
        limpiarCamposAnuncios();
        tableAnuncios.getSelectionModel().clearSelection();
        tableAnuncios.setItems(listaAnunciosDto);
        listenerSelection();
    }

    private void obtenerAnunciantes() {
        listaAnunciantesDto.addAll(pujaControllerService.obtenerAnunciantes());
    }

    private void obtenerProductos() {
        listaProductosDto.addAll(pujaControllerService.obtenerProductos());
    }

    private void obtenerAnuncios() {
        listaAnunciosDto.addAll(pujaControllerService.obtenerAnuncios());
    }

    private void obtenerOfertas() {
        listaOfertasDto.addAll(pujaControllerService.obtenerPujas());
    }



    private void mostrarInformacionAnuncio(AnuncioDto anuncioSeleccionado) {
        if(anuncioSeleccionado != null){
            txfCodigoAnuncio.setText(anuncioSeleccionado.codigo());
            cmbProducto.setValue(anuncioSeleccionado.getProductoDto());
            cmbAnunciante.setValue(anuncioSeleccionado.getAnuncianteDto());
            txfFechaFinPuja.setText(anuncioSeleccionado.fechaPublicacion());
            txfValorInicial.setText(String.valueOf(anuncioSeleccionado.valorInicial()));
        }
    }



    private void limpiarCamposAnuncios() {
        txfCodigoAnuncio.setText("");
        cmbProducto.setValue(null);
        cmbAnunciante.setValue(null);
        txfFechaFinPuja.setText("");
        txfValorInicial.setText("");
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        pujaControllerService.registrarAcciones(mensaje, nivel, accion);
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
