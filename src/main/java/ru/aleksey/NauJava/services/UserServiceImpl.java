package ru.aleksey.NauJava.services;

import org.springframework.transaction.annotation.Transactional;
import ru.aleksey.NauJava.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.entities.User;
import ru.aleksey.NauJava.entities.UserProduct;
import ru.aleksey.NauJava.repositories.ProductRepository;
import ru.aleksey.NauJava.repositories.UserProductRepository;
import ru.aleksey.NauJava.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserProductRepository userProductRepository;


    @Override
    public User createUser(String login, String name, String password)
    {
        var existingUser = userRepository.findByLogin(login);
        if (existingUser != null)
        {
            return null;
        }

        var user = new User();
        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password)
    {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    @Transactional
    public List<Product> addProductToUser(Long userId, Long productId)
    {
        var user = userRepository.findById(userId).orElse(null);
        if (user == null)
        {
            return Collections.emptyList();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
        {
            return Collections.emptyList();
        }

        var userProduct = new UserProduct();
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setEatenAt(OffsetDateTime.now());

        userProductRepository.save(userProduct);

        return user
                .getProducts()
                .stream()
                .map(UserProduct::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getUserProducts(Long userId)
    {
        return userRepository
                .findById(userId)
                .map(User::getProducts)
                .orElse(Collections.emptyList())
                .stream()
                .map(UserProduct::getProduct)
                .collect(Collectors.toList());
    }
}
