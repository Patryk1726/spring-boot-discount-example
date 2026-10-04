package org.example.pl.kurs.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Integer id;
    private String customerName;
    private Double basePrice;

    public Order(Integer id, String customerName, Double basePrice) {
        this.id = id;
        this.customerName = customerName;
        this.basePrice = basePrice;
    }
}
