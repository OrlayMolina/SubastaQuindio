package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.exceptions.AnuncioException;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.Anuncio;
import co.edu.uniquindio.programacion3.subastaquindio.model.Chat;

import java.util.List;

public interface IModelFactoryService {


    void producirMensaje(String queue, String message);

    List<ProductoDto> obtenerProductos();

    List<UsuarioDto> obtenerUsuarios();

    CompradorDto obtenerComprador(String nombre);

    int numeroPujasPorProducto(String cedula, String codigoAnuncio);

    String obtenerProducto(String nombre);

    Anuncio obtenerAnuncio(String codigo) throws AnuncioException;

    List<AnuncianteDto> obtenerAnunciantes();

    AnuncianteDto obtenerAnunciante(String cedula);

    List<CompradorDto> obtenerCompradores();

    List<AnuncioDto> obtenerAnuncios();

    String obtenerEstadoAnuncio(String codigo);

    List<PujaDto> obtenerPujas();

    List<ChatDto> obtenerChats();

    boolean agregarProducto(ProductoDto productoDto);

    boolean actualizarPuja(String codigo, PujaDto pujaDto);

    boolean agregarAnunciante(AnuncianteDto anuncianteDto);

    boolean agregarComprador(CompradorDto compradorDto);

    boolean agregarAnuncio(AnuncioDto anuncioDto);

    boolean eliminarProducto(String codigoUnico);

    boolean actualizarAnuncio(String codigoActual, AnuncioDto anuncioDto);

    boolean validarEdadAnunciante(AnuncianteDto anuncianteDto);

    boolean validarEdadComprador(CompradorDto compradorDto);

    boolean eliminarAnunciante(String cedula);

    boolean eliminarComprador(String cedula);

    boolean agregarPuja(PujaDto pujaDto);

    boolean eliminarAnuncio(String codigo);

    boolean actualizarProducto(String cedulaActual, ProductoDto productoDto);

    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String nombreUsuario);

    boolean actualizarUsuario(String nombreUsuario, UsuarioDto usuarioDto);

    boolean actualizarAnunciante(String cedula, AnuncianteDto anuncianteDto);

    boolean actualizarComprador(String cedulaActual, CompradorDto compradorDto);

    boolean inicioSesion(String usuario, String password) throws Exception;
}
