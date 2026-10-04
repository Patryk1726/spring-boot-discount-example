package org.example.pl.kurs.example.service;

import org.springframework.stereotype.Service;

@Service
public class SummerDiscount implements DiscountStrategy {
    @Override
    public String getDiscountName() {
        return "summer20";
    }

    @Override
    public double applyDiscount(Double basePrice) {
        Double discountRate = basePrice * 0.2;
        return basePrice - discountRate;
    }
}
