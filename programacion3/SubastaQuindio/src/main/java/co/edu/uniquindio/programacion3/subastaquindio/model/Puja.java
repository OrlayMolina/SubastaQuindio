package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;

public class Puja implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private String producto;
    private String anuncio;
    private String comprador;
    private double oferta;

    public Puja() {
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
}