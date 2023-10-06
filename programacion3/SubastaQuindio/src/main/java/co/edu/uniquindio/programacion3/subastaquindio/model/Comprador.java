package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;

public class Comprador extends Persona implements Serializable {

    Usuario usuarioAsociado;

    public Comprador(){

    }

    public Comprador(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }
}
