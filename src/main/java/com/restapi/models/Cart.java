package com.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CARTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( exclude = {"userStores","address", "userModel"})
@ToString(exclude = {"userModel"})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @OneToOne
    @JoinColumn(name="USERMODEL_CODE", nullable=false)
    @JsonBackReference
    private UserModel userModel;

    @OneToOne(mappedBy = "cart", optional=true )
    @JsonManagedReference
    private Address address;

    @OneToMany
    @JsonManagedReference
    private List<UserStore> userStores;

    private int totalCost;
}
