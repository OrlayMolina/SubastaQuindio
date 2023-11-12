package co.edu.uniquindio.programacion3.subastaquindio.model.service;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;

import java.util.ArrayList;

public interface ISubastaQuindioService {


    Producto crearProducto(String codigoUnico, String nombreProducto, String tipoProducto,
                           String foto, String nombreAnunciante) throws ProductoException;

    Boolean eliminarProducto(String codigoUnico) throws ProductoException;

    Boolean eliminarUsuario(String nombreUsuario) throws UsuarioException;

    Boolean eliminarAnunciante(String cedula) throws AnuncianteException;

    Boolean eliminarComprador(String cedula) throws CompradorException;

    Boolean eliminarAnuncio(String cedula) throws AnuncioException;

    boolean actualizarProducto(String codigoUnico, Producto producto) throws ProductoException;

    boolean actualizarUsuario(String nombreUsuario, Usuario usuario) throws UsuarioException;

    boolean actualizarAnunciante(String cedula, Anunciante anunciante) throws AnuncianteException;

    boolean actualizarComprador(String cedula, Comprador comprador) throws CompradorException;

    boolean actualizarAnuncio(String codigo, Anuncio anuncio) throws AnuncioException;

    boolean verificarProductoExistente(String codigoUnico) throws ProductoException;

    boolean verificarUsuarioExistente(String nombreUsuario) throws UsuarioException;

    boolean verificarAnuncianteExistente(String cedula) throws AnuncianteException;

    boolean verificarCompradorExistente(String cedula) throws CompradorException;

    boolean verificarAnuncioExistente(String codigo) throws AnuncioException;

    boolean usuarioExiste(String nombreUsuario, String password);

    boolean anuncioExiste(String cedula);

    Producto obtenerProducto(String cedula);

    Usuario obtenerUsuario(String nombreUsuario);

    Anunciante obtenerAnunciante(String cedula);

    Comprador obtenerComprador(String cedula);

    Anuncio obtenerAnuncio(String cedula);

    ArrayList<Producto> obtenerProductos();
}
