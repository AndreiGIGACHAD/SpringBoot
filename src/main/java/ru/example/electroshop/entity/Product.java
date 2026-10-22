package ru.example.electroshop.entity;

import jakarta.persistence.*;

@Entity @Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;
    @Column(nullable=false,length=150)

    private String name;
    @Column(length=100)

    private String brand;
    @Column(length=100)

    private String model;
    @Column(nullable=false,unique=true,length=40)

    private String sku;
    @ManyToOne(fetch=FetchType.EAGER,optional=false)

    private Category category;
    @Column(nullable=false)

    private double price;
    @Column(nullable=false)

    private int stock;
    @Column(length=1500)

    private String description;
    @ManyToOne(fetch=FetchType.LAZY)

    private AppUser createdBy;
    public Product() {
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
    public String getBrand() {
        return brand;
    }
    public void setBrand(String v) {
        brand=v;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String v) {
        model=v;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String v) {
        sku=v;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category v) {
        category=v;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double v) {
        price=v;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int v) {
        stock=v;
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
