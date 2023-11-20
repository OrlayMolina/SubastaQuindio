package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.PujaController;
import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.AnuncioException;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.ListaAnuncioUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.usuarioLogeado;

public class PujaViewController extends Frame {

    PujaController pujaControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ObservableList<AnuncianteDto> listaAnunciantesDto = FXCollections.observableArrayList();
    ObservableList<PujaDto> listPujaDto = FXCollections.observableArrayList();
    ObservableList<String> listaTipoProducto = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnunciosDto = FXCollections.observableArrayList();
    AnuncianteDto anuncianteDto;
    CompradorDto compradorDto;
    ProductoDto productoDto;
    AnuncioDto anuncioDto;
    SubastaQuindio subastaQuindio;
    AnuncioDto anuncioSeleccionado;
    PujaDto ofertaSeleccionada;
    String foto;

    public static boolean dato;

    @FXML
    private Button btnHacerOferta;

    @FXML
    private ImageView imagen;

    @FXML
    private ComboBox<AnuncianteDto> cmbAnunciante;

    @FXML
    private TextArea txaDescripcion;

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
        String descripcion = txaDescripcion.getText();
        buscarListaAnuncio(codigo, descripcion);

        String producto = String.valueOf(cmbProducto.getValue());
        String traerImagen = producto.split("  ")[0];
        foto = pujaControllerService.obtenerProducto(traerImagen);
        mostrarImagen(foto);
    }

    @FXML
    void cancelarFiltro(ActionEvent event) {
        cancelarBusqueda();
    }
    @FXML
    void hacerOferta(ActionEvent event) {
        crearOferta();
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
        tableOfertas.setItems(listPujaDto);
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

    private void crearOferta(){

        PujaDto pujaDto = construirPujaDto();
        dato = pujaControllerService.actualizarTiempoRestante(pujaDto.anuncio());
        boolean valorPermitido = pujaControllerService.validarValorPuja(pujaDto.anuncio(), pujaDto.oferta());
        if(datosValidos(pujaDto)){
            if(valorPermitido){
                if(dato){
                    if(mostrarMensajeConfirmacion("¿Estas seguro que desea realizar una Puja por este producto?")){
                        if(pujaControllerService.agregarPuja(pujaDto)){
                            listPujaDto.add(pujaDto);
                            mostrarMensaje("Notificación puja", "Puja creada", "El puja se ha creado con éxito", Alert.AlertType.INFORMATION);

                            registrarAcciones("Puja agregado",1, "Agregar puja");
                        }else{
                            mostrarMensaje("Notificación puja", "Puja no creada", "El puja no se ha creado", Alert.AlertType.ERROR);
                        }
                    }else{
                        mostrarMensaje("Notificación puja", "Puja no seleccionado", "No fue posible realizar la Puja", Alert.AlertType.WARNING);
                    }

                }else {
                    mostrarMensaje("Notificación puja", "Anuncio Finalizado", "El Anuncio ha finalizado, no se puede pujar por este producto", Alert.AlertType.ERROR);
                }
            }

        }

    }


    public void mostrarProducto(){
        cmbProducto.setItems(listaProductosDto);
    }

    public void mostrarAnunciantes(){
        cmbAnunciante.setItems(listaAnunciantesDto);
    }

    private void buscarListaAnuncio(String codigo, String descripcion) {

        Predicate<AnuncioDto> predicado = ListaAnuncioUtil.buscarPorTodo(codigo, descripcion);
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
        listPujaDto.addAll(pujaControllerService.obtenerPujas());
    }

    private PujaDto construirPujaDto() {
        UUID uuid = UUID.randomUUID();
        String codigoPuja = uuid.toString();
        String producto = String.valueOf(cmbProducto.getValue());
        String codigoAnuncio = txfCodigoAnuncio.getText();
        String comprador = obtenerUsuarioComprador();
        Double oferta;

        String textoOferta = txfOferta.getText();

        if (textoOferta == null || textoOferta.isEmpty()) {
            oferta = 0.0;
        } else {
            oferta = Double.valueOf(textoOferta);
        }

        String estadoAnuncio = obtenerEstadoAnuncio();
        return new PujaDto(codigoPuja, producto, codigoAnuncio, comprador, oferta, estadoAnuncio);
    }


    private String obtenerEstadoAnuncio(){
        return pujaControllerService.obtenerEstadoAnuncio(txfCodigoAnuncio.getText());
    }

    private String obtenerUsuarioComprador(){
        CompradorDto compradorDto = pujaControllerService.obtenerComprador(usuarioLogeado);
        if(compradorDto != null){
            if(compradorDto.rol().equals(String.valueOf(Rol.Comprador))){
                return compradorDto.cedula() + "  " + compradorDto.nombre() + " " + compradorDto.apellido();
            }else{
                mostrarMensaje("Notificación puja", "Puja no creada", "El usuario debe tener el rol 'Comprador' para hacer una Puja.", Alert.AlertType.ERROR);
                return "";
            }

        }
        mostrarMensaje("Notificación puja", "Puja no creada", "El usuario debe tener el rol 'Comprador' para hacer una Puja.", Alert.AlertType.ERROR);
        return "";
    }


    private boolean datosValidos(PujaDto pujaDto) {
        String mensaje = "";
        if(pujaDto.codigo() == null || pujaDto.codigo().equals(""))
            mensaje += "El código de la puja es invalido \n" ;
        if(pujaDto.producto() == null || pujaDto.producto() .equals(""))
            mensaje += "El producto de la puja es invalido \n" ;
        if(pujaDto.anuncio()== null || pujaDto.anuncio() .equals(""))
            mensaje += "El código del anuncio que desea pujar está vació \n" ;
        if(pujaDto.comprador() == null || pujaDto.comprador().equals(""))
            mensaje += "El comprador de la puja es invalida \n" ;
        if(pujaDto.oferta() == 0.0 )
            mensaje += "La oferta de la puja es invalida, debe agregar un valor de oferta \n" ;
        if(pujaDto.estadoAnuncio() == null || pujaDto.estadoAnuncio().equals(""))
            mensaje += "El estado del anuncio de la puja es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación puja", "Puja no creada", mensaje, Alert.AlertType.ERROR);
            return false;
        }
    }

    private void mostrarInformacionAnuncio(AnuncioDto anuncioSeleccionado) {
        if(anuncioSeleccionado != null){
            txfCodigoAnuncio.setText(anuncioSeleccionado.codigo());
            txaDescripcion.setText(anuncioSeleccionado.descripcion());
            cmbProducto.setValue(anuncioSeleccionado.getProductoDto());
            cmbAnunciante.setValue(anuncioSeleccionado.getAnuncianteDto());
            txfFechaFinPuja.setText(anuncioSeleccionado.fechaPublicacion());
            txfValorInicial.setText(String.valueOf(anuncioSeleccionado.valorInicial()));

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



    private void limpiarCamposAnuncios() {
        txfCodigoAnuncio.setText("");
        txaDescripcion.setText("");
        cmbProducto.setValue(null);
        cmbAnunciante.setValue(null);
        txfFechaFinPuja.setText("");
        txfValorInicial.setText("");
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

    public void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


}
