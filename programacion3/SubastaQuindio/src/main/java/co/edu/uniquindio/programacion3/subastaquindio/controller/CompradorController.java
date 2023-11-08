package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.List;

public class CompradorController {

    ModelFactoryController modelFactoryController;

    public CompradorController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<CompradorDto> obtenerCompradores() {
        return modelFactoryController.obtenerCompradores();
    }

    public List<UsuarioDto> obtenerUsuarios(){
        return modelFactoryController.obtenerUsuarios();
    }

    public boolean agregarComprador(CompradorDto compradorDto) {
        return modelFactoryController.agregarComprador(compradorDto);
    }

    public boolean eliminarComprador(String cedula) {
        return modelFactoryController.eliminarComprador(cedula);
    }

    public boolean actualizarAnunciante(String cedula, CompradorDto compradorDto) {
        return modelFactoryController.actualizarComprador(cedula, compradorDto);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
