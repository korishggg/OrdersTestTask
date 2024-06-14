package org.example.testtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntityWithAudit {
    private String name;
    private String description;
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    //    ... other fields like phones, and other additional infos ....
}