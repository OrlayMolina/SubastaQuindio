package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.config.RabbitFactory;
import co.edu.uniquindio.programacion3.subastaquindio.controller.service.IModelFactoryService;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.*;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers.SubastaMapper;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;
import co.edu.uniquindio.programacion3.subastaquindio.utils.Persistencia;
import co.edu.uniquindio.programacion3.subastaquindio.utils.SubastaUtils;
import co.edu.uniquindio.programacion3.subastaquindio.viewController.PujaViewController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static co.edu.uniquindio.programacion3.subastaquindio.utils.Constantes.QUEUE_NUEV0_MENSAJE;


public class ModelFactoryController implements IModelFactoryService, Runnable {

    SubastaQuindio subasta;
    SubastaMapper mapper = SubastaMapper.INSTANCE;
    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    Thread hiloServicio1_guardarResourceXml;
    Thread hiloServicio2_guardarRegistroLog;
    Thread hiloServicio3_guardarCopiaXml;
    Thread hiloServicio4_nuevoMensajeConsumer;
    BoundedSemaphore semaphore = new BoundedSemaphore(1);
    PujaViewController pujaView = new PujaViewController();
    String mensaje;
    int nivel;
    String accion;

    @Override
    public void run() {
        Thread hiloActual = Thread.currentThread();
        ocuparSemaforo();
        if(hiloActual == hiloServicio1_guardarResourceXml){
            Persistencia.guardarRecursoSubastaXML(subasta);
            liberarSemaforo();
        }

        if(hiloActual == hiloServicio2_guardarRegistroLog){
            Persistencia.guardaRegistroLog(mensaje, nivel, accion);
            liberarSemaforo();

        }

        if(hiloActual == hiloServicio3_guardarCopiaXml){
            Persistencia.copiarArchivoRespaldoXml();
            liberarSemaforo();

        }
        if(hiloActual == hiloServicio4_nuevoMensajeConsumer){
            consumirMensajes();
        }

    }

    private void ocuparSemaforo() {
        try {
            semaphore.ocupar();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void liberarSemaforo() {
        try {
            semaphore.liberar();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        //1. inicializar datos y luego guardarlo en archivos
        System.out.println("invocación clase singleton");
        initRabbitConnection();

        //cargarDatosBase();
        //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
        //cargarDatosDesdeArchivos();

        //3. Guardar y Cargar el recurso serializable binario
        //cargarResourceBinario();
        //guardarResourceBinario();

        //4. Guardar y Cargar el recurso serializable XML

        //guardarResourceXML();
        //guardarRespaldoXML();
        cargarResourceXML();
        guardarRespaldosXml();

        //Siempre se debe verificar si la raiz del recurso es null

        if(subasta == null){
            cargarDatosBase();
            guardarResourceXML();
            guardarRespaldosXml();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");

    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecida");
    }

    public void consumirMensajesServicio4(){
        hiloServicio4_nuevoMensajeConsumer = new Thread(this);
        hiloServicio4_nuevoMensajeConsumer.start();
    }

    public boolean actualizarTiempoRestante(String codigo) {
        AnuncioDto anuncioDto;
        Anuncio anuncio = obtenerAnuncio(codigo);

        if (anuncio != null) {
            anuncioDto = mapper.anuncioToAnuncioDto(anuncio);
            return getSubasta().verificarHoraFin(anuncioDto.fechaFinPublicacion());
        } else {
            pujaView.mostrarMensaje("Notificación puja", "Puja no creada", "No se pudo encontrar la fecha fin del anuncio", Alert.AlertType.ERROR);

            return false;
        }
    }


    public boolean validarValorPuja(String codigo, Double puja){
        return getSubasta().validarValorPuja(codigo, puja);
    }

    private void consumirMensajes() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NUEV0_MENSAJE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);
                //actualizarEstado(message);
            };
            while (true) {
                channel.basicConsume(QUEUE_NUEV0_MENSAJE, true, deliverCallback, consumerTag -> { });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosDesdeArchivos() {
        subasta = new SubastaQuindio();
        try {
            Persistencia.cargarDatosArchivos(subasta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarRespaldosXml(){
        hiloServicio3_guardarCopiaXml = new Thread(this);
        hiloServicio3_guardarCopiaXml.start();
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion){
        this.mensaje=mensaje;
        this.nivel=nivel;
        this.accion=accion;

        hiloServicio2_guardarRegistroLog = new Thread(this);
        hiloServicio2_guardarRegistroLog.start();
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarProducto(getSubasta().getListaProductos());
            //Persistencia.guardarAnunciante(getSubasta().getListaAnunciantes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
    }

    public SubastaQuindio getSubasta() {
        return subasta;
    }

    public void setSubasta(SubastaQuindio subasta) {
        this.subasta = subasta;
    }


    @Override
    public List<ProductoDto> obtenerProductos() {
        return  mapper.getProductoDto(subasta.getListaProductos());
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return  mapper.getUsuarioDto(subasta.getListaUsuarios());
    }

    @Override
    public CompradorDto obtenerComprador(String nombre) {
        return  mapper.compradorToCompradorDto(getSubasta().obtenerCompradorPorUsuario(nombre));
    }

    @Override
    public String obtenerProducto(String nombre) {
        Producto producto = getSubasta().obtenerProducto(nombre);

        if (producto != null) {
            ProductoDto productoDto = mapper.productoToProductoDto(producto);
            if (productoDto != null) {
                return productoDto.foto();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public Anuncio obtenerAnuncio(String codigo) {
        Anuncio anuncio = null;

        anuncio = getSubasta().obtenerAnuncio(codigo);

        return anuncio;
    }


    @Override
    public List<AnuncianteDto> obtenerAnunciantes() {
        return  mapper.getAnuncianteDto(subasta.getListaAnunciantes());
    }

    @Override
    public List<CompradorDto> obtenerCompradores() {
        return  mapper.getCompradorDto(subasta.getListaCompradores());
    }

    @Override
    public List<AnuncioDto> obtenerAnuncios() {
        return  mapper.getAnuncioDto(subasta.getListaAnuncios());
    }

    @Override
    public String obtenerEstadoAnuncio(String codigo) {
        return getSubasta().obtenerEstadoAnuncio(codigo);
    }

    @Override
    public List<PujaDto> obtenerPujas() {
        return  mapper.getPujaDto(subasta.getListaPujas());
    }

    @Override
    public List<ChatDto> obtenerChats() {
        //consumirMensajesServicio4();
        //guardarResourceXML();
        return  mapper.getChatDto(subasta.getListaMensajes());
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto) {
        try{
            if(!subasta.verificarProductoExistente(productoDto.codigoUnico())) {
                Producto producto = mapper.productoDtoToProducto(productoDto);
                getSubasta().agregarProducto(producto);
                guardarResourceXML();
            }
            return true;
        }catch (ProductoException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(String codigoUnico) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarProducto(codigoUnico);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
        } catch (ProductoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarProducto(String codigoActual, ProductoDto productoDto) {
        try {
            Producto producto = mapper.productoDtoToProducto(productoDto);
            getSubasta().actualizarProducto(codigoActual, producto);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
            return true;
        } catch (ProductoException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try{
            if(!subasta.verificarUsuarioExistente(usuarioDto.usuario())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                getSubasta().agregarUsuario(usuario);
                guardarResourceXML();
            }
            return true;
        }catch (UsuarioException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String nombreUsuario) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarUsuario(nombreUsuario);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
        } catch (UsuarioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarUsuario(String nombreUsuario, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            getSubasta().actualizarUsuario(nombreUsuario, usuario);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
            return true;
        } catch (UsuarioException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarPuja(String codigo, PujaDto pujaDto) {
        try {
            Puja puja = mapper.pujaDtoToPuja(pujaDto);
            getSubasta().actualizarPuja(codigo, puja);
            guardarResourceXML();
            return true;
        } catch (PujaException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarAnunciante(AnuncianteDto anuncianteDto) {
        try{
            if(!subasta.verificarAnuncianteExistente(anuncianteDto.cedula())) {
                Anunciante anunciante = mapper.anuncianteDtoToAnunciante(anuncianteDto);
                getSubasta().agregarAnunciante(anunciante);
                guardarResourceXML();
            }
            return true;
        }catch (AnuncianteException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean agregarAnuncio(AnuncioDto anuncioDto) {
        try{
            if(!subasta.verificarAnuncioExistente(anuncioDto.codigo())) {
                Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
                getSubasta().agregarAnuncio(anuncio);
                guardarResourceXML();
            }
            return true;
        }catch (AnuncioException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean agregarPuja(PujaDto pujaDto) {
        try{
            Puja puja = mapper.pujaDtoToPuja(pujaDto);
            getSubasta().agregarPuja(puja);
            guardarResourceXML();
            return true;
        }catch (PujaException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarAnuncio(String codigo) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarAnuncio(codigo);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
        } catch (AnuncioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarAnuncio(String codigoActual, AnuncioDto anuncioDto) {
        try {
            Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
            getSubasta().actualizarAnuncio(codigoActual, anuncio);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
            return true;
        } catch (AnuncioException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validarEdadAnunciante(AnuncianteDto anuncianteDto){
        try {
            Anunciante anunciante = mapper.anuncianteDtoToAnunciante(anuncianteDto);
            getSubasta().esMayor(anunciante);
            return true;

        }catch(PersonaException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean validarEdadComprador(CompradorDto compradorDto){
        try {
            Comprador comprador = mapper.compradorDtoToComprador(compradorDto);
            getSubasta().esMayor(comprador);
            return true;

        }catch(PersonaException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarAnunciante(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarAnunciante(cedula);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
        } catch (AnuncianteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarAnunciante(String cedula, AnuncianteDto anuncianteDto) {
        try {
            Anunciante anunciante = mapper.anuncianteDtoToAnunciante(anuncianteDto);
            getSubasta().actualizarAnunciante(cedula, anunciante);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
            return true;
        } catch (AnuncianteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarComprador(CompradorDto compradorDto) {
        try{
            if(!subasta.verificarCompradorExistente(compradorDto.cedula())) {
                Comprador comprador = mapper.compradorDtoToComprador(compradorDto);
                getSubasta().agregarComprador(comprador);
                guardarResourceXML();
            }
            return true;
        }catch (CompradorException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarComprador(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarComprador(cedula);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
        } catch (CompradorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarComprador(String cedulaActual, CompradorDto compradorDto) {
        try {
            Comprador comprador = mapper.compradorDtoToComprador(compradorDto);
            getSubasta().actualizarComprador(cedulaActual, comprador);
            guardarResourceXML(); //Pendiente verificar si este método es adecuado
            return true;
        } catch (CompradorException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void iniciarChat(String texto) {
        getSubasta().iniciarChat(texto);
        guardarResourceXML();
        producirMensaje(QUEUE_NUEV0_MENSAJE, texto);
    }

    @Override
    public boolean inicioSesion(String usuario, String password) {
        return getSubasta().inicioSesion(usuario, password);
    }

    private void cargarResourceXML() {
        subasta = Persistencia.cargarRecursoSubastaXML();
    }

    private void guardarResourceXML() {
        hiloServicio1_guardarResourceXml = new Thread(this);
        hiloServicio1_guardarResourceXml.start();
    }


    private void cargarResourceBinario() {
        subasta = Persistencia.cargarRecursoSubastaBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoSubastaBinario(subasta);
    }

}
