package com.restapi.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SHOPS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( exclude = {"store"})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private String name;
    private Boolean inactive;

    @JsonManagedReference
    @OneToMany(mappedBy = "affiliateCode", cascade = CascadeType.ALL)
    private List<Store> store;
}