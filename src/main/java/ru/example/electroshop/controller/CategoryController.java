package ru.example.electroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.*;
import ru.example.electroshop.service.AuditService;

@Controller @RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categories;
    private final ProductRepository products;
    private final UserRepository users;
    private final AuditService audit;
    public CategoryController(CategoryRepository c,ProductRepository p,UserRepository u,AuditService a) {
        categories=c;
        products=p;
        users=u;
        audit=a;
    }
    @GetMapping
    String list(Model m,Authentication a) {
        m.addAttribute("categories",AccessHelper.admin(a)||AccessHelper.readOnly(a)?categories.findAll():categories.findByCreatedBy(AccessHelper.current(a,users)));
        return "categories";
    }
    @GetMapping("/new")
    String form(Model m,Authentication a) {
        AccessHelper.write(a);
        m.addAttribute("category",new Category());
        return "category-form";
    }
    @PostMapping
    String create(@ModelAttribute
    Category c,Authentication a,Model m) {
        AccessHelper.write(a);
        if(c.getName()==null||c.getName().isBlank()||categories.existsByNameIgnoreCase(c.getName())) {
            m.addAttribute("error","Название обязательно и должно быть уникальным.");
            m.addAttribute("category",c);
            return "category-form";
        }
        c.setCreatedBy(AccessHelper.current(a,users));
        categories.save(c);
        audit.record(a,"Добавлена категория: "+c.getName());
        return "redirect:/categories";
    }
    @GetMapping("/{id}/edit")
    String edit(@PathVariable
    Long id,Model m,Authentication a) {
        AccessHelper.write(a);
        m.addAttribute("category",owned(id,a));
        return "category-form";
    }
    @PostMapping("/{id}")
    String update(@PathVariable
    Long id,@ModelAttribute
    Category f,Authentication a,Model m) {
        AccessHelper.write(a);
        Category c=owned(id,a);
        if(f.getName()==null||f.getName().isBlank()||(categories.existsByNameIgnoreCase(f.getName())&&!f.getName().equalsIgnoreCase(c.getName()))) {
            m.addAttribute("error","Название обязательно и должно быть уникальным.");
            m.addAttribute("category",f);
            return "category-form";
        }
        c.setName(f.getName());
        c.setDescription(f.getDescription());
        categories.save(c);
        audit.record(a,"Изменена категория: "+c.getName());
        return "redirect:/categories";
    }
    @PostMapping("/{id}/delete")
    String delete(@PathVariable
    Long id,Authentication a,Model m) {
        AccessHelper.write(a);
        Category c=owned(id,a);
        if(products.findAll().stream().anyMatch(p->p.getCategory().getId().equals(c.getId()))) {
            throw new IllegalArgumentException("Нельзя удалить категорию, в которой есть товары.");
        }
        categories.delete(c);
        audit.record(a,"Удалена категория: "+c.getName());
        return "redirect:/categories";
    }
    private Category owned(Long id,Authentication a) {
        Category c=categories.findById(id).orElseThrow();
        AccessHelper.own(c.getCreatedBy(),a);
        return c;
    }
}
