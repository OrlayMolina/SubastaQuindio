package co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;

public record PujaDto(
        String codigo,
        String producto,
        String anuncio,
        String comprador,
        double oferta,
        String estadoAnuncio) {

    public ProductoDto getProductoDto(){
        return new ProductoDto("", producto, "", "", "");
    }

    public AnuncianteDto getCompradorDto(){
        return new AnuncianteDto(comprador,"", "", "","","","","");
    }

    public AnuncioDto getAnuncioDto(){
        return new AnuncioDto("","", "", "","",0,"","");
    }
}
