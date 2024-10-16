package ru.aleksey.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(String name, Integer calories)
    {
        var existingProduct = productRepository.findByName(name);
        if (existingProduct != null)
        {
            return null;
        }

        var product = new Product();
        product.setName(name);
        product.setCalories(calories);

        productRepository.save(product);

        return product;
    }

    @Override
    public Product getProductByName(String name)
    {
        return productRepository.findByName(name);
    }
}
