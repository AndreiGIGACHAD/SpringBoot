package ru.example.electroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.*;
import ru.example.electroshop.service.AuditService;
import org.springframework.data.domain.Sort;

@Controller @RequestMapping("/admin")
public class AdminController {
    private final UserRepository users;
    private final UserActionRepository actions;
    private final AuditService audit;
    public AdminController(UserRepository u,UserActionRepository a,AuditService l) {
        users=u;
        actions=a;
        audit=l;
    }
    @GetMapping("/users")
    String users(Model m) {
        m.addAttribute("users",users.findAll());
        m.addAttribute("roles",Role.values());
        return "users";
    }
    @PostMapping("/users/{id}/role")
    String role(@PathVariable
    Long id,@RequestParam Role role,Authentication auth) {
        AppUser u=users.findById(id).orElseThrow();
        if(u.getUsername().equals(auth.getName())&&role!=Role.ADMIN)throw new IllegalArgumentException("Нельзя снять собственную роль ADMIN");
        u.setRole(role);
        users.save(u);
        audit.record(auth,"Назначена роль "+role+" пользователю "+u.getUsername());
        return "redirect:/admin/users";
    }
    @GetMapping("/logs")
    String logs(Model m) {
        m.addAttribute("actions",actions.findAll(Sort.by(Sort.Direction.DESC,"actionDate")));
        return "logs";
    }
}
