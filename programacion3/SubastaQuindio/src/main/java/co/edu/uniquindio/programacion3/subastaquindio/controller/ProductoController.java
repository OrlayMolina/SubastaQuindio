package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.controller.service.IProductoControllerService;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.List;

public class ProductoController implements IProductoControllerService {

    ModelFactoryController modelFactoryController;

    public ProductoController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @Override
    public List<ProductoDto> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }

    public List<AnuncianteDto> obtenerAnunciantes(){
        return modelFactoryController.obtenerAnunciantes();
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDTO) {
        return modelFactoryController.agregarProducto(productoDTO);
    }

    @Override
    public boolean eliminarProducto(String codigoUnico) {
        return modelFactoryController.eliminarProducto(codigoUnico);
    }

    @Override
    public boolean actualizarProducto(String codigoUnico, ProductoDto productoDto) {
        return modelFactoryController.actualizarProducto(codigoUnico, productoDto);
    }

    @Override
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }


}
