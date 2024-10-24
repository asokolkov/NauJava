package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aleksey.NauJava.dtos.ProductDto;
import ru.aleksey.NauJava.dtos.ProductCreateDto;
import ru.aleksey.NauJava.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView getProducts() {
        var productsDtos = productService.getProducts();

        var modelAndView = new ModelAndView();
        modelAndView.setViewName("productsList");

        modelAndView.addObject("products", productsDtos);

        return modelAndView;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String name)
    {
        var productDto = productService.getProductByName(name);
        return productDto != null
            ? ResponseEntity.ok(productDto)
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateDto productCreate)
    {
        var productDto = productService.createProduct(productCreate);
        return productDto != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(productDto)
            : ResponseEntity.notFound().build();
    }
}
