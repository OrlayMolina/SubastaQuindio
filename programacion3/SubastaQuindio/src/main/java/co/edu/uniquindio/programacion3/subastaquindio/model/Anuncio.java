package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Anuncio implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombreProducto;
    private String documentoAnunciante;
    private String nombreAnunciante;
    private LocalDate fechaPublicacion;
    private LocalDate fechaFinPublicacion;
    private double valorInicial;
    private String descripcion;
    private String estado;

    public Anuncio(){

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDocumentoAnunciante() {
        return documentoAnunciante;
    }

    public void setDocumentoAnunciante(String documentoAnunciante) {
        this.documentoAnunciante = documentoAnunciante;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaFinPublicacion() {
        return fechaFinPublicacion;
    }

    public void setFechaFinPublicacion(LocalDate fechaFinPublicacion) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
