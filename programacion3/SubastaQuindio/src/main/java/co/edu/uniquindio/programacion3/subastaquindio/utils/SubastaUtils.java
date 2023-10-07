package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.model.Comprador;
import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import co.edu.uniquindio.programacion3.subastaquindio.model.Usuario;

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

        Usuario usuario = new Usuario();
        usuario.setUsuario("Matias");
        usuario.setContrasenia("2019");
        subasta.getListaUsuarios().add(usuario);

        Comprador comprador = new Comprador();
        comprador.setNombre("Matias");

        return subasta;
    }
}
