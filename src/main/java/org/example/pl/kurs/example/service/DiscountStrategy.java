package org.example.pl.kurs.example.service;

public interface DiscountStrategy {
    String getDiscountName();
    double applyDiscount(Double basePrice);
}
