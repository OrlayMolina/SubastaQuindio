package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

import co.edu.uniquindio.programacion3.subastaquindio.enumm.TipoProducto;

import java.time.LocalDate;

public record ProductoDTO (

    String codigoUnico,
    String nombreProducto,
    String descripcion,
    String tipoProducto,
    String foto,
    String nombreAnunciante,
    String fechaPublicacion,
    String fechaFinPublicacion,
    Double valorInicial){

}

