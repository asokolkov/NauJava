package ru.aleksey.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.dtos.ProductCreateDto;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.mappers.ProductMapper;
import ru.aleksey.NauJava.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> getProducts() {
        var products = (List<Product>) productRepository.findAll();

        var productsDtos = ProductMapper.MAPPER.mapToDtos(products);

        return productsDtos;
    }

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto)
    {
        var name = productCreateDto.getName();
        var calories = productCreateDto.getCalories();

        var existingProduct = productRepository.findByName(name);
        if (existingProduct != null)
        {
            return null;
        }

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var productDto = ProductMapper.MAPPER.mapToDto(product);

        return productDto;
    }

    @Override
    public ProductDto getProductByName(String name)
    {
        var product = productRepository.findByName(name);

        var productDto = ProductMapper.MAPPER.mapToDto(product);

        return productDto;
    }

    @Override
    public ProductDto deleteProductById(long productId)
    {
        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
        {
            return null;
        }

        var productDto = ProductMapper.MAPPER.mapToDto(product);

        productRepository.delete(product);

        return productDto;
    }
}
