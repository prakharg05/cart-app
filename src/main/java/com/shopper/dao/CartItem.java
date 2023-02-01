package com.shopper.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long cartItemId;

    @ManyToOne(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    @JsonIgnore
    private Cart cart;


    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "product_id")
    private Long productId;
}
