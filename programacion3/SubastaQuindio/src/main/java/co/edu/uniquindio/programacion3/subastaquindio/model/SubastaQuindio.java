package co.edu.uniquindio.programacion3.subastaquindio.model;

import co.edu.uniquindio.programacion3.subastaquindio.exceptions.ProductoException;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.UsuarioException;
import co.edu.uniquindio.programacion3.subastaquindio.model.service.ISubastaQuindioService;

import java.io.Serializable;
import java.util.ArrayList;

public class SubastaQuindio implements ISubastaQuindioService, Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();

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


    public boolean inicioSesion(String usuario, String password){
        boolean encontrado = usuarioExiste(usuario, password);
        return encontrado;
    }
    @Override
    public Producto crearProducto(String codigoUnico, String nombreProducto, String descripcion, String tipoProducto,
                                  String foto, String nombreAnunciante, String fechaPublicacion, String fechaFinPublicacion,
                                  Double valorInicial) throws ProductoException{
        Producto nuevoProducto = null;
        boolean productoExiste = verificarProductoExistente(codigoUnico);
        if(productoExiste){
            throw new ProductoException("El producto con codigo unico: "+codigoUnico+" ya existe");
        }else{
            nuevoProducto = new Producto();
            nuevoProducto.setCodigoUnico(codigoUnico);
            nuevoProducto.setNombreProducto(nombreProducto);
            nuevoProducto.setDescripcion(descripcion);
            nuevoProducto.setTipoProducto(tipoProducto);
            nuevoProducto.setPhoto(foto);
            nuevoProducto.setNombreAnunciante(nombreAnunciante);
            nuevoProducto.setFechaPublicacion(fechaPublicacion);
            nuevoProducto.setFechaPublicacion(fechaFinPublicacion);
            nuevoProducto.setValorInicial(valorInicial);
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
    public boolean actualizarProducto(String codigoUnico, Producto producto) throws ProductoException {
        Producto productoActual = obtenerProducto(codigoUnico);
        if(productoActual == null)
            throw new ProductoException("El producto a actualizar no existe");
        else{
            productoActual.setCodigoUnico(producto.getCodigoUnico());
            productoActual.setNombreProducto(producto.getNombreProducto());
            productoActual.setDescripcion(producto.getDescripcion());
            productoActual.setTipoProducto(producto.getTipoProducto());
            productoActual.setPhoto(producto.getPhoto());
            productoActual.setNombreAnunciante(producto.getNombreAnunciante());
            productoActual.setFechaPublicacion(producto.getFechaPublicacion());
            productoActual.setFechaFinPublicacion(producto.getFechaFinPublicacion());
            productoActual.setValorInicial(producto.getValorInicial());
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

    public void agregarProducto(Producto nuevoProducto) throws ProductoException{
        getListaProductos().add(nuevoProducto);
    }

    public void agregarUsuario(Usuario nuevoUsuario) throws UsuarioException{
        getListaUsuarios().add(nuevoUsuario);
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

    @Override
    public boolean usuarioExiste(String nombreUsuario, String password){
        boolean usuarioExiste = false;
        for(Usuario usuario : getListaUsuarios()){
            if(usuario.getUsuario().equalsIgnoreCase(nombreUsuario) &&
                    usuario.getContrasenia().equalsIgnoreCase(password)){
                usuarioExiste = true;
                break;
            }
        }
        return usuarioExiste;
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
    public ArrayList<Producto> obtenerProductos() {
        // TODO Auto-generated method stub
        return getListaProductos();
    }


}
