package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.ProductCreateDto;
import ru.aleksey.NauJava.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateDto productCreate)
    {
        var productDto = productService.createProduct(productCreate);
        return productDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(productDto)
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable long productId)
    {
        var productDto = productService.deleteProductById(productId);
        return productDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(productDto)
            : ResponseEntity.notFound().build();
    }
}
