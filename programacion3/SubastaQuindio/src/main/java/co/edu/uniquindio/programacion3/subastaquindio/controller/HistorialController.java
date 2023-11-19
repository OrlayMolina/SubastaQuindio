package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.PujaDto;

import java.util.List;

public class HistorialController {

    ModelFactoryController modelFactoryController;

    public HistorialController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean elegirPuja(String codigo, PujaDto pujaDto) {
        return modelFactoryController.actualizarPuja(codigo, pujaDto);
    }

    public List<PujaDto> obtenerPujas() {
        return modelFactoryController.obtenerPujas();
    }

    public List<ProductoDto> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }

    public List<CompradorDto> obtenerCompradores() {
        return modelFactoryController.obtenerCompradores();
    }

    public CompradorDto obtenerComprador(String nombre){
        return modelFactoryController.obtenerComprador(nombre);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
