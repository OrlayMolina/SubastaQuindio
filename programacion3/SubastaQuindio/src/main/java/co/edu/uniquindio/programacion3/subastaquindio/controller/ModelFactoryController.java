package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.controller.service.IModelFactoryService;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.*;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers.SubastaMapper;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;
import co.edu.uniquindio.programacion3.subastaquindio.utils.Persistencia;
import co.edu.uniquindio.programacion3.subastaquindio.utils.SubastaUtils;

import java.io.IOException;
import java.util.List;


public class ModelFactoryController implements IModelFactoryService, Runnable {

    SubastaQuindio subasta;
    SubastaMapper mapper = SubastaMapper.INSTANCE;
    Anunciante anuncianteActual;
    Thread hiloServicio1_guardarResourceXml;
    Thread hiloServicio2_guardarRegistroLog;
    Thread hiloServicio3_guardarCopiaXml;
    BoundedSemaphore semaphore = new BoundedSemaphore(1);
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
    public List<PujaDto> obtenerPujas() {
        return  mapper.getPujaDto(subasta.getListaOfertas());
    }

    @Override
    public List<Chat> obtenerChats() {
        return  subasta.getListaMensajes();
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
