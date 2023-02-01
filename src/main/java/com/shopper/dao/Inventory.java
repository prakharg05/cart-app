package com.shopper.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Inventory {


    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long inventoryId;


    @Column(name = "quantity")
    private Integer quantity;
}