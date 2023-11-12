package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record CompradorDto(
        String nombre,
        String apellido,
        String cedula,
        String telefono,
        String direccion,
        String correo,
        String fechaNacimiento,
        String usuarioAsociado) {

    public UsuarioDto getUsuarioAsociado(){
        return new UsuarioDto(usuarioAsociado, "","");
    }

    @Override
    public String toString() {
        return cedula + " " + nombre + " " + apellido;
    }
}
