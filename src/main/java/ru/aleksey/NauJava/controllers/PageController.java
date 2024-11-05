package ru.aleksey.NauJava.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController
{
    @GetMapping
    public String showHomePage(Model model, Authentication authentication)
    {
        var userAuthenticated = authentication != null && authentication.isAuthenticated();

        model.addAttribute("userAuthenticated", userAuthenticated);

        return "homePage";
    }
}
