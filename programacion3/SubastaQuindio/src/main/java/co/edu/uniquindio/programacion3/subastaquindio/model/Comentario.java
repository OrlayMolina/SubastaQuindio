package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serial;
import java.io.Serializable;

public class Comentario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Anunciante anuncianteEnviado;
    private Producto productoComentado;
    private String nombre;
    private String identificacion;
    private String comentario;
    public Comentario() {

    }

    public Comentario(Anunciante vendedorEnviado, Producto productoComentado, String nombre, String identificacion, String comentario) {
        this.anuncianteEnviado = vendedorEnviado;
        this.productoComentado = productoComentado;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.comentario = comentario;
    }

    public Anunciante getAnuncianteEnviado() {
        return anuncianteEnviado;
    }
    public void setAnuncianteEnviado(Anunciante anuncianteEnviado) {
        this.anuncianteEnviado = anuncianteEnviado;
    }
    public Producto getProductoComentado() {
        return productoComentado;
    }
    public void setProductoComentado(Producto productoComentado) {
        this.productoComentado = productoComentado;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentetificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
