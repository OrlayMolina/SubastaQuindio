package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;

public class SubastaUtils {

    public static SubastaQuindio inicializarDatos() {

        SubastaQuindio subasta = new SubastaQuindio();

        Producto producto = new Producto();
        producto.setCodigoUnico("1243");
        producto.setNombreProducto("Lavadora Haceb");
        producto.setDescripcion("Lavadora casi nueva");
        producto.setNombreAnunciante("Carlos Montes");
        producto.setTipoProducto("HOGAR");
        producto.setFechaPublicacion("29-09-2023");
        producto.setFechaFinPublicacion("29-09-2023");
        producto.setValorInicial(45200);
        subasta.getListaProductos().add(producto);

        return subasta;
    }
}
