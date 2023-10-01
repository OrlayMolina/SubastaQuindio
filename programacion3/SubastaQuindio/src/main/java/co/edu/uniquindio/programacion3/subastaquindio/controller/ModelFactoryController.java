package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.controller.service.IModelFactoryService;
import co.edu.uniquindio.programacion3.subastaquindio.exceptions.ProductoException;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDTO;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers.SubastaMapper;
import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.utils.SubastaUtils;

import java.util.List;


public class ModelFactoryController implements IModelFactoryService {

    SubastaQuindio subasta;
    SubastaMapper mapper = SubastaMapper.INSTANCE;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("invocación clase singleton");
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
    }

    public SubastaQuindio getSubasta() {
        return subasta;
    }

    public void setSubasta(SubastaQuindio subasta) {
        this.subasta = subasta;
    }


    @Override
    public List<ProductoDTO> obtenerProductos() {
        return  mapper.getProductoDto(subasta.getListaProductos());
    }

    @Override
    public boolean agregarProducto(ProductoDTO productoDto) {
        try{
            if(!subasta.verificarProductoExistente(productoDto.codigoUnico())) {
                Producto producto = mapper.productoDtoToProducto(productoDto);
                getSubasta().agregarProducto(producto);
            }
            return true;
        }catch (ProductoException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(String codigoUnico) {
        boolean flagExiste = false;
        try {
            flagExiste = getSubasta().eliminarProducto(codigoUnico);
        } catch (ProductoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarProducto(String codigoActual, ProductoDTO productoDto) {
        try {
            Producto producto = mapper.productoDtoToProducto(productoDto);
            getSubasta().actualizarProducto(codigoActual, producto);
            return true;
        } catch (ProductoException e) {
            e.printStackTrace();
            return false;
        }
    }
}
