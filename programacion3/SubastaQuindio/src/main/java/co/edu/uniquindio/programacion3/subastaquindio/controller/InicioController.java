package co.edu.uniquindio.programacion3.subastaquindio.controller;

public class InicioController {

    ModelFactoryController modelFactoryController;

    public InicioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean inicioSesion(String usuario, String password){
        return modelFactoryController.inicioSesion(usuario, password);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }

}
