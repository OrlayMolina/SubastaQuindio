package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.CompradorDto;

import java.util.function.Predicate;

public class CompradorUtil {

    public static Predicate<CompradorDto> buscarPorCedula(String cedula){
        return compradorDto -> compradorDto.cedula().contains(cedula);
    }

    public static Predicate<CompradorDto> buscarPorNombres(String nombres){
        return compradorDto -> compradorDto.nombre().contains(nombres);
    }

    public static Predicate<CompradorDto> buscarPorApellidos(String apellidos){
        return compradorDto -> compradorDto.apellido().contains(apellidos);
    }

    public static Predicate<CompradorDto> buscarPorCorreo(String correo){
        return compradorDto -> compradorDto.correo().contains(correo);
    }

    public static Predicate<CompradorDto> buscarPorTelefono(String telefono){
        return compradorDto -> compradorDto.telefono().contains(telefono);
    }

    public static Predicate<CompradorDto> buscarPorFechaNacimiento(String fechaNacimiento){
        return compradorDto -> compradorDto.fechaNacimiento().contains(fechaNacimiento);
    }

    public static Predicate<CompradorDto> buscarPorUsuarioAsociado(String usuarioAsociado){
        return compradorDto -> compradorDto.getUsuarioAsociado().usuario().contains(usuarioAsociado);
    }



    public static Predicate<CompradorDto> buscarPorTodo(String cedula, String nombres, String apellidos,
                                                         String correo, String telefono, String fechaNacimiento, String usuarioAsociado) {

        Predicate<CompradorDto> predicado = compradorDto -> true;

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
