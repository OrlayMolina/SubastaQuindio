package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.exceptions.AnuncioException;
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

    public String obtenerProducto(String codigo){
        return modelFactoryController.obtenerProducto(codigo);
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

    public CompradorDto obtenerComprador(String cedula){
        return modelFactoryController.obtenerComprador(cedula);
    }

    public int numeroPujasPorProducto(String cedula, String codigoAnuncio){
        return modelFactoryController.numeroPujasPorProducto(cedula, codigoAnuncio);
    }

    public List<AnuncianteDto> obtenerAnunciantes(){
        return modelFactoryController.obtenerAnunciantes();
    }

    public String obtenerEstadoAnuncio(String codigo) {
        return modelFactoryController.obtenerEstadoAnuncio(codigo);
    }

    public boolean actualizarTiempoRestante(String codigo) {
        return modelFactoryController.actualizarTiempoRestante(codigo);
    }

    public boolean validarValorPuja(String codigo, String puja){
        return modelFactoryController.validarValorPuja(codigo, puja);
    }
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
