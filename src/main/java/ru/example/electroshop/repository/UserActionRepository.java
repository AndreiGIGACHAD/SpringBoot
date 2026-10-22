package ru.example.electroshop.repository;

import ru.example.electroshop.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction,
Long> {
    List<UserAction> findAll(Sort sort);
}
