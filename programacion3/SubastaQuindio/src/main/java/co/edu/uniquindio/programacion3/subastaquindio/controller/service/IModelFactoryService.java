package co.edu.uniquindio.programacion3.subastaquindio.controller.service;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDTO;

import java.util.List;

public interface IModelFactoryService {


    List<ProductoDTO> obtenerProductos();

    boolean agregarProducto(ProductoDTO productoDto);
}
