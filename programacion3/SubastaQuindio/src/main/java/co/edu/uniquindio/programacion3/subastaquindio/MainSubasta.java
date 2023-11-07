package co.edu.uniquindio.programacion3.subastaquindio;

import co.edu.uniquindio.programacion3.subastaquindio.controller.ModelFactoryController;
import co.edu.uniquindio.programacion3.subastaquindio.mapping.dto.ProductoDto;

import java.util.List;

public class MainSubasta {

    public static void main(String[] args) {

        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

        ProductoDto productoDto = new ProductoDto(
                "1",
                "Renault Logan",
                "Carro nuevo",
                "TECNOLOGIA",
                "",
                "Yarley Camilo Mejía",
                "2023-08-12",
                "2023-08-21",
                1223.00
        );

        if(modelFactoryController.agregarProducto(productoDto)){
            System.out.println("No existe se agrgeo correctamente");
        }else{
            System.out.println("Ya existe");
        }

        List<ProductoDto> empleadoDtoList = modelFactoryController.obtenerProductos();
        empleadoDtoList.forEach(System.out::println);
    }


}
