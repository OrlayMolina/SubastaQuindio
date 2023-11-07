package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.List;

public class UsuarioController {

    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactoryController.obtenerUsuarios();
    }

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactoryController.agregarUsuario(usuarioDto);
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        return modelFactoryController.eliminarUsuario(nombreUsuario);
    }

    public boolean actualizarUsuario(String nombreUsuario, UsuarioDto usuarioDto) {
        return modelFactoryController.actualizarUsuario(nombreUsuario, usuarioDto);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }

}
