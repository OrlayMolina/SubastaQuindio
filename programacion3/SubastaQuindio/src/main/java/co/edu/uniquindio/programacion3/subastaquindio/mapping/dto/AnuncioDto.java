package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

import java.time.LocalDate;

public record AnuncioDto(
        String codigo,
        String producto,
        String anunciante,
        String fechaPublicacion,
        String fechaFinPublicacion,
        String valorInicial,
        String descripcion,
        String foto,
        String estado) {

    public ProductoDto getProductoDto(){
        return new ProductoDto("", producto, "", "", "");
    }

    public AnuncianteDto getAnuncianteDto(){
        return new AnuncianteDto(anunciante,"", "", "","","","","", "", "");
    }

}
