package ru.aleksey.NauJava.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.UserCreateDto;
import ru.aleksey.NauJava.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey.NauJava.dtos.UserLoginDto;
import ru.aleksey.NauJava.entities.User;
import ru.aleksey.NauJava.entities.UserProduct;
import ru.aleksey.NauJava.enums.UserRole;
import ru.aleksey.NauJava.mappers.ProductMapper;
import ru.aleksey.NauJava.mappers.UserMapper;
import ru.aleksey.NauJava.repositories.ProductRepository;
import ru.aleksey.NauJava.repositories.UserProductRepository;
import ru.aleksey.NauJava.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserProductRepository userProductRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserCreateDto userCreateDto)
    {
        var name = userCreateDto.getName();
        var login = userCreateDto.getLogin();
        var password = userCreateDto.getPassword();
        var encodedPassword = passwordEncoder.encode(password);

        var existingUser = userRepository.findByLogin(login);
        if (existingUser != null)
        {
            return null;
        }

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(encodedPassword);
        user.setRole(UserRole.DEFAULT);

        userRepository.save(user);

        var userDto = UserMapper.MAPPER.mapToDto(user);

        return userDto;
    }

    @Override
    public UserDto getUserByLoginAndPassword(UserLoginDto userLoginDto)
    {
        var login = userLoginDto.getLogin();
        var password = userLoginDto.getPassword();

        var user = userRepository.findByLoginAndPassword(login, password);

        var userDto = UserMapper.MAPPER.mapToDto(user);

        return userDto;
    }

    @Override
    @Transactional
    public List<ProductDto> addProductToUser(long userId, long productId)
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

        var products = user
            .getProducts()
            .stream()
            .map(UserProduct::getProduct)
            .toList();

        var productsDtos = ProductMapper.MAPPER.mapToDtos(products);

        return productsDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getUserProducts(long userId)
    {
        var products = userRepository
            .findById(userId)
            .map(User::getProducts)
            .orElse(Collections.emptyList())
            .stream()
            .map(UserProduct::getProduct)
            .toList();

        var productsDtos = ProductMapper.MAPPER.mapToDtos(products);

        return productsDtos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var user = userRepository.findByLogin(username);

        var userRoles = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        var userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), userRoles);

        return userDetails;
    }
}
