package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.PujaDto;

import java.util.function.Predicate;

public class PujaUtil {

    public static Predicate<PujaDto> buscarPorCodigo(String codigo){
        return pujaDto -> pujaDto.codigo().contains(codigo);
    }
    public static Predicate<PujaDto> buscarPorProducto(String nombreProducto){
        return pujaDto -> pujaDto.getProductoDto().toString().contains(nombreProducto);
    }

    public static Predicate<PujaDto> buscarPorCodigoAnuncio(String anunciante){
        return pujaDto -> pujaDto.anuncio().contains(anunciante);
    }

    public static Predicate<PujaDto> buscarPorComprador(String tipoProducto){
        return pujaDto -> pujaDto.getCompradorDto().toString().contains(tipoProducto);
    }

    public static Predicate<PujaDto> buscarPorValorPuja(Double valorPuja){
        return pujaDto -> pujaDto.oferta() == valorPuja;
    }

    public static Predicate<PujaDto> buscarPorEstadoAnuncio(String tipoProducto){
        return pujaDto -> pujaDto.estadoAnuncio().contains(tipoProducto);
    }


    public static Predicate<PujaDto> buscarPorTodo(String codigo, String producto, String codigoAnuncio,
                                                   String comprador, Double valorPuja, String estadoAnuncio) {

        Predicate<PujaDto> predicado = pujaDto -> true;

        if( codigo != null && !codigo.isEmpty() ){
            predicado = predicado.and(buscarPorCodigo(codigo));
        }
        if( producto != null && !producto.isEmpty() ){
            predicado = predicado.and(buscarPorProducto(producto));
        }
        if( codigoAnuncio != null && !codigoAnuncio.isEmpty() && !codigoAnuncio.equals("null")){
            predicado = predicado.and(buscarPorComprador(codigoAnuncio));
        }
        if( comprador != null && !comprador.isEmpty() && !comprador.equals("null")){
            predicado = predicado.and(buscarPorComprador(comprador));
        }
        if(valorPuja != null && valorPuja != 0){
            predicado = predicado.and(buscarPorValorPuja(valorPuja));
        }
        if( estadoAnuncio != null && !estadoAnuncio.isEmpty() && !estadoAnuncio.equals("null")){
            predicado = predicado.and(buscarPorEstadoAnuncio(estadoAnuncio));
        }

        return predicado;
    }
}
