package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.util.List;

public class AnuncioController {

    ModelFactoryController modelFactoryController;

    public AnuncioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<AnuncioDto> obtenerAnuncios() {
        return modelFactoryController.obtenerAnuncios();
    }

    public AnuncianteDto obtenerAnunciante(String cedula){
        return modelFactoryController.obtenerAnunciante(cedula);
    }

    public List<ProductoDto> obtenerProductos(){
        return modelFactoryController.obtenerProductos();
    }

    public List<AnuncianteDto> obtenerAnunciantes(){
        return modelFactoryController.obtenerAnunciantes();
    }

    public String obtenerProducto(String codigo){
        return modelFactoryController.obtenerProducto(codigo);
    }

    public boolean agregarAnuncio(AnuncioDto anuncioDto) {
        return modelFactoryController.agregarAnuncio(anuncioDto);
    }

    public boolean eliminarAnuncio(String codigo) {
        return modelFactoryController.eliminarAnuncio(codigo);
    }

    public boolean actualizarAnuncio(String cedula, AnuncioDto anuncioDto) {
        return modelFactoryController.actualizarAnuncio(cedula, anuncioDto);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
