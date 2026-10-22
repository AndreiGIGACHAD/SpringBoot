package ru.example.electroshop.entity;

import jakarta.persistence.*;

@Entity @Table(name="categories")
public class Category {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;
    @Column(nullable=false,unique=true,length=100)

    private String name;
    @Column(length=1000)

    private String description;
    @ManyToOne(fetch=FetchType.LAZY)

    private AppUser createdBy;
    public Category() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long v) {
        id=v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description=v;
    }
    public AppUser getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(AppUser v) {
        createdBy=v;
    }
}
