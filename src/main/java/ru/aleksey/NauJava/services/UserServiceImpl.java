package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.entities.User;
import ru.aleksey.NauJava.repository.ProductRepository;
import ru.aleksey.NauJava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository)
    {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createUser(Long id, String username)
    {
        var newUser = new User();
        newUser.setId(id);
        newUser.setUsername(username);
        userRepository.create(newUser);
    }

    @Override
    public void updateUser(Long id, String username)
    {
        var updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername(username);
        userRepository.update(updatedUser);
    }

    @Override
    public List<Product> getProducts(Long id)
    {
        return userRepository.get(id).getProducts();
    }

    @Override
    public void addProductToUser(Long userId, Long productId)
    {
        var user = userRepository.get(userId);
        var product = productRepository.get(productId);
        if (user != null && product != null)
        {
            var newProductsList = Stream.concat(user.getProducts().stream(), Stream.of(product)).toList();
            user.setProducts(newProductsList);
        }
    }
}
