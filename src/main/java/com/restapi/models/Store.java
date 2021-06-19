package com.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( exclude = {"affiliateCode"})
@ToString(exclude = { "affiliateCode" })
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="PRODUCT_CODE", nullable=false)
    private Product productCode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="AFFILIATE_CODE", nullable=false)
    private Shop affiliateCode;

}
