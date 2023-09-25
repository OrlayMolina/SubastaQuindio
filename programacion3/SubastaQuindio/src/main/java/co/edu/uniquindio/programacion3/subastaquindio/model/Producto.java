package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.time.LocalDate;

public class Producto {

    private String nombreProducto;

    private String descripcion;

    private String photo;

    private String nombreAnunciante;

    private LocalDate fechaPublicacion;

    private LocalDate fechaFinPublicacion;

    private double valorInicial;

    public Producto(){

    }

    public Producto(String nombreProducto, String descripcion, String photo,
                    String nombreAnunciante, LocalDate fechaPublicacion,
                    LocalDate fechaFinPublicacion, double valorInicial) {
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
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
}
