package ru.aleksey.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksey.NauJava.services.ProductService;
import ru.aleksey.NauJava.services.ReportService;
import ru.aleksey.NauJava.services.UserService;

@Controller
public class PageController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;

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

    @GetMapping("/my-products")
    public String showMyProductsPage(Model model, Authentication authentication)
    {
        var username = authentication.getName();

        var productsDtos = userService.getUserProducts(username);

        model.addAttribute("products", productsDtos);

        return "myProductsPage";
    }

    @GetMapping("/my-reports")
    public String showMyReportsPage(Model model, Authentication authentication)
    {
        var username = authentication.getName();

        var reportsDtos = userService.getUserReports(username);

        model.addAttribute("reports", reportsDtos);

        return "myReportsPage";
    }

    @GetMapping("/my-reports/{reportId}")
    public String getUsersReport(@PathVariable long reportId, Model model)
    {
        var reportDto = reportService.getReport(reportId);

        model.addAttribute("reportContent", reportDto.getContent());

        return "myReportPage";
    }

    @GetMapping("/register")
    public String getRegistration(Authentication authentication, RedirectAttributes redirectAttributes)
    {
        var userAuthenticated = authentication != null && authentication.isAuthenticated();

        return userAuthenticated ? "redirect:/" : "registerPage";
    }
}
