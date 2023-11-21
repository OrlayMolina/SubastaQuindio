package co.edu.uniquindio.programacion3.subastaquindio.controller;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ChatDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.Chat;

import java.util.List;

public class ChatController {


    ModelFactoryController modelFactoryController;

    public ChatController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public void iniciarChat(String texto) {
        modelFactoryController.iniciarChat(texto);
    }

    public List<ChatDto> obtenerChats() {
        return modelFactoryController.obtenerChats();
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactoryController.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
