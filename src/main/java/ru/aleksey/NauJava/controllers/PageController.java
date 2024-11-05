package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksey.NauJava.services.ProductService;

@Controller
public class PageController
{
    @Autowired
    private ProductService productService;

    @GetMapping
    public String showHomePage(Model model, Authentication authentication)
    {
        var userAuthenticated = authentication != null && authentication.isAuthenticated();

        model.addAttribute("userAuthenticated", userAuthenticated);

        return "homePage";
    }

    @GetMapping("/products")
    public String showProductsPage(Model model, Authentication authentication)
    {
        var productsDtos = productService.getProducts();
        var userAuthenticated = authentication != null && authentication.isAuthenticated();

        model.addAttribute("products", productsDtos);
        model.addAttribute("userAuthenticated", userAuthenticated);

        return "productsPage";
    }

    @GetMapping("/register")
    public String getRegistration(Authentication authentication, RedirectAttributes redirectAttributes)
    {
        var userAuthenticated = authentication != null && authentication.isAuthenticated();

        return userAuthenticated ? "redirect:/" : "registerPage";
    }
}
