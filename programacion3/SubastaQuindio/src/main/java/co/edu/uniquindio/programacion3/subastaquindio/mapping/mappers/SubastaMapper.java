package co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers;


import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDTO;
import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubastaMapper {

    SubastaMapper INSTANCE = Mappers.getMapper(SubastaMapper.class);

    @Named("productoToProductoDto")
    ProductoDTO productoToProductoDto(Producto producto);

    Producto productoDtoToProducto(ProductoDTO productoDto);

    @IterableMapping(qualifiedByName = "productoToProductoDto")
    List<ProductoDTO> getProductoDto(List<Producto> listaProductos);


}
