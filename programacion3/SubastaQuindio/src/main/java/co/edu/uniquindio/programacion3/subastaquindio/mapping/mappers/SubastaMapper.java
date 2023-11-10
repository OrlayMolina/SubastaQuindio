package co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers;


import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubastaMapper {

    SubastaMapper INSTANCE = Mappers.getMapper(SubastaMapper.class);

    @Named("productoToProductoDto")
    ProductoDto productoToProductoDto(Producto producto);

    //@Mappiing(target ="producto", source="productoToProductoDto")
    Producto productoDtoToProducto(ProductoDto productoDto);

    @IterableMapping(qualifiedByName = "productoToProductoDto")
    List<ProductoDto> getProductoDto(List<Producto> listaProductos);

    @Named("usuarioToUsuarioDto")
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuarioDto(List<Usuario> listaUsuarios);

    @Named("anuncianteToAnuncianteDto")
    AnuncianteDto anuncianteToAnuncianteDto(Anunciante anunciante);

    Anunciante anuncianteDtoToAnunciante(AnuncianteDto anuncianteDto);

    @IterableMapping(qualifiedByName = "anuncianteToAnuncianteDto")
    List<AnuncianteDto> getAnuncianteDto(List<Anunciante> listaAnunciantes);

    @Named("compradorToCompradorDto")
    CompradorDto compradorToCompradorDto(Comprador comprador);

    Comprador compradorDtoToComprador(CompradorDto compradorDto);

    @IterableMapping(qualifiedByName = "compradorToCompradorDto")
    List<CompradorDto> getCompradorDto(List<Comprador> listaCompradores);

    @Named("anuncioToAnuncioDto")
    AnuncioDto anuncioToAnuncioDto(Anuncio anuncio);

    Anuncio anuncioDtoToAnuncio(AnuncioDto anuncioDto);

    @IterableMapping(qualifiedByName = "anuncioToAnuncioDto")
    List<AnuncioDto> getAnuncioDto(List<Anuncio> listaAnuncios);

    @Named("pujaToPujaDto")
    PujaDto pujaToPujaDto(Puja puja);

    Puja pujaDtoToPuja(PujaDto pujaDto);

    @IterableMapping(qualifiedByName = "pujaToPujaDto")
    List<PujaDto> getPujaDto(List<Puja> listaPujas);

}
