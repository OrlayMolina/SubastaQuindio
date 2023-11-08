package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;

public class Comprador extends Persona implements Serializable {

    String usuarioAsociado;

    public Comprador(){

    }

    public Comprador(String usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public String getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(String usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }
}
