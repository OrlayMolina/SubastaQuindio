package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;

public class SubastaUtils {

    public static SubastaQuindio inicializarDatos() {

        SubastaQuindio subasta = new SubastaQuindio();

        Producto producto = new Producto();
        producto.setCodigoUnico("1243");
        producto.setNombreProducto("Lavadora Haceb");
        producto.setNombreAnunciante("Carlos Montes");
        producto.setTipoProducto("HOGAR");
        subasta.getListaProductos().add(producto);

        Usuario usuario = new Usuario();
        usuario.setUsuario("Matias");
        usuario.setContrasenia("2019");
        usuario.setRol(Rol.Comprador);
        subasta.getListaUsuarios().add(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("Jorge");
        usuario2.setContrasenia("2022");
        usuario2.setRol(Rol.Anunciante);
        subasta.getListaUsuarios().add(usuario2);

        Comprador comprador = new Comprador();
        comprador.setNombre("Matias");
        comprador.setApellido("Rodriguez");
        comprador.setCedula("1462653");
        comprador.setTelefono("310787916");
        comprador.setDireccion("mza 2 # 24");
        comprador.setCorreo("matias@gmail.com");
        comprador.setFechaNacimiento("30-05-1990");

        Anunciante anunciante = new Anunciante();
        anunciante.setNombre("Jorge");
        anunciante.setApellido("Molina");
        anunciante.setCedula("86266656");
        anunciante.setTelefono("314651963");
        anunciante.setDireccion("mza 2 # 36");
        anunciante.setCorreo("jorge@gmail.com");
        anunciante.setFechaNacimiento("16-05-1989");


        return subasta;
    }
}
