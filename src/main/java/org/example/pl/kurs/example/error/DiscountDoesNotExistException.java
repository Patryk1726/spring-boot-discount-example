package org.example.pl.kurs.example.error;

import lombok.Value;
import org.example.pl.kurs.example.service.DiscountStrategy;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
@Value
public class DiscountDoesNotExistException extends RuntimeException {
    Map<String, DiscountStrategy> discountStrategies;
    String type;
}
