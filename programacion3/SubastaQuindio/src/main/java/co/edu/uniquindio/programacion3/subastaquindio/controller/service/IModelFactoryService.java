package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.util.List;

public interface IModelFactoryService {


    List<ProductoDto> obtenerProductos();

    boolean agregarProducto(ProductoDto productoDto);

    boolean eliminarProducto(String codigoUnico);

    boolean actualizarProducto(String cedulaActual, ProductoDto productoDto);

    boolean inicioSesion(String usuario, String password) throws Exception;
}
