package ru.example.electroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.UserRepository;
import ru.example.electroshop.service.AuditService;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder enc;
    private final AuditService audit;
    public AuthController(UserRepository u,PasswordEncoder e,AuditService a) {
        users=u;
        enc=e;
        audit=a;
    }
    @GetMapping("/login")
    String login() {
        return "login";
    }
    @GetMapping("/register")
    String regForm() {
        return "register";
    }
    @PostMapping("/register")
    String register(@RequestParam String username,@RequestParam String password,@RequestParam String confirmPassword,Model m) {
        username=username.trim();
        if(username.length()<3||username.length()>50||password.length()<8||!password.equals(confirmPassword)||users.existsByUsername(username)) {
            m.addAttribute("error","Проверьте логин (3–50 символов), пароль (от 8 символов), совпадение паролей и уникальность логина.");
            return "register";
        }
        users.save(new AppUser(username,enc.encode(password),Role.READ_ONLY));
        audit.record(null,"Регистрация пользователя: "+username);
        return "redirect:/login?registered";
    }
}
