package co.edu.uniquindio.programacion3.subastaquindio.model.service;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.AnuncianteException;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.ProductoException;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.UsuarioException;
import co.edu.uniquindio.programacion3.subastaquindio.model.Anunciante;
import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import co.edu.uniquindio.programacion3.subastaquindio.model.Usuario;

import java.util.ArrayList;

public interface ISubastaQuindioService {


    Producto crearProducto(String codigoUnico, String nombreProducto, String descripcion, String tipoProducto,
                           String foto, String nombreAnunciante, String fechaPublicacion, String fechaFinPublicacion,
                           Double valorInicial) throws ProductoException;

    Boolean eliminarProducto(String codigoUnico) throws ProductoException;

    Boolean eliminarUsuario(String nombreUsuario) throws UsuarioException;

    Boolean eliminarAnunciante(String cedula) throws AnuncianteException;

    boolean actualizarProducto(String codigoUnico, Producto producto) throws ProductoException;

    boolean actualizarUsuario(String nombreUsuario, Usuario usuario) throws UsuarioException;

    boolean actualizarAnunciante(String cedula, Anunciante anunciante) throws AnuncianteException;

    boolean verificarProductoExistente(String codigoUnico) throws ProductoException;

    boolean verificarUsuarioExistente(String nombreUsuario) throws UsuarioException;

    boolean verificarAnuncianteExistente(String cedula) throws AnuncianteException;

    boolean usuarioExiste(String nombreUsuario, String password);

    Producto obtenerProducto(String cedula);

    Usuario obtenerUsuario(String nombreUsuario);

    Anunciante obtenerAnunciante(String cedula);

    ArrayList<Producto> obtenerProductos();
}
