package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.ProductoController;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class ProductoViewController {

    ProductoController productoControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    ProductoDto productoSeleccionado;

    @FXML
    private Rectangle ImageView;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private TextField txfTipoProducto;

    @FXML
    private TableColumn<ProductoDto, String> colAnunciante;

    @FXML
    private TableColumn<ProductoDto, String> colDescripcion;

    @FXML
    private TableColumn<ProductoDto, String> colFechaFinPuja;

    @FXML
    private TableColumn<ProductoDto, String> colCodigoUnico;

    @FXML
    private TableColumn<ProductoDto, String> colFechaPublicacion;

    @FXML
    private TableColumn<ProductoDto, String> colNombreProducto;

    @FXML
    private TableColumn<ProductoDto, String> colTipoProducto;

    @FXML
    private TableColumn<ProductoDto, String> colValorInicial;

    @FXML
    private TextField txfFechaFinPublicacion;

    @FXML
    private TextField txfFechaPublicacion;

    @FXML
    private TableView<ProductoDto> tableProductos;

    @FXML
    private TextArea txaDescripcion;

    @FXML
    private ComboBox<AnuncianteDto> cmbAnunciante;

    @FXML
    private TextField txfNombreProducto;

    @FXML
    private TextField txfValorInicial;

    @FXML
    private TextField txfCodigoUnico;

    @FXML
    void actualizarProducto(ActionEvent event) {
        actualizarProducto();
    }

    @FXML
    void agregarProducto(ActionEvent event) {
        crearProducto();
    }

    @FXML
    void eliminarProducto(ActionEvent event) {
        eliminarProducto();
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {

    }

    @FXML
    void busquedaProducto(ActionEvent event) {

    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {

    }

    @FXML
    void initialize() {
        productoControllerService = new ProductoController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerProductos();
        mostrarAnunciantes();
        getListaAnunciantes();
        tableProductos.getItems().clear();
        tableProductos.setItems(listaProductosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCodigoUnico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigoUnico()));
        colNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreProducto()));
        colAnunciante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnunciante().toString()));
        colValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        colFechaPublicacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaPublicacion()));
        colFechaFinPuja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaFinPublicacion()));
        colTipoProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoProducto()));
    }

    private void obtenerProductos() {
        listaProductosDto.addAll(productoControllerService.obtenerProductos());
    }

    /**
     *
     */
    private void listenerSelection() {
        tableProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
        });
    }

    public void mostrarAnunciantes(){
        listaAnunciantesDto.add(anuncianteDto);
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    /**
     *
     * @param productoSeleccionado
     */
    private void mostrarInformacionProducto(ProductoDto productoSeleccionado) {
        if(productoSeleccionado != null){
            txfCodigoUnico.setText(productoSeleccionado.codigoUnico());
            txfNombreProducto.setText(productoSeleccionado.nombreProducto());
            cmbAnunciante.setValue(productoSeleccionado.getAnunciante());
            txaDescripcion.setText(productoSeleccionado.descripcion());
            txfValorInicial.setText(String.valueOf(productoSeleccionado.valorInicial()));
            txfFechaPublicacion.setText(productoSeleccionado.fechaPublicacion());
            txfFechaFinPublicacion.setText(productoSeleccionado.fechaFinPublicacion());
            txfTipoProducto.setText(productoSeleccionado.tipoProducto());
        }
    }



    private void crearProducto() {
        //1. Capturar los datos
        ProductoDto productoDto = construirProductoDto();
        //2. Validar la información
        if(datosValidos(productoDto)){
            if(productoControllerService.agregarProducto(productoDto)){
                listaProductosDto.add(productoDto);
                mostrarMensaje("Notificación producto", "Producto creado", "El producto se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposProductos();
                registrarAcciones("Producto agregado",1, "Agregar empleado");
            }else{
                mostrarMensaje("Notificación producto", "Producto no creado", "El producto no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación producto", "Producto no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarProducto() {
        boolean productoEliminado = false;
        if(productoSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al producto?")){
                productoEliminado = productoControllerService.eliminarProducto(productoSeleccionado.codigoUnico());
                if(productoEliminado == true){
                    listaProductosDto.remove(productoSeleccionado);
                    productoSeleccionado = null;
                    tableProductos.getSelectionModel().clearSelection();
                    limpiarCamposProductos();
                    registrarAcciones("Producto eliminado",1, "Eliminar producto");
                    mostrarMensaje("Notificación producto", "Producto eliminado", "El producto se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación producto", "Producto no eliminado", "El producto no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación producto", "Producto no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarProducto() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String codigoUnico = productoSeleccionado.codigoUnico();
        ProductoDto productoDto = construirProductoDto();
        //2. verificar el empleado seleccionado
        if(productoSeleccionado != null){
            //3. Validar la información
            if(datosValidos(productoSeleccionado)){
                clienteActualizado = productoControllerService.actualizarProducto(codigoUnico, productoDto);
                if(clienteActualizado){
                    listaProductosDto.remove(productoSeleccionado);
                    listaProductosDto.add(productoDto);
                    tableProductos.refresh();
                    mostrarMensaje("Notificación producto", "Producto actualizado", "El producto se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposProductos();
                    registrarAcciones("Producto actualizado",1, "Actualizar producto");
                }else{
                    mostrarMensaje("Notificación producto", "Producto no actualizado", "El producto no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    registrarAcciones("Producto no actualizado",1, "Actualizar producto");
                }
            }else{
                mostrarMensaje("Notificación producto", "Producto no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private ProductoDto construirProductoDto() {
        return new ProductoDto(
                txfCodigoUnico.getText(),
                txfNombreProducto.getText(),
                txaDescripcion.getText(),
                txfTipoProducto.getText(),
                "",
                String.valueOf(cmbAnunciante.getValue()),
                txfFechaPublicacion.getText(),
                txfFechaFinPublicacion.getText(),
                Double.valueOf(txfValorInicial.getText())

        );
    }

    private void limpiarCamposProductos() {
        txfCodigoUnico.setText("");
        txfNombreProducto.setText("");
        cmbAnunciante.setValue(null);
        txfValorInicial.setText("");
        txaDescripcion.setText("");
        txfFechaPublicacion.setText("");
        txfFechaFinPublicacion.setText("");
        txfTipoProducto.setText("");
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        productoControllerService.registrarAcciones(mensaje, nivel, accion);
    }

    private boolean datosValidos(ProductoDto productoDto) {
        String mensaje = "";
        if(productoDto.nombreProducto() == null || productoDto.nombreProducto().equals(""))
            mensaje += "El nombre del Producto es invalido \n" ;
        if(productoDto.nombreAnunciante() == null || productoDto.nombreAnunciante() .equals(""))
            mensaje += "El nombre del anunciante es invalido \n" ;
        if(productoDto.codigoUnico() == null || productoDto.codigoUnico() .equals(""))
            mensaje += "El código único del anunciante es invalido \n" ;
        if(productoDto.valorInicial() == null)
            mensaje += "El valor inicial de la puja es invalido \n" ;
        if(productoDto.descripcion() == null || productoDto.descripcion().equals(""))
            mensaje += "El descripcion de la puja es invalido \n" ;
        if(productoDto.fechaPublicacion() == null || productoDto.fechaPublicacion().equals(""))
            mensaje += "La fecha de publicación es invalida \n" ;
        if(productoDto.fechaFinPublicacion() == null || productoDto.fechaFinPublicacion().equals(""))
            mensaje += "La fecha fin de publicación es invalida \n" ;
        if(productoDto.tipoProducto() == null || productoDto.tipoProducto().equals(""))
            mensaje += "El tipo de producto es invalida \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación producto", "Producto no creado", mensaje, Alert.AlertType.ERROR);
            return false;
        }
    }

    public ObservableList<AnuncianteDto> getListaAnunciantes() {
        listaAnunciantesDto.addAll(productoControllerService.obtenerAnunciantes());
        return listaAnunciantesDto;
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
