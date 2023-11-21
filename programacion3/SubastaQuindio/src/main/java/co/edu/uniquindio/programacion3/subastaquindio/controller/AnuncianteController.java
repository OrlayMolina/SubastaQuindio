package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.List;

public class AnuncianteController {

    ModelFactoryController modelFactoryController;

    public AnuncianteController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<AnuncianteDto> obtenerAnunciantes() {
        return modelFactoryController.obtenerAnunciantes();
    }

    public List<UsuarioDto> obtenerUsuarios(){
        return modelFactoryController.obtenerUsuarios();
    }

    public AnuncianteDto obtenerAnunciante(String cedula){
        return modelFactoryController.obtenerAnunciante(cedula);
    }

    public boolean validarEdadAnunciante(AnuncianteDto anuncianteDto){
        return modelFactoryController.validarEdadAnunciante(anuncianteDto);
    }

    public boolean agregarAnunciante(AnuncianteDto anuncianteDto) {
        return modelFactoryController.agregarAnunciante(anuncianteDto);
    }

    public boolean eliminarAnunciante(String cedula) {
        return modelFactoryController.eliminarAnunciante(cedula);
    }

    public boolean actualizarAnunciante(String cedula, AnuncianteDto anuncianteDto) {
        return modelFactoryController.actualizarAnunciante(cedula, anuncianteDto);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }

}
