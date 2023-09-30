package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.controller.service.IProductoControllerService;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDTO;

import java.util.List;

public class ProductoController implements IProductoControllerService {

    ModelFactoryController modelFactoryController;

    public ProductoController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @Override
    public List<ProductoDTO> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }

    @Override
    public boolean agregarProducto(ProductoDTO productoDTO) {
        return modelFactoryController.agregarProducto(productoDTO);
    }

    @Override
    public boolean eliminarProducto(String codigoUnico) {
        return false;
    }

    @Override
    public boolean actualizarProducto(String codigoUnico, ProductoDTO productoDto) {
        return false;
    }
}
