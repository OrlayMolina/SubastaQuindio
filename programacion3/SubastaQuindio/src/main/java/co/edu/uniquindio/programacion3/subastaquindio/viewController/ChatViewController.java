package co.edu.uniquindio.programacion3.subastaquindio.viewController;

import co.edu.uniquindio.programacion3.subastaquindio.controller.ChatController;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ChatDto;
import co.edu.uniquindio.programacion3.subastaquindio.model.Chat;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import static co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio.usuarioChat;
public class ChatViewController {

    SubastaQuindio subastaQuindio;
    ChatController chatControllerService;
    ObservableList<ChatDto> listaChats = FXCollections.observableArrayList();
    Chat chatSelecionado;

    @FXML
    private Button btnEnviarComen_chat;

    @FXML
    private TextArea txtAreaChat_chat;

    @FXML
    private TextArea txtAreaComen_chat;

    @FXML
    void initialize() {
        subastaQuindio = new SubastaQuindio();
        chatControllerService = new ChatController();

        obtenerChatsAnteriores();

        // Agregar un Listener para actualizar automáticamente el txtAreaChat_chat cuando se agreguen nuevos chats
        listaChats.addListener((ListChangeListener<ChatDto>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (ChatDto nuevoChat : change.getAddedSubList()) {
                        mostrarMensajeAnterior(nuevoChat);
                    }
                }
            }
        });
    }
    public SubastaQuindio getSubasta() {
        return subastaQuindio;
    }

    @FXML
    void enviarComentarioAction(ActionEvent event) {
        String texto = usuarioChat+": ";
         texto += txtAreaComen_chat.getText();
        chatControllerService.iniciarChat(texto);

        String mensajeAnterior = txtAreaChat_chat.getText();
        if (!mensajeAnterior.isEmpty()) {
            mensajeAnterior += "\n";
        }
        txtAreaChat_chat.setText(mensajeAnterior + texto);

        txtAreaComen_chat.clear();
    }

    private void obtenerChatsAnteriores() {
        listaChats.addAll(chatControllerService.obtenerChats());
        for (ChatDto chatDto : listaChats) {
            mostrarMensajeAnterior(chatDto);
        }
    }

    private void mostrarMensajeAnterior(ChatDto chatDto) {
        String mensajeAnterior = txtAreaChat_chat.getText();
        if (!mensajeAnterior.isEmpty()) {
            mensajeAnterior += "\n";
        }
        txtAreaChat_chat.setText(mensajeAnterior + chatDto.miChat());
    }


}
