package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.Chat;

import java.util.List;

public interface IModelFactoryService {


    void producirMensaje(String queue, String message);

    List<ProductoDto> obtenerProductos();

    List<UsuarioDto> obtenerUsuarios();

    List<AnuncianteDto> obtenerAnunciantes();

    List<CompradorDto> obtenerCompradores();

    List<AnuncioDto> obtenerAnuncios();

    List<PujaDto> obtenerPujas();

    List<Chat> obtenerChats();

    boolean agregarProducto(ProductoDto productoDto);

    boolean agregarAnunciante(AnuncianteDto anuncianteDto);

    boolean agregarComprador(CompradorDto compradorDto);

    boolean agregarAnuncio(AnuncioDto anuncioDto);

    boolean eliminarProducto(String codigoUnico);

    boolean actualizarAnuncio(String codigoActual, AnuncioDto anuncioDto);

    boolean validarEdadAnunciante(AnuncianteDto anuncianteDto);

    boolean validarEdadComprador(CompradorDto compradorDto);

    boolean eliminarAnunciante(String cedula);

    boolean eliminarComprador(String cedula);

    boolean eliminarAnuncio(String codigo);

    boolean actualizarProducto(String cedulaActual, ProductoDto productoDto);

    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String nombreUsuario);

    boolean actualizarUsuario(String nombreUsuario, UsuarioDto usuarioDto);

    boolean actualizarAnunciante(String cedula, AnuncianteDto anuncianteDto);

    boolean actualizarComprador(String cedulaActual, CompradorDto compradorDto);

    boolean inicioSesion(String usuario, String password) throws Exception;
}
