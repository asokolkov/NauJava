package ru.aleksey.NauJava.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aleksey.NauJava.dtos.UserCreateDto;
import ru.aleksey.NauJava.dtos.UserLoginDto;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.entities.User;
import ru.aleksey.NauJava.repositories.ProductRepository;
import ru.aleksey.NauJava.repositories.UserRepository;

import java.util.UUID;

@SpringBootTest
public class UserServiceTests
{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUserValid()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var userCreateDto = new UserCreateDto();
        userCreateDto.setName(name);
        userCreateDto.setLogin(login);
        userCreateDto.setPassword(password);

        var createdUser = userService.createUser(userCreateDto);

        var foundUser = userRepository.findById(createdUser.getId()).orElse(null);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(createdUser.getId(), foundUser.getId());
        Assertions.assertEquals(name, foundUser.getName());
        Assertions.assertEquals(login, foundUser.getLogin());
        Assertions.assertEquals(password, foundUser.getPassword());
    }

    @Test
    public void testCreateUserExisting()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var userCreateDto = new UserCreateDto();
        userCreateDto.setName(name);
        userCreateDto.setLogin(login);
        userCreateDto.setPassword(password);

        var createdUser = userService.createUser(userCreateDto);

        Assertions.assertNull(createdUser);
    }

    @Test
    public void testGetUserByLoginAndPassportValid()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var userLoginDto = new UserLoginDto();
        userLoginDto.setLogin(login);
        userLoginDto.setPassword(password);

        var foundUser = userService.getUserByLoginAndPassword(userLoginDto);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(name, foundUser.getName());
        Assertions.assertEquals(login, foundUser.getLogin());
    }

    @Test
    public void testGetUserByLoginAndPassportInvalidLogin()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var userLoginDto = new UserLoginDto();
        userLoginDto.setLogin(UUID.randomUUID().toString());
        userLoginDto.setPassword(password);

        var foundUser = userService.getUserByLoginAndPassword(userLoginDto);

        Assertions.assertNull(foundUser);
    }

    @Test
    public void testGetUserByLoginAndPassportInvalidPassport()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var userLoginDto = new UserLoginDto();
        userLoginDto.setLogin(login);
        userLoginDto.setPassword(UUID.randomUUID().toString());

        var foundUser = userService.getUserByLoginAndPassword(userLoginDto);

        Assertions.assertNull(foundUser);
    }

    @Test
    public void testAddProductToUserValid()
    {
        var userName = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var productName = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(productName);
        product.setCalories(calories);

        productRepository.save(product);

        var userProducts = userService.addProductToUser(user.getId(), product.getId());

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(1, userProducts.size());
        Assertions.assertEquals(productName, userProducts.getFirst().getName());
        Assertions.assertEquals(calories, userProducts.getFirst().getCalories());
    }

    @Test
    public void testAddProductToUserInvalidUser()
    {
        var userName = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var productName = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(productName);
        product.setCalories(calories);

        productRepository.save(product);

        var userProducts = userService.addProductToUser(-1L, product.getId());

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(0, userProducts.size());
    }

    @Test
    public void testAddProductToUserInvalidProduct()
    {
        var userName = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var productName = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(productName);
        product.setCalories(calories);

        productRepository.save(product);

        var userProducts = userService.addProductToUser(user.getId(), -1L);

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(0, userProducts.size());
    }

    @Test
    public void testGetUserProductsValid()
    {
        var userName = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var productName = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(productName);
        product.setCalories(calories);

        productRepository.save(product);

        var addedUserProducts = userService.addProductToUser(user.getId(), product.getId());

        var userProducts = userService.getUserProducts(user.getId());

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(1, userProducts.size());
        Assertions.assertEquals(productName, userProducts.getFirst().getName());
        Assertions.assertEquals(calories, userProducts.getFirst().getCalories());
    }

    @Test
    public void testGetUserProductsInvalid()
    {
        var userName = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var productName = UUID.randomUUID().toString();
        var calories = 10;

        var product = new Product();
        product.setName(productName);
        product.setCalories(calories);

        productRepository.save(product);

        var addedUserProducts = userService.addProductToUser(user.getId(), product.getId());

        var userProducts = userService.getUserProducts(-1L);

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(0, userProducts.size());
    }
}
