package ru.aleksey.NauJava.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aleksey.NauJava.dtos.ProductCreateDto;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.repositories.ProductRepository;

import java.util.UUID;

@SpringBootTest
public class ProductServiceTests
{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductsValid()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var foundProducts = productService.getProducts();

        Assertions.assertNotNull(foundProducts);
        Assertions.assertFalse(foundProducts.isEmpty());
    }

    @Test
    public void testCreateProductValid()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var productCreateDto = new ProductCreateDto();
        productCreateDto.setName(name);
        productCreateDto.setCalories(calories);

        var createdProduct = productService.createProduct(productCreateDto);

        var foundProduct = productRepository.findById(createdProduct.getId()).orElse(null);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(createdProduct.getId(), foundProduct.getId());
        Assertions.assertEquals(name, foundProduct.getName());
        Assertions.assertEquals(calories, foundProduct.getCalories());
    }

    @Test
    public void testCreateProductExisting()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var productCreateDto = new ProductCreateDto();
        productCreateDto.setName(name);
        productCreateDto.setCalories(calories);

        var createdProduct = productService.createProduct(productCreateDto);

        Assertions.assertNull(createdProduct);
    }

    @Test
    public void testFindProductByNameValid()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var foundProduct = productService.getProductByName(name);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getId(), foundProduct.getId());
        Assertions.assertEquals(name, foundProduct.getName());
        Assertions.assertEquals(calories, foundProduct.getCalories());
    }

    @Test
    public void testFindProductByNameInvalid()
    {
        var name = UUID.randomUUID().toString();

        var foundProduct = productService.getProductByName(name);

        Assertions.assertNull(foundProduct);
    }
}
