package ru.example.electroshop.repository;

import ru.example.electroshop.entity.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,
Long> {
    List<Product> findByCreatedBy(AppUser user);
    boolean existsBySku(String sku);
}
