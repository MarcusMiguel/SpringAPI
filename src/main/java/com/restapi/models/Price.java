package com.restapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="PRICES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( exclude = {"productPrice"})
@ToString(exclude = { "productPrice" })
public class Price
{
    @Id
    private Integer code;
    private Long value;
    private Date startDate;
    private Date endDate;
    private Boolean indInactive;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name="PRODUCT_ID", nullable=false)
    private Product productPrice;
}
