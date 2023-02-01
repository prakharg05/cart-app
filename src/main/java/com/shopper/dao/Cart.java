package com.shopper.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = "items")
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy="cart")
    @ToString.Exclude
    private Set<CartItem> items = new HashSet<>();

}
