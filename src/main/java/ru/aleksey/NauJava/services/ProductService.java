package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.dtos.ProductCreateDto;
import ru.aleksey.NauJava.dtos.ProductDto;

import java.util.List;

public interface ProductService
{
    List<ProductDto> getProducts();

    ProductDto createProduct(ProductCreateDto productCreateDto);

    ProductDto getProductByName(String name);

    ProductDto deleteProductById(long productId);
}
