package com.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="ADDRESSES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( exclude = {"cart"})
@ToString( exclude = {"cart"})

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private String city;
    private String street;
    private String postalCode;
    private Integer number;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name="ADDRESS_CART", nullable=false)
    private Cart cart;
}
