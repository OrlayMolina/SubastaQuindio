package co.edu.uniquindio.programacion3.subastaquindio.model;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;

import java.time.LocalDate;

public class Producto {

    private String codigoUnico;
    private String nombreProducto;
    private String descripcion;
    private String tipoProducto;
    private String photo;
    private String nombreAnunciante;
    private String fechaPublicacion;
    private String fechaFinPublicacion;
    private double valorInicial;
    public Producto(){

    }

    public Producto(String codigoUnico, String nombreProducto, String descripcion,
                    String tipoProducto, String photo, String nombreAnunciante,
                    String fechaPublicacion, String fechaFinPublicacion,
                    double valorInicial) {
        this.codigoUnico = codigoUnico;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.photo = photo;
        this.nombreAnunciante = nombreAnunciante;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFinPublicacion = fechaFinPublicacion;
        this.valorInicial = valorInicial;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
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

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
}
