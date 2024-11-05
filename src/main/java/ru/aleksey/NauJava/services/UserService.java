package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.dtos.*;

import java.util.List;

public interface UserService
{
    List<UserDto> getUsers();

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto getUserByLoginAndPassword(UserLoginDto userLoginDto);

    List<ProductDto> addProductToUser(String username, ProductAddDto productAddDto);

    List<ProductDto> getUserProducts(String username);
}
