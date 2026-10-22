package ru.example.electroshop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="users")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;
    @Column(nullable=false,unique=true,length=50)

    private String username;
    @Column(nullable=false)

    private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false)

    private Role role=Role.READ_ONLY;
    @Column(nullable=false)

    private LocalDateTime registeredAt=LocalDateTime.now();
    public AppUser() {
    }
    public AppUser(String u,String p,Role r) {
        username=u;
        password=p;
        role=r;
    }
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String v) {
        username=v;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String v) {
        password=v;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role v) {
        role=v;
    }
    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
