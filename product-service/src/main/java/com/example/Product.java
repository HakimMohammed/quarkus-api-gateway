package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Product extends PanacheEntity {
    public String name;
    public double price;
    public String description;
}
