package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;

public class Puja implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private String producto;
    private String anuncio;
    private String comprador;
    private double oferta;
    private String estadoAnuncio;

    public Puja() {
    }

    public Puja(String codigo, String producto, String anuncio, String comprador, double oferta, String estadoAnuncio) {
        this.codigo = codigo;
        this.producto = producto;
        this.anuncio = anuncio;
        this.comprador = comprador;
        this.oferta = oferta;
        this.estadoAnuncio = estadoAnuncio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public double getOferta() {
        return oferta;
    }

    public void setOferta(double oferta) {
        this.oferta = oferta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(String anuncio) {
        this.anuncio = anuncio;
    }

    public String getEstadoAnuncio() {
        return estadoAnuncio;
    }

    public void setEstadoAnuncio(String estadoAnuncio) {
        this.estadoAnuncio = estadoAnuncio;
    }
}
