package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncioDto;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.util.function.Predicate;

public class ListaAnuncioUtil {

    public static Predicate<AnuncioDto> buscarPorCodigo(String codigo){
        return anuncioDto -> anuncioDto.codigo().contains(codigo);
    }

    public static Predicate<AnuncioDto> buscarPorTodo(String codigo) {

        Predicate<AnuncioDto> predicado = anuncioDto -> true;

        if( codigo != null && !codigo.isEmpty() ){
            predicado = predicado.and(buscarPorCodigo(codigo));
        }

        return predicado;
    }
}
