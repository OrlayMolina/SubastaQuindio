package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Anuncio implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private String producto;
    private String anunciante;
    private String fechaPublicacion;
    private String fechaFinPublicacion;
    private double valorInicial;
    private String descripcion;
    private String foto;
    private String estado;

    public Anuncio(){

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(String anunciante) {
        this.anunciante = anunciante;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaFinPublicacion() {
        return fechaFinPublicacion;
    }

    public void setFechaFinPublicacion(String fechaFinPublicacion) {
        this.fechaFinPublicacion = fechaFinPublicacion;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
