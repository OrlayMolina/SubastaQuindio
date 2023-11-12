package co.edu.uniquindio.programacion3.subastaquindio.model;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;

import java.io.Serializable;
import java.time.LocalDate;

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigoUnico;
    private String nombreProducto;
    private String tipoProducto;
    private String foto;
    private String nombreAnunciante;
    public Producto(){

    }

    public Producto(String codigoUnico, String nombreProducto, String descripcion,
                    String tipoProducto, String foto, String nombreAnunciante,
                    String fechaPublicacion, String fechaFinPublicacion,
                    double valorInicial) {
        this.codigoUnico = codigoUnico;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.foto = foto;
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
}
