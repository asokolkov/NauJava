package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.UserCreateDto;
import ru.aleksey.NauJava.dtos.UserDto;
import ru.aleksey.NauJava.dtos.UserLoginDto;
import ru.aleksey.NauJava.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<ProductDto>> getUserProducts(@PathVariable Long userId)
    {

        var productsDtos = userService.getUserProducts(userId);
        return ResponseEntity.ok(productsDtos);
    }

    @PostMapping("/{userId}/products/{productId}")
    public ResponseEntity<List<ProductDto>> addProductToUser(@PathVariable Long userId, @PathVariable Long productId)
    {
        var productsDtos = userService.addProductToUser(userId, productId);
        return ResponseEntity.ok(productsDtos);
    }

    @PostMapping("/current")
    public ResponseEntity<UserDto> getUserByLoginAndPassword(@RequestBody UserLoginDto userLoginDto)
    {
        var userDto = userService.getUserByLoginAndPassword(userLoginDto);
        return userDto != null
            ? ResponseEntity.ok(userDto)
            : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(UserCreateDto userCreate)
    {
        var userDto = userService.createUser(userCreate);
        return userDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(userDto)
            : ResponseEntity.notFound().build();
    }
}
