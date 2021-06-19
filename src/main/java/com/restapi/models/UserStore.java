package com.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cart"})
@ToString(exclude = {"cart"})
public class UserStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private Integer quantity;

    @ManyToOne
    @JsonBackReference
    private Cart cart;

    private int productCode;

    private int shopCode;

    private String username;

    public UserStore(Integer quantity, int productCode, int shopCode, String username) {
        this.quantity = quantity;
        this.productCode = productCode;
        this.shopCode = shopCode;
        this.username = username;
    }

}

