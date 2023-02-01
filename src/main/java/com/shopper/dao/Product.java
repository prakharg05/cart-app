package com.shopper.dao;

import com.shopper.service.InventoryService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

    @Column(name = "product_price")
    private Integer productPrice;
}