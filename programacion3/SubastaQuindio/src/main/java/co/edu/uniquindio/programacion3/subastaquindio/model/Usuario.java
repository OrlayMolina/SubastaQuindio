package co.edu.uniquindio.programacion3.subastaquindio.model;


import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;

import java.io.Serializable;

public class Usuario implements Serializable {

    //atributos
    private static final long serialVersionUID = 1L;

    private String usuario;
    private String contrasenia;

    public Usuario(){

    }

    public Usuario(String usuario, String contrasenia, Rol rol) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
