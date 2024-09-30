package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.entities.Product;

import java.util.List;

public interface UserService
{
    void createUser(Long id, String username);

    void updateUser(Long id, String username);

    List<Product> getProducts(Long id);

    void addProductToUser(Long userId, Long productId);
}
