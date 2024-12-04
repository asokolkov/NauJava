package ru.aleksey.NauJava.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aleksey.NauJava.entities.Product;

import java.util.UUID;

@SpringBootTest
public class ProductRepositoryTests
{
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindProductByNameValid()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var foundProduct = productRepository.findByName(name);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getId(), foundProduct.getId());
        Assertions.assertEquals(name, foundProduct.getName());
        Assertions.assertEquals(calories, foundProduct.getCalories());
    }

    @Test
    public void testFindProductByNameInvalid()
    {
        var name = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        var foundProduct = productRepository.findByName(UUID.randomUUID().toString());

        Assertions.assertNull(foundProduct);
    }
}
