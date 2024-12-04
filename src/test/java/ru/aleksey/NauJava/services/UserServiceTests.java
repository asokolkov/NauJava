package ru.aleksey.NauJava.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aleksey.NauJava.dtos.ProductAddDto;
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

        var productAddDto = new ProductAddDto();
        productAddDto.setName(productName);

        var userProducts = userService.addProductToUser(login, productAddDto);

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

        var productAddDto = new ProductAddDto();
        productAddDto.setName(productName);

        var userProducts = userService.addProductToUser("", productAddDto);

        Assertions.assertNull(userProducts);
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

        var productAddDto = new ProductAddDto();
        productAddDto.setName("");

        var userProducts = userService.addProductToUser(userName, productAddDto);

        Assertions.assertNull(userProducts);
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

        var productAddDto = new ProductAddDto();
        productAddDto.setName(productName);

        var addedUserProducts = userService.addProductToUser(login, productAddDto);

        var userProducts = userService.getUserProducts(login);

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

        var productAddDto = new ProductAddDto();
        productAddDto.setName(productName);

        var addedUserProducts = userService.addProductToUser(user.getName(), productAddDto);

        var userProducts = userService.getUserProducts("");

        Assertions.assertNotNull(userProducts);
        Assertions.assertEquals(0, userProducts.size());
    }
}
