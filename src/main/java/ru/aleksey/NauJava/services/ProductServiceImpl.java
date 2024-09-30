package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(Long id, String name, Integer calories)
    {
        var newProduct = new Product();
        newProduct.setId(id);
        newProduct.setName(name);
        newProduct.setCalories(calories);
        productRepository.create(newProduct);
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.getAll();
    }

    @Override
    public void updateProduct(Long id, String name, Integer calories)
    {
        var updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName(name);
        updatedProduct.setCalories(calories);
        productRepository.update(updatedProduct);
    }
}
