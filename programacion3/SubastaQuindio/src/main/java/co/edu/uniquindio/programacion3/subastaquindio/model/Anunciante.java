package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Anunciante extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private String usuarioAsociado;

    public Anunciante(){

    }

    public String getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(String usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }


}
