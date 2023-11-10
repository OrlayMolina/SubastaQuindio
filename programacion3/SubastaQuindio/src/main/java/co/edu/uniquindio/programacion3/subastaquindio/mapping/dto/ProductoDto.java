package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record ProductoDto(

    String codigoUnico,
    String nombreProducto,
    String tipoProducto,
    String foto,
    String nombreAnunciante){

    public AnuncianteDto getAnunciante(){
        return new AnuncianteDto(nombreAnunciante, "","", "", "", "", "", "");
    }

    @Override
    public String toString() {
        return codigoUnico + "  "  + nombreProducto;
    }
}

