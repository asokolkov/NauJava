package ru.aleksey.NauJava.services;

import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.UserCreateDto;
import ru.aleksey.NauJava.dtos.UserDto;
import ru.aleksey.NauJava.dtos.UserLoginDto;

import java.util.List;

public interface UserService
{
    UserDto createUser(UserCreateDto userCreateDto);

    UserDto getUserByLoginAndPassword(UserLoginDto userLoginDto);

    List<ProductDto> addProductToUser(long userId, long productId);

    List<ProductDto> getUserProducts(long userId);
}
