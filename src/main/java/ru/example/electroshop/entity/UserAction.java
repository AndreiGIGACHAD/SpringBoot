package ru.example.electroshop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="user_actions")
public class UserAction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;
    @Column(nullable=false)

    private LocalDateTime actionDate;
    @Column(nullable=false,length=1000)

    private String description;
    private String username;
    public UserAction() {
    }
    public UserAction(String u,String d) {
        username=u;
        description=d;
        actionDate=LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public LocalDateTime getActionDate() {
        return actionDate;
    }
    public String getDescription() {
        return description;
    }
    public String getUsername() {
        return username;
    }
}
