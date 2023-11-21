package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.ProductoController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.utils.ProductoUtil;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.*;

public class ProductoViewController {

    ProductoController productoControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    ObservableList<String> listaTipoProducto = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    ProductoDto productoSeleccionado;
    String foto;

    @FXML
    private ImageView imagen;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAgregarImagen;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private Label lblAnunciante;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTipoProducto;

    @FXML
    private Rectangle shapeFoto;

    @FXML
    private Rectangle shape;

    @FXML
    private Rectangle shapeDetalle;

    @FXML
    private ComboBox<String> cmbTipoProducto;

    @FXML
    private TableColumn<ProductoDto, String> colAnunciante;

    @FXML
    private TableColumn<ProductoDto, String> colCodigoUnico;

    @FXML
    private TableColumn<ProductoDto, String> colNombreProducto;

    @FXML
    private TableColumn<ProductoDto, String> colTipoProducto;

    @FXML
    private TableView<ProductoDto> tableProductos;

    @FXML
    private ComboBox<AnuncianteDto> cmbAnunciante;

    @FXML
    private TextField txfNombreProducto;

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
        seleccionarImagen();
    }


    @FXML
    void busquedaProducto(ActionEvent event) {
        String codigoUnico = txfCodigoUnico.getText();
        String nombreProducto = txfNombreProducto.getText();
        String nombreAnunciante = String.valueOf(cmbAnunciante.getValue());
        String tipoProducto = cmbTipoProducto.getValue();
        buscarProducto(codigoUnico, nombreProducto, nombreAnunciante, tipoProducto);
    }

    @FXML
    void limpiarBusqueda(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        productoControllerService = new ProductoController();
        intiView();
        cmbAnunciante.setValue(obtenerAnunciante(cedulaUsuario));
        cargarPestaniaSegunRol();
    }

    private void intiView() {
        initDataBinding();
        obtenerProductos();
        mostrarAnunciantes();
        mostrarTipoProducto();
        getListaAnunciantes();
        tableProductos.getItems().clear();
        tableProductos.setItems(listaProductosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCodigoUnico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigoUnico()));
        colNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreProducto()));
        colAnunciante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnunciante().toString()));
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

    private AnuncianteDto obtenerAnunciante(String cedula){
        return productoControllerService.obtenerAnunciante(cedula);
    }

    public void mostrarAnunciantes(){
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    public void recargarInformacion(){

        cmbAnunciante.getItems().clear();
        getListaAnunciantes();
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    private void cargarPestaniaSegunRol(){
        if(rolUsuarioLogeado.equals(String.valueOf(Rol.Comprador))){
            btnActualizar.setVisible(false);
            btnEliminar.setVisible(false);
            btnAgregar.setVisible(false);
            btnAgregarImagen.setVisible(false);
            btnBuscar.setVisible(false);
            btnLimpiarCampos.setVisible(false);
            cmbTipoProducto.setVisible(false);
            tableProductos.setVisible(false);
            txfCodigoUnico.setVisible(false);
            lblAnunciante.setVisible(false);
            lblCodigo.setVisible(false);
            lblNombre.setVisible(false);
            lblTipoProducto.setVisible(false);
            cmbAnunciante.setVisible(false);
            txfNombreProducto.setVisible(false);
            shapeFoto.setVisible(false);
            shape.setVisible(false);
            shapeDetalle.setVisible(false);
        }else{
            lblMensaje.setVisible(false);
        }
    }

    public void mostrarTipoProducto(){
        listaTipoProducto.add(String.valueOf(TipoProducto.TECNOLOGIA));
        listaTipoProducto.add(String.valueOf(TipoProducto.HOGAR));
        listaTipoProducto.add(String.valueOf(TipoProducto.DEPORTES));
        listaTipoProducto.add(String.valueOf(TipoProducto.VEHICULOS));
        listaTipoProducto.add(String.valueOf(TipoProducto.BIEN_RAIZ));
        cmbTipoProducto.setItems(listaTipoProducto);
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
            cmbTipoProducto.setValue(productoSeleccionado.tipoProducto());

            String rutaImagen = productoSeleccionado.foto();
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                File file = new File(rutaImagen);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imagen.setImage(image);
                } else {
                    System.out.println("La imagen no existe en la ruta especificada.");
                }
            } else {
                System.out.println("La ruta de la imagen está vacía o es nula.");
            }
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
                registrarAcciones("Producto agregado",1, "Agregar producto");
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
                cmbTipoProducto.getValue(),
                foto,
                String.valueOf(cmbAnunciante.getValue())
        );
    }

    private void limpiarCamposProductos() {
        txfCodigoUnico.setText("");
        txfNombreProducto.setText("");
        cmbAnunciante.setValue(null);
        cmbTipoProducto.setValue(null);
        imagen.setImage(null);
    }

    private void cancelarBusqueda(){
        limpiarCamposProductos();
        tableProductos.getSelectionModel().clearSelection();
        tableProductos.setItems(listaProductosDto);
        recargarInformacion();
        cmbAnunciante.setValue(obtenerAnunciante(cedulaUsuario));
        listenerSelection();
    }

    private void buscarProducto(String codigoUnico, String nombreProducto, String nombreAnunciante,
                                String tipoProducto) {

        Predicate<ProductoDto> predicado = ProductoUtil.buscarPorTodo(codigoUnico, nombreProducto, nombreAnunciante,
                tipoProducto);
        ObservableList<ProductoDto> productosFiltrados = listaProductosDto.filtered(predicado);
        tableProductos.setItems(productosFiltrados);
    }

    private void seleccionarImagen(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) { // Verificar si se seleccionó un archivo
            String rutaOrigen = file.getPath();
            String rutaDestino = "src/main/resources/co/edu/uniquindio/programacion3/subastaquindio/img/" + file.getName();

            try (InputStream inputStream = new FileInputStream(rutaOrigen);
                 OutputStream outputStream = new FileOutputStream(rutaDestino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                mostrarImagen(rutaDestino);
                foto = rutaDestino;
                System.out.println("Archivo copiado con éxito!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    private void mostrarImagen(String rutaImagen) {
        try {
            File file = new File(rutaImagen);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());

                imagen.setImage(image);
            } else {
                System.out.println("La imagen no existe en la ruta especificada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
