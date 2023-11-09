package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.function.Predicate;

public class ProductoUtil {

    public static Predicate<ProductoDto> buscarPorCodigo(String codigo){
        return productoDto -> productoDto.codigoUnico().contains(codigo);
    }
    public static Predicate<ProductoDto> buscarPorNombreProducto(String nombreProducto){
        return productoDto -> productoDto.nombreProducto().contains(nombreProducto);
    }

    public static Predicate<ProductoDto> buscarPorNombreAnunciante(String anunciante){
        return productoDto -> productoDto.getAnunciante().toString().contains(anunciante);
    }

    public static Predicate<ProductoDto> buscarPorTipoProducto(String tipoProducto){
        return productoDto -> productoDto.tipoProducto().contains(tipoProducto);
    }


    public static Predicate<ProductoDto> buscarPorTodo(String codigoUnico, String nombreProducto, String nombreAnunciante,
                                                       String tipoProducto) {

        Predicate<ProductoDto> predicado = productoDto -> true;

        if( codigoUnico != null && !codigoUnico.isEmpty() ){
            predicado = predicado.and(buscarPorCodigo(codigoUnico));
        }
        if( nombreProducto != null && !nombreProducto.isEmpty() ){
            predicado = predicado.and(buscarPorNombreProducto(nombreProducto));
        }
        if( nombreAnunciante != null && !nombreAnunciante.isEmpty() && !nombreAnunciante.equals("null")){
            predicado = predicado.and(buscarPorNombreAnunciante(nombreAnunciante));
        }
        if( tipoProducto != null && !tipoProducto.isEmpty() && !tipoProducto.equals("null")){
            predicado = predicado.and(buscarPorTipoProducto(tipoProducto));
        }


        return predicado;
    }
}
