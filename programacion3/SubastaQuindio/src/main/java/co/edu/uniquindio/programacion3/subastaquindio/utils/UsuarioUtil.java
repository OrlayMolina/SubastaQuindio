package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.UsuarioDto;

import java.util.function.Predicate;

public class UsuarioUtil {

    public static Predicate<UsuarioDto> buscarPorUsuario(String usuario){
        return usuarioDto -> usuarioDto.usuario().contains(usuario);
    }


    public static Predicate<UsuarioDto> buscarPorContrasenia(String contrasenia){
        return usuarioDto -> usuarioDto.contrasenia().contains(contrasenia);
    }

    public static Predicate<UsuarioDto> buscarPorTodo(String usuario, String contrasenia) {

        Predicate<UsuarioDto> predicado = usuarioDto -> true;

        if( usuario != null && !usuario.isEmpty() ){
            predicado = predicado.and(buscarPorUsuario(usuario));
        }
        if( contrasenia != null && !contrasenia.isEmpty() ){
            predicado = predicado.and(buscarPorContrasenia(contrasenia));
        }

        return predicado;
    }
}
