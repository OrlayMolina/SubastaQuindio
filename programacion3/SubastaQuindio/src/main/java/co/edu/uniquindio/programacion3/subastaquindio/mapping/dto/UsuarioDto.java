package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record UsuarioDto(
        String usuario,
        String contrasenia,
        String rol) {

    @Override
    public String toString(){
        return usuario + " rol -- " + rol;
    }
}
