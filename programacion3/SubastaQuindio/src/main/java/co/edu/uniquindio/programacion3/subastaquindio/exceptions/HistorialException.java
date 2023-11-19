package co.edu.uniquindio.programacion3.subastaquindio.exceptions;

//Exception que no permitirá elegir una Puja si el anuncio no ha finalizado aún
public class HistorialException extends Exception{
    public HistorialException(String mensaje) {
        super(mensaje);
    }
}
