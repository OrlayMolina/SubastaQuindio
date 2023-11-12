package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.AnuncianteDto;

import java.util.function.Predicate;

public class AnuncianteUtil {

    public static Predicate<AnuncianteDto> buscarPorCedula(String cedula){
        return anuncianteDto -> anuncianteDto.cedula().contains(cedula);
    }

    public static Predicate<AnuncianteDto> buscarPorNombres(String nombres){
        return anuncianteDto -> anuncianteDto.nombre().contains(nombres);
    }

    public static Predicate<AnuncianteDto> buscarPorApellidos(String apellidos){
        return anuncianteDto -> anuncianteDto.apellido().contains(apellidos);
    }

    public static Predicate<AnuncianteDto> buscarPorCorreo(String correo){
        return anuncianteDto -> anuncianteDto.correo().contains(correo);
    }

    public static Predicate<AnuncianteDto> buscarPorTelefono(String telefono){
        return anuncianteDto -> anuncianteDto.telefono().contains(telefono);
    }

    public static Predicate<AnuncianteDto> buscarPorFechaNacimiento(String fechaNacimiento){
        return anuncianteDto -> anuncianteDto.fechaNacimiento().contains(fechaNacimiento);
    }

    public static Predicate<AnuncianteDto> buscarPorUsuarioAsociado(String usuarioAsociado){
        return anuncianteDto -> anuncianteDto.getUsuarioAsociado().usuario().contains(usuarioAsociado);
    }



    public static Predicate<AnuncianteDto> buscarPorTodo(String cedula, String nombres, String apellidos,
                                                         String correo, String telefono, String fechaNacimiento, String usuarioAsociado) {

        Predicate<AnuncianteDto> predicado = anuncianteDto -> true;

        if( cedula != null && !cedula.isEmpty() ){
            predicado = predicado.and(buscarPorCedula(cedula));
        }
        if( nombres != null && !nombres.isEmpty() ){
            predicado = predicado.and(buscarPorNombres(nombres));
        }
        if( apellidos != null && !apellidos.isEmpty() ){
            predicado = predicado.and(buscarPorApellidos(apellidos));
        }
        if( correo != null && !correo.isEmpty() ){
            predicado = predicado.and(buscarPorCorreo(correo));
        }
        if( telefono != null && !telefono.isEmpty() ){
            predicado = predicado.and(buscarPorTelefono(telefono));
        }
        if( fechaNacimiento != null && !fechaNacimiento.isEmpty() ){
            predicado = predicado.and(buscarPorFechaNacimiento(fechaNacimiento));
        }
        if( usuarioAsociado != null && !usuarioAsociado.isEmpty() && !usuarioAsociado.equals("null")){
            predicado = predicado.and(buscarPorUsuarioAsociado(usuarioAsociado));
        }

        return predicado;
    }
}
