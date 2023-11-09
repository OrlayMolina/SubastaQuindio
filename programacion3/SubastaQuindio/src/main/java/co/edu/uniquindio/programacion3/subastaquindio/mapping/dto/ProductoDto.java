package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record ProductoDto(

    String codigoUnico,
    String nombreProducto,
    String descripcion,
    String tipoProducto,
    String foto,
    String nombreAnunciante,
    String fechaPublicacion,
    String fechaFinPublicacion,
    Double valorInicial){

    public AnuncianteDto getAnunciante(){
        return new AnuncianteDto(nombreAnunciante, "","", "", "", "", "", "");
    }

}

