package ru.example.electroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.electroshop.repository.*;

@Controller
public class HomeController {
    private final ProductRepository products;
    private final CategoryRepository categories;
    public HomeController(ProductRepository p,CategoryRepository c) {
        products=p;
        categories=c;
    }
    @GetMapping("/")
    String home(Model m,org.springframework.security.core.Authentication a) {
        m.addAttribute("username",a.getName());
        m.addAttribute("productCount",products.count());
        m.addAttribute("categoryCount",categories.count());
        m.addAttribute("lowStock",products.findAll().stream().filter(p->p.getStock()<5).count());
        return "index";
    }
    @GetMapping("/about")
    String about() {
        return "about";
    }
    @GetMapping("/calculator")
    String calc() {
        return "calculator";
    }
    @PostMapping("/calculator")
    String calculate(@RequestParam double price,@RequestParam double percent,@RequestParam double markup,Model m) {
        m.addAttribute("price",price);
        m.addAttribute("percent",percent);
        m.addAttribute("markup",markup);
        m.addAttribute("discounted",Math.max(0,price)*(1-Math.max(0,Math.min(percent,100))/100));
        m.addAttribute("finalPrice",Math.max(0,price)*(1+Math.max(0,Math.min(markup,500))/100));
        return "calculator";
    }
}
