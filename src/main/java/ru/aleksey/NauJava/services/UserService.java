package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.entities.User;

import java.util.List;

public interface UserService
{
    User createUser(String login, String name, String password);

    User getUserByLoginAndPassword(String login, String password);

    List<Product> addProductToUser(Long userId, Long productId);

    List<Product> getUserProducts(Long userId);
}
