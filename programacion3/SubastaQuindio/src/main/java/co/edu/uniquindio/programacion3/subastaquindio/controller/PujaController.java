package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;

import java.util.List;

public class PujaController {

    ModelFactoryController modelFactoryController;

    public PujaController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean agregarPuja(PujaDto pujaDto) {
        return modelFactoryController.agregarPuja(pujaDto);
    }

    public List<AnuncioDto> obtenerAnuncios() {
        return modelFactoryController.obtenerAnuncios();
    }

    public List<PujaDto> obtenerPujas() {
        return modelFactoryController.obtenerPujas();
    }

    public List<ProductoDto> obtenerProductos(){
        return modelFactoryController.obtenerProductos();
    }

    public CompradorDto obtenerComprador(String nombre){
        return modelFactoryController.obtenerComprador(nombre);
    }

    public List<AnuncianteDto> obtenerAnunciantes(){
        return modelFactoryController.obtenerAnunciantes();
    }

    public String obtenerEstadoAnuncio(String codigo) {
        return modelFactoryController.obtenerEstadoAnuncio(codigo);
    }
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
