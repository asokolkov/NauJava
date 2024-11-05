package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.aleksey.NauJava.dtos.ProductAddDto;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.UserCreateDto;
import ru.aleksey.NauJava.dtos.UserDto;
import ru.aleksey.NauJava.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/current/products")
    public ResponseEntity<List<ProductDto>> addProductToUser(@RequestBody ProductAddDto productAddDto, Authentication authentication)
    {
        var username = authentication.getName();

        var productsDtos = userService.addProductToUser(username, productAddDto);

        return productsDtos != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(productsDtos)
            : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(UserCreateDto userCreateDto)
    {
        var userDto = userService.createUser(userCreateDto);
        return userDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(userDto)
            : ResponseEntity.notFound().build();
    }
}
