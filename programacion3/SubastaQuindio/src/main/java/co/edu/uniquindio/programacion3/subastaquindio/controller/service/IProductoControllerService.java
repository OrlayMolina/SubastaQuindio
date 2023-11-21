package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.util.List;

public interface IProductoControllerService {

    List<ProductoDto> obtenerProductos();

    AnuncianteDto obtenerAnunciante(String cedula);

    boolean agregarProducto(ProductoDto productoDTO);

    boolean eliminarProducto(String codigoUnico);

    boolean actualizarProducto(String codigoUnico, ProductoDto productoDto);

    void registrarAcciones(String mensaje, int nivel, String accion);
}
