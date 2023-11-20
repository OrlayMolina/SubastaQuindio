package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record UsuarioDto(
        String usuario,
        String contrasenia) {

    @Override
    public String toString(){
        return usuario;
    }
}
