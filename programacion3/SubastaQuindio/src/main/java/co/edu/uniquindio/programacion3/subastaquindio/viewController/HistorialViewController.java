package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.HistorialController;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.PujaDto;
import co.edu.uniquindio.programacion3.subastaquindio.utils.PujaUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.Predicate;

public class HistorialViewController {

    HistorialController historialControllerService;
    ObservableList<PujaDto> listaPujasDto = FXCollections.observableArrayList();
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<CompradorDto> listaCompradoresDto = FXCollections.observableArrayList();
    CompradorDto compradorDto;
    ProductoDto productoDto;
    PujaDto pujaSeleccionada;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private ComboBox<CompradorDto> cmbComprador;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private ComboBox<ProductoDto> cmbProducto;

    @FXML
    private TableColumn<PujaDto, String> colCodigoAnuncio;

    @FXML
    private TableColumn<PujaDto, String> colComprador;

    @FXML
    private TableColumn<PujaDto, String> colEstado;

    @FXML
    private TableColumn<PujaDto, String> colProducto;

    @FXML
    private TableColumn<PujaDto, String> colPuja;

    @FXML
    private TableColumn<PujaDto, String> colValorOferta;

    @FXML
    private TableView<PujaDto> tableHistorial;

    @FXML
    private TextField txfCodigoAnuncio;

    @FXML
    private TextField txfCodigoPuja;

    @FXML
    private TextField txfValorOferta;

    @FXML
    void busquedaComprador(ActionEvent event) {
        String codigo = txfCodigoPuja.getText();
        String producto = String.valueOf(cmbProducto.getValue());
        String codigoAnuncio = txfCodigoAnuncio.getText();
        String comprador = String.valueOf(cmbComprador.getValue());
        Double valorPuja = Double.valueOf(txfValorOferta.getText());
        String estadoAnuncio = cmbEstado.getValue();
        buscarPujas( codigo, producto, codigoAnuncio,
                 comprador, valorPuja, estadoAnuncio);
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        historialControllerService = new HistorialController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerProductos();
        obtenerOfertas();
        mostrarCompradores();
        getListaCompradores();
        tableHistorial.getItems().clear();
        tableHistorial.setItems(listaPujasDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colPuja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductoDto().toString()));
        colCodigoAnuncio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnuncioDto().codigo()));
        colComprador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompradorDto().toString()));
        colValorOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoAnuncio()));
    }

    private void obtenerProductos() {
        listaProductosDto.addAll(historialControllerService.obtenerProductos());
    }

    private void obtenerOfertas() {
        listaPujasDto.addAll(historialControllerService.obtenerPujas());
    }

    /**
     *
     */
    private void listenerSelection() {
        tableHistorial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaSeleccionada = newSelection;
            mostrarInformacionPuja(pujaSeleccionada);
        });
    }

    public void mostrarCompradores(){
        cmbComprador.setItems(listaCompradoresDto);
    }

    /**
     *
     * @param pujaSeleccionada
     */
    private void mostrarInformacionPuja(PujaDto pujaSeleccionada) {
        if(pujaSeleccionada != null){
            txfCodigoPuja.setText(pujaSeleccionada.codigo());
            cmbProducto.setValue(pujaSeleccionada.getProductoDto());
            txfCodigoAnuncio.setText(pujaSeleccionada.anuncio());
            cmbComprador.setValue(pujaSeleccionada.getCompradorDto());
            txfValorOferta.setText(String.valueOf(pujaSeleccionada.oferta()));
            cmbEstado.setValue(pujaSeleccionada.estadoAnuncio());
        }
    }

    private void limpiarCamposPuja() {
        txfCodigoPuja.setText("");
        cmbProducto.setValue(null);
        txfCodigoAnuncio.setText("");
        cmbComprador.setValue(null);
        txfValorOferta.setText("");
        cmbEstado.setValue(null);
    }

    private void cancelarBusqueda(){
        limpiarCamposPuja();
        tableHistorial.getSelectionModel().clearSelection();
        tableHistorial.setItems(listaPujasDto);
        listenerSelection();
    }

    private void buscarPujas(String codigo, String producto, String codigoAnuncio,
                             String comprador, Double valorPuja, String estadoAnuncio) {

        Predicate<PujaDto> predicado = PujaUtil.buscarPorTodo(codigo, producto, codigoAnuncio,
                comprador, valorPuja, estadoAnuncio);
        ObservableList<PujaDto> pujasFiltradas = listaPujasDto.filtered(predicado);
        tableHistorial.setItems(pujasFiltradas);
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        historialControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    public ObservableList<CompradorDto> getListaCompradores() {
        listaCompradoresDto.addAll(historialControllerService.obtenerCompradores());
        return listaCompradoresDto;
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
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

}
