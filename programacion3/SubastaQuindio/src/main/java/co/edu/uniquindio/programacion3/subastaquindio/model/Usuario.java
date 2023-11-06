package co.edu.uniquindio.programacion3.subastaquindio.model;


import co.edu.uniquindio.programacion3.subastaquindio.enumm.Rol;

import java.io.Serializable;

public class Usuario implements Serializable {

    //atributos
    private static final long serialVersionUID = 1L;

    private Rol rol;
    private String usuario;
    private String contrasenia;


    public Usuario(Rol rol, String usuario, String contrasenia) {
        super();
        this.rol = rol;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    // constructor sin parametros
    public Usuario() {
        super();

    }


    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    @Override
    public String toString() {
        return usuario + " -- " + rol;
    }


}
