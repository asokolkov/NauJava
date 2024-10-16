package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.entities.Product;

public interface ProductService
{
    Product createProduct(String name, Integer calories);

    Product getProductByName(String name);
}
