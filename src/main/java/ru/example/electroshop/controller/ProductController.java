package ru.example.electroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.example.electroshop.entity.*;
import ru.example.electroshop.repository.*;
import ru.example.electroshop.service.AuditService;

@Controller @RequestMapping("/products")
public class ProductController {
    private final ProductRepository products;
    private final CategoryRepository categories;
    private final UserRepository users;
    private final AuditService audit;
    public ProductController(ProductRepository p,CategoryRepository c,UserRepository u,AuditService a) {
        products=p;
        categories=c;
        users=u;
        audit=a;
    }
    @GetMapping
    String list(Model m,Authentication a) {
        m.addAttribute("products",AccessHelper.admin(a)||AccessHelper.readOnly(a)?products.findAll():products.findByCreatedBy(AccessHelper.current(a,users)));
        return "products";
    }
    @GetMapping("/new")
    String form(Model m,Authentication a) {
        AccessHelper.write(a);
        m.addAttribute("product",new Product());
        m.addAttribute("categories",categoryList(a));
        return "product-form";
    }
    @PostMapping
    String create(@ModelAttribute
    Product p,Authentication a,Model m) {
        AccessHelper.write(a);
        if(!valid(p)||p.getSku()==null||p.getSku().isBlank()||products.existsBySku(p.getSku())) {
            m.addAttribute("error","Проверьте поля. Артикул должен быть уникальным, цена и остаток — неотрицательными.");
            m.addAttribute("product",p);
            m.addAttribute("categories",categoryList(a));
            return "product-form";
        }
        p.setCategory(categories.findById(p.getCategory().getId()).orElseThrow());
        p.setCreatedBy(AccessHelper.current(a,users));
        products.save(p);
        audit.record(a,"Добавлен товар: "+p.getName());
        return "redirect:/products";
    }
    @GetMapping("/{id}/edit")
    String edit(@PathVariable
    Long id,Model m,Authentication a) {
        AccessHelper.write(a);
        m.addAttribute("product",owned(id,a));
        m.addAttribute("categories",categoryList(a));
        return "product-form";
    }
    @PostMapping("/{id}")
    String update(@PathVariable
    Long id,@ModelAttribute
    Product f,Authentication a,Model m) {
        AccessHelper.write(a);
        Product p=owned(id,a);
        if(!valid(f)||f.getSku()==null||f.getSku().isBlank()||(products.existsBySku(f.getSku())&&!f.getSku().equals(p.getSku()))) {
            m.addAttribute("error","Проверьте поля и уникальность артикула.");
            m.addAttribute("product",f);
            m.addAttribute("categories",categoryList(a));
            return "product-form";
        }
        p.setName(f.getName());
        p.setBrand(f.getBrand());
        p.setModel(f.getModel());
        p.setSku(f.getSku());
        p.setPrice(f.getPrice());
        p.setStock(f.getStock());
        p.setDescription(f.getDescription());
        p.setCategory(categories.findById(f.getCategory().getId()).orElseThrow());
        products.save(p);
        audit.record(a,"Изменён товар: "+p.getName());
        return "redirect:/products";
    }
    @PostMapping("/{id}/delete")
    String delete(@PathVariable
    Long id,Authentication a) {
        AccessHelper.write(a);
        Product p=owned(id,a);
        products.delete(p);
        audit.record(a,"Удалён товар: "+p.getName());
        return "redirect:/products";
    }
    private Product owned(Long id,Authentication a) {
        Product p=products.findById(id).orElseThrow();
        AccessHelper.own(p.getCreatedBy(),a);
        return p;
    }
    private boolean valid(Product p) {
        return p.getName()!=null&&!p.getName().isBlank()&&p.getCategory()!=null&&p.getCategory().getId()!=null&&p.getPrice()>=0&&p.getStock()>=0;
    }
    private java.util.List<Category> categoryList(Authentication a) {
        return AccessHelper.admin(a)||AccessHelper.readOnly(a)?categories.findAll():categories.findByCreatedBy(AccessHelper.current(a,users));
    }
}
