package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.time.LocalDate;
import java.util.function.Predicate;

public class AnuncioUtil {

    public static Predicate<AnuncioDto> buscarPorCodigo(String codigo){
        return anuncioDto -> anuncioDto.codigo().contains(codigo);
    }

    public static Predicate<AnuncioDto> buscarPorProducto(String nombreProducto){
        return anuncioDto -> anuncioDto.getProductoDto().nombreProducto().contains(nombreProducto);
    }

    public static Predicate<AnuncioDto> buscarPorAnunciante(String nombreProducto){
        return anuncioDto -> anuncioDto.getAnuncianteDto().toString().contains(nombreProducto);
    }

    public static Predicate<AnuncioDto> buscarPorFechaPublicacion(LocalDate fechaPublicacion){
        return anuncioDto -> anuncioDto.fechaPublicacion().equals(fechaPublicacion);
    }

    public static Predicate<AnuncioDto> buscarPorFechaFinPublicacion(LocalDate fechaFinPublicacion){
        return anuncioDto -> anuncioDto.fechaFinPublicacion().equals(fechaFinPublicacion);
    }

    public static Predicate<AnuncioDto> buscarPorValorInicial(double valorInicial){
        return anuncioDto -> anuncioDto.valorInicial() == valorInicial;
    }

    public static Predicate<AnuncioDto> buscarPorDescripcion(String descripcion){
        return anuncioDto -> anuncioDto.descripcion().contains(descripcion);
    }

    public static Predicate<AnuncioDto> buscarPorEstado(String estado){
        return anuncioDto -> anuncioDto.estado().contains(estado);
    }

    public static Predicate<AnuncioDto> buscarPorTodo(String codigo, ProductoDto productoDto, AnuncianteDto anuncianteDto,
                                                      LocalDate fechaPublicacion, LocalDate fechaFinPublicacion, double valorInicial,
                                                      String descripcion, String estado) {

        Predicate<AnuncioDto> predicado = anuncioDto -> true;

        if( codigo != null && !codigo.isEmpty() ){
            predicado = predicado.and(buscarPorCodigo(codigo));
        }
        if( productoDto.nombreProducto() != null && !productoDto.nombreProducto().isEmpty() && !productoDto.nombreProducto().equals("null")){
            predicado = predicado.and(buscarPorProducto(productoDto.nombreProducto()));
        }
        if(!anuncianteDto.toString().isEmpty() && !anuncianteDto.toString().equals("null")){
            predicado = predicado.and(buscarPorAnunciante(anuncianteDto.toString()));
        }
        if( fechaPublicacion != null ){
            predicado = predicado.and(buscarPorFechaPublicacion(fechaPublicacion));
        }
        if( fechaFinPublicacion != null){
            predicado = predicado.and(buscarPorFechaFinPublicacion(fechaFinPublicacion));
        }
        if( valorInicial != 0){
            predicado = predicado.and(buscarPorValorInicial(valorInicial));
        }
        if( descripcion != null && !descripcion.isEmpty() ){
            predicado = predicado.and(buscarPorDescripcion(descripcion));
        }
        if( estado != null && !estado.isEmpty() && !estado.equals("null")){
            predicado = predicado.and(buscarPorEstado(estado));
        }

        return predicado;
    }
}
