package ru.aleksey.NauJava.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.entities.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto mapToDto(Product product);

    List<ProductDto> mapToDtos(List<Product> product);
}
