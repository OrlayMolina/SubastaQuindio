package co.edu.uniquindio.programacion3.subastaquindio.model;

import co.edu.uniquindio.programacion3.subastaquindio.exceptions.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.service.ISubastaQuindioService;
import co.edu.uniquindio.programacion3.subastaquindio.viewController.PujaViewController;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;

import static co.edu.uniquindio.programacion3.subastaquindio.viewController.InicioViewController.*;

public class SubastaQuindio implements ISubastaQuindioService, Serializable {

    private static final long serialVersionUID = 1L;
    private PujaViewController pujaView = new PujaViewController();
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Anunciante> listaAnunciantes = new ArrayList<>();
    private ArrayList<Comprador> listaCompradores = new ArrayList<>();
    private ArrayList<Anuncio> listaAnuncios = new ArrayList<>();
    private ArrayList<Puja> listaPujas = new ArrayList<>();
    private ArrayList<Chat> listaMensajes = new ArrayList<>();
    public static String usuarioChat = "";

    public SubastaQuindio() {

    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Anunciante> getListaAnunciantes() {
        return listaAnunciantes;
    }

    public void setListaAnunciantes(ArrayList<Anunciante> listaAnunciantes) {
        this.listaAnunciantes = listaAnunciantes;
    }

    public ArrayList<Comprador> getListaCompradores() {
        return listaCompradores;
    }

    public void setListaCompradores(ArrayList<Comprador> listaCompradores) {
        this.listaCompradores = listaCompradores;
    }
    public ArrayList<Anuncio> getListaAnuncios() {
        return listaAnuncios;
    }

    public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;
    }

    public ArrayList<Puja> getListaPujas() {
        return listaPujas;
    }

    public void setListaPujas(ArrayList<Puja> listaPujas) {
        this.listaPujas = listaPujas;
    }

    public ArrayList<Chat> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(ArrayList<Chat> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }



    public boolean inicioSesion(String usuario, String password){
        boolean encontrado = usuarioExiste(usuario, password);
        return encontrado;
    }

    public void iniciarChat(String texto) {
        Chat chatAnunciantes = new Chat();
        chatAnunciantes.setMiChat(texto);
        getListaMensajes().add(chatAnunciantes);
    }

    public boolean esMayor(Persona persona) throws PersonaException {
        boolean mayor = false;
        LocalDate fechaNacimiento = LocalDate.parse(persona.getFechaNacimiento());
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        int edad = periodo.getYears();

        int edadMinima = 18;

        if (edad >= edadMinima) {
            mayor = true;
        } else {
            throw new PersonaException("No se puede crear el registro porque es menor de edad");
        }
        return mayor;
    }

    /**
     * Función para que se encarga de cambiar el estado del anuncio a Finalizado
     * @param hora
     * @return
     */
    public boolean verificarHoraFin(String hora) {

        LocalTime horaActual = LocalTime.now();
        LocalTime horaFin = LocalTime.parse(hora);

        return !horaActual.isAfter(horaFin);
    }

    public boolean validarValorPuja(String codigoAnuncio, String puja) {
        boolean respuesta = false;
        if(!valorMenorPujado(codigoAnuncio, puja)){
            return respuesta;
        }else {
            respuesta = true;
        }
        return respuesta;
    }

    public boolean valorMenorPujado(String codigoAnuncio, String puja) {
        boolean respuesta = false;
        Anuncio anuncio = obtenerAnuncio(codigoAnuncio);
        if(anuncio != null){
            if (Double.parseDouble(anuncio.getValorInicial()) < Double.parseDouble(puja)) {
                respuesta = true;
            }
        }

        return respuesta;
    }


    @Override
    public Producto crearProducto(String codigoUnico, String nombreProducto, String tipoProducto,
                                  String foto, String nombreAnunciante) throws ProductoException{
        Producto nuevoProducto = null;
        boolean productoExiste = verificarProductoExistente(codigoUnico);
        if(productoExiste){
            throw new ProductoException("El producto con codigo unico: "+codigoUnico+" ya existe");
        }else{
            nuevoProducto = new Producto();
            nuevoProducto.setCodigoUnico(codigoUnico);
            nuevoProducto.setNombreProducto(nombreProducto);
            nuevoProducto.setTipoProducto(tipoProducto);
            nuevoProducto.setFoto(foto);
            nuevoProducto.setNombreAnunciante(nombreAnunciante);
            getListaProductos().add(nuevoProducto);
        }
        return nuevoProducto;
    }

    @Override
    public Boolean eliminarProducto(String codigoUnico) throws ProductoException {
        Producto producto = null;
        boolean flagExiste = false;
        producto = obtenerProducto(codigoUnico);
        if(producto == null)
            throw new ProductoException("El producto a eliminar no existe");
        else{
            getListaProductos().remove(producto);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public Boolean eliminarUsuario(String nombreUsuario) throws UsuarioException {
        Usuario usuario = null;
        boolean flagExiste = false;
        usuario = obtenerUsuario(nombreUsuario);
        if(usuario == null)
            throw new UsuarioException("El usuario a eliminar no existe");
        else{
            getListaUsuarios().remove(usuario);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public Boolean eliminarAnunciante(String cedula) throws AnuncianteException {
        Anunciante anunciante = null;
        boolean flagExiste = false;
        anunciante = obtenerAnunciante(cedula);
        if(anunciante == null)
            throw new AnuncianteException("El anunciante a eliminar no existe");
        else{
            getListaAnunciantes().remove(anunciante);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public Boolean eliminarComprador(String cedula) throws CompradorException {
        Comprador comprador = null;
        boolean flagExiste = false;
        comprador = obtenerComprador(cedula);
        if(comprador == null)
            throw new CompradorException("El comprador a eliminar no existe");
        else{
            getListaCompradores().remove(comprador);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public Boolean eliminarAnuncio(String cedula) throws AnuncioException {
        Anuncio anuncio = null;
        boolean flagExiste = false;
        anuncio = obtenerAnuncio(cedula);
        if(anuncio == null)
            throw new AnuncioException("El anuncio a eliminar no existe");
        else{
            getListaAnuncios().remove(anuncio);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarProducto(String codigoUnico, Producto producto) throws ProductoException {
        Producto productoActual = obtenerProducto(codigoUnico);
        if(productoActual == null)
            throw new ProductoException("El producto a actualizar no existe");
        else{
            productoActual.setCodigoUnico(producto.getCodigoUnico());
            productoActual.setNombreProducto(producto.getNombreProducto());
            productoActual.setTipoProducto(producto.getTipoProducto());
            productoActual.setFoto(producto.getFoto());
            productoActual.setNombreAnunciante(producto.getNombreAnunciante());
            return true;
        }
    }

    @Override
    public boolean actualizarUsuario(String nombreUsuario, Usuario usuario) throws UsuarioException {
        Usuario usuarioActual = obtenerUsuario(nombreUsuario);
        if(usuarioActual == null)
            throw new UsuarioException("El usuario a actualizar no existe");
        else{
            usuarioActual.setUsuario(usuario.getUsuario());
            usuarioActual.setContrasenia(usuario.getContrasenia());

            return true;
        }
    }

    @Override
    public boolean actualizarAnunciante(String cedula, Anunciante anunciante) throws AnuncianteException {
        Anunciante anuncianteActual = obtenerAnunciante(cedula);
        if(anuncianteActual == null)
            throw new AnuncianteException("El anunciante a actualizar no existe");
        else{
            anuncianteActual.setCedula(anunciante.getCedula());
            anuncianteActual.setNombre(anunciante.getNombre());
            anuncianteActual.setApellido(anunciante.getApellido());
            anuncianteActual.setTelefono(anunciante.getTelefono());
            anuncianteActual.setContrasenia(anunciante.getContrasenia());
            anuncianteActual.setCorreo(anunciante.getCorreo());
            anuncianteActual.setDireccion(anunciante.getDireccion());
            anuncianteActual.setFechaNacimiento(anunciante.getFechaNacimiento());
            anuncianteActual.setUsuarioAsociado(anunciante.getUsuarioAsociado());
            return true;
        }
    }

    @Override
    public boolean actualizarComprador(String cedula, Comprador comprador) throws CompradorException {
        Comprador compradorActual = obtenerComprador(cedula);
        if(compradorActual == null)
            throw new CompradorException("El comprador a actualizar no existe");
        else{
            compradorActual.setCedula(comprador.getCedula());
            compradorActual.setNombre(comprador.getNombre());
            compradorActual.setApellido(comprador.getApellido());
            compradorActual.setTelefono(comprador.getTelefono());
            compradorActual.setContrasenia(comprador.getContrasenia());
            compradorActual.setCorreo(comprador.getCorreo());
            compradorActual.setDireccion(comprador.getDireccion());
            compradorActual.setFechaNacimiento(comprador.getFechaNacimiento());
            compradorActual.setUsuarioAsociado(comprador.getUsuarioAsociado());
            return true;
        }
    }

    @Override
    public boolean actualizarAnuncio(String codigo, Anuncio anuncio) throws AnuncioException {
        Anuncio anuncioActual = obtenerAnuncio(codigo);
        if(anuncioActual == null)
            throw new AnuncioException("El anuncio a actualizar no existe");
        else{
            anuncioActual.setCodigo(anuncio.getCodigo());
            anuncioActual.setProducto(anuncio.getProducto());
            anuncioActual.setAnunciante(anuncio.getAnunciante());
            anuncioActual.setFechaPublicacion(anuncio.getFechaPublicacion());
            anuncioActual.setFechaFinPublicacion(anuncio.getFechaFinPublicacion());
            anuncioActual.setValorInicial(anuncio.getValorInicial());
            anuncioActual.setDescripcion(anuncio.getDescripcion());
            anuncioActual.setEstado(anuncio.getEstado());
            return true;
        }
    }

    @Override
    public boolean actualizarPuja(String codigo, Puja puja) throws PujaException {
        Puja pujaActual = obtenerPuja(codigo);
        if(pujaActual == null)
            throw new PujaException("No se pudo actualizar la puja");
        else{
            pujaActual.setCodigo(puja.getCodigo());
            pujaActual.setProducto(puja.getProducto());
            pujaActual.setAnuncio(puja.getAnuncio());
            pujaActual.setComprador(puja.getComprador());
            pujaActual.setOferta(puja.getOferta());
            pujaActual.setEstadoAnuncio(puja.getEstadoAnuncio());
            return true;
        }
    }

    public void agregarProducto(Producto nuevoProducto) throws ProductoException{
        getListaProductos().add(nuevoProducto);
    }

    public void agregarUsuario(Usuario nuevoUsuario) throws UsuarioException{
        getListaUsuarios().add(nuevoUsuario);
    }

    public void agregarAnunciante(Anunciante nuevoAnunciante) throws AnuncianteException{
        getListaAnunciantes().add(nuevoAnunciante);
    }

    public void agregarComprador(Comprador nuevoComprador) throws CompradorException{
        getListaCompradores().add(nuevoComprador);
    }

    public void agregarAnuncio(Anuncio nuevoAnuncio) throws AnuncioException{
        getListaAnuncios().add(nuevoAnuncio);
    }

    public void agregarPuja(Puja nuevaPuja) throws PujaException{
        getListaPujas().add(nuevaPuja);
    }

    @Override
    public boolean verificarProductoExistente(String codigoUnico) throws ProductoException {
        if(productoExiste(codigoUnico)){
            throw new ProductoException("El producto con código único: "+codigoUnico+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public boolean verificarUsuarioExistente(String nombreUsuario) throws UsuarioException {
        if(usuarioExiste(nombreUsuario)){
            throw new UsuarioException("El usuario con nombre: "+nombreUsuario+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public boolean verificarAnuncianteExistente(String cedula) throws AnuncianteException {
        if(anuncianteExiste(cedula)){
            throw new AnuncianteException("El anunciante con cédula: "+cedula+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public boolean verificarCompradorExistente(String cedula) throws CompradorException {
        if(compradorExiste(cedula)){
            throw new CompradorException("El comprador con cédula: "+cedula+" ya existe");
        }else{
            return false;
        }
    }

    @Override
    public boolean verificarAnuncioExistente(String codigo) throws AnuncioException {
        if(anuncioExiste(codigo)){
            throw new AnuncioException("El anuncio con cédula: "+codigo+" ya existe");
        }else{
            return false;
        }
    }

    public boolean productoExiste(String codigoUnico) {
        boolean productoEncontrado = false;
        for (Producto producto : getListaProductos()) {
            if(producto.getCodigoUnico().equalsIgnoreCase(codigoUnico)){
                productoEncontrado = true;
                break;
            }
        }
        return productoEncontrado;
    }

    public boolean usuarioExiste(String nombreUsuario) {
        boolean usuarioEncontrado = false;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getUsuario().equalsIgnoreCase(nombreUsuario)){
                usuarioEncontrado = true;
                break;
            }
        }
        return usuarioEncontrado;
    }

    public boolean anuncianteExiste(String cedula) {
        boolean anuncianteEncontrado = false;
        for (Anunciante anunciante : getListaAnunciantes()) {
            if(anunciante.getCedula().equalsIgnoreCase(cedula)){
                anuncianteEncontrado = true;
                break;
            }
        }
        return anuncianteEncontrado;
    }

    public boolean compradorExiste(String cedula) {
        boolean compradorEncontrado = false;
        for (Comprador comprador : getListaCompradores()) {
            if(comprador.getCedula().equalsIgnoreCase(cedula)){
                compradorEncontrado = true;
                break;
            }
        }
        return compradorEncontrado;
    }

    public int numeroPujasPorProducto(String cedula, String codigoAnuncio){
        int contador = 0;
        for(Puja puja : getListaPujas()){
            if(puja.getAnuncio().equals(codigoAnuncio) && puja.getComprador().contains(cedula)){
                contador++;
            }
        }
        return contador;
    }

    @Override
    public boolean usuarioExiste(String cedula, String password){
        boolean usuarioExiste = false;
        for(Usuario usuario : getListaUsuarios()){
            if(usuario.getUsuario().equalsIgnoreCase(cedula) &&
                    usuario.getContrasenia().equalsIgnoreCase(password)){
                usuarioExiste = true;
                usuarioLogeado = cedula;
                break;
            }
        }

        for(Anunciante anunciante : getListaAnunciantes()){
            if(anunciante.getCedula().equalsIgnoreCase(cedula) &&
                    anunciante.getContrasenia().equalsIgnoreCase(password)){
                usuarioExiste = true;
                rolUsuarioLogeado = anunciante.getRol();
                cedulaUsuario = anunciante.getCedula();
                usuarioLogeado = cedula + " " + anunciante.getNombre() + " " + anunciante.getApellido();
                usuarioChat = anunciante.getNombre() + " " + anunciante.getApellido();
                break;
            }
        }

        for(Comprador comprador : getListaCompradores()){
            if(comprador.getCedula().equalsIgnoreCase(cedula) &&
                    comprador.getContrasenia().equalsIgnoreCase(password)){
                usuarioExiste = true;
                rolUsuarioLogeado = comprador.getRol();
                cedulaUsuario = comprador.getCedula();
                usuarioLogeado = cedula + " " +comprador.getNombre() +" "+comprador.getApellido();
                usuarioChat = comprador.getNombre() +" "+comprador.getApellido();
                break;
            }
        }

        return usuarioExiste;
    }

    @Override
    public boolean anuncioExiste(String codigo) {
        boolean anuncioEncontrado = false;
        for (Anuncio anuncio : getListaAnuncios()) {
            if(anuncio.getCodigo().equalsIgnoreCase(codigo)){
                anuncioEncontrado = true;
                break;
            }
        }
        return anuncioEncontrado;
    }

    @Override
    public String obtenerEstadoAnuncio(String codigo) {
        String estado = "";
        for (Anuncio anuncio : getListaAnuncios()) {
            if(anuncio.getCodigo().equalsIgnoreCase(codigo)){
                estado = anuncio.getEstado();
                break;
            }
        }
        return estado;
    }

    @Override
    public Producto obtenerProducto(String codigoUnico) {
        Producto productoEncontrado = null;
        for (Producto producto : getListaProductos()) {
            if(producto.getCodigoUnico().equalsIgnoreCase(codigoUnico)){
                productoEncontrado = producto;
                break;
            }
        }
        return productoEncontrado;
    }

    @Override
    public Usuario obtenerUsuario(String nombreUsuario) {
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getUsuario().equalsIgnoreCase(nombreUsuario)){
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }

    @Override
    public Anunciante obtenerAnunciante(String cedula) {
        Anunciante anuncianteEncontrado = null;
        for (Anunciante anunciante : getListaAnunciantes()) {
            if(anunciante.getCedula().equalsIgnoreCase(cedula)){
                anuncianteEncontrado = anunciante;
                break;
            }
        }
        return anuncianteEncontrado;
    }

    @Override
    public Comprador obtenerComprador(String cedula) {
        Comprador compradorEncontrado = null;
        for (Comprador comprador : getListaCompradores()) {
            if(comprador.getCedula().equalsIgnoreCase(cedula)){
                compradorEncontrado = comprador;
                break;
            }
        }
        return compradorEncontrado;
    }

    @Override
    public Comprador obtenerCompradorPorUsuario(String cedula) {
        Comprador compradorEncontrado = null;
        for (Comprador comprador : getListaCompradores()) {
            if(comprador.getCedula().contains(cedula)){
                compradorEncontrado = comprador;
                break;
            }
        }
        return compradorEncontrado;
    }

    @Override
    public Anuncio obtenerAnuncio(String cedula){
        Anuncio anuncioEncontrado = null;
        for (Anuncio anuncio : getListaAnuncios()) {
            if(anuncio.getCodigo().equalsIgnoreCase(cedula)){
                anuncioEncontrado = anuncio;
                break;
            }
        }

        return anuncioEncontrado;
    }

    @Override
    public Puja obtenerPuja(String codigo) {
        Puja pujaEncontrada = null;
        for (Puja puja : getListaPujas()) {
            if(puja.getCodigo().equalsIgnoreCase(codigo)){
                pujaEncontrada = puja;
                break;
            }
        }
        return pujaEncontrada;
    }

    @Override
    public ArrayList<Producto> obtenerProductos() {
        // TODO Auto-generated method stub
        return getListaProductos();
    }




}
