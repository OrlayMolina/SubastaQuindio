package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Anunciante extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    Usuario usuarioAsociado;

    public Anunciante(){

    }
    public Anunciante(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

}
