package ru.aleksey.NauJava.services;

import java.util.List;

import ru.aleksey.NauJava.entities.Product;

public interface ProductService
{
    void createProduct(Long id, String name, Integer calories);

    List<Product> getAllProducts();

    void updateProduct(Long id, String name, Integer calories);
}
