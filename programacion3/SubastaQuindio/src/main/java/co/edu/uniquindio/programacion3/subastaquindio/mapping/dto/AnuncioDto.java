package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

import java.time.LocalDate;

public record AnuncioDto(
        String codigo,
        String producto,
        String documentoAnunciante,
        String nombreAnunciante,
        LocalDate fechaPublicacion,
        LocalDate fechaFinPublicacion,
        double valorInicial,
        String descripcion,
        String estado) {

    public ProductoDto getProductoDto(){
        return new ProductoDto("", producto, "", "", "");
    }

    public AnuncianteDto getAnuncianteDto(){
        return new AnuncianteDto(nombreAnunciante,"",documentoAnunciante, "", "","","","");
    }

    @Override
    public String toString() {
        return getAnuncianteDto().toString();
    }
}
