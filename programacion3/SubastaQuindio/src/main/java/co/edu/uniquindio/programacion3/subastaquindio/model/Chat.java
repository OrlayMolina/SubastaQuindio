package co.edu.uniquindio.programacion3.subastaquindio.model;

import java.io.Serial;
import java.io.Serializable;

public class Chat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private  String anunciante;
    private String miChat;

    public Chat(){
        super();
    }
    public Chat(String anunciante, String miChat){
        super();
        this.anunciante = anunciante;
        this.miChat = miChat;
    }
    public String getAnunciante() {
        return anunciante;
    }
    public void setAnunciante(String anunciante) {
        this.anunciante = anunciante;
    }
    public String getMiChat() {
        return miChat;
    }

    public void setMiChat(String miChat){
        this.miChat = miChat;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    /**
     * toString
     */
    @Override
    public String toString() {
        return "ChatVendedores [vendedor2=" + anunciante + ", miChat=" + miChat + "]";
    }
}
