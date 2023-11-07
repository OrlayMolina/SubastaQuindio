package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.List;

public interface IModelFactoryService {


    List<ProductoDto> obtenerProductos();

    List<UsuarioDto> obtenerUsuarios();

    boolean agregarProducto(ProductoDto productoDto);

    boolean eliminarProducto(String codigoUnico);

    boolean actualizarProducto(String cedulaActual, ProductoDto productoDto);

    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String nombreUsuario);

    boolean actualizarUsuario(String nombreUsuario, UsuarioDto usuarioDto);

    boolean inicioSesion(String usuario, String password) throws Exception;
}
