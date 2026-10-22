package ru.example.electroshop.repository;

import ru.example.electroshop.entity.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,
Long> {
    List<Category> findByCreatedBy(AppUser user);
    boolean existsByNameIgnoreCase(String name);
}
