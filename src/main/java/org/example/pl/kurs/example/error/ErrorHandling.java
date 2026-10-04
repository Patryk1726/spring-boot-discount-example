package org.example.pl.kurs.example.error;

import org.example.pl.kurs.example.service.DiscountStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandling {
    @ExceptionHandler(OrderDoesNotExistException.class)
    public ResponseEntity<ErrorDto> orderDoesNotExistException(OrderDoesNotExistException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Order with id: " + exc.getOrderId() + " does not exist"));
    }

    @ExceptionHandler(DiscountDoesNotExistException.class)
    public ResponseEntity<ErrorDto> discountDoesNotExistException(DiscountDoesNotExistException exc) {
        Map<String, DiscountStrategy> discountStrategies = exc.getDiscountStrategies();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Discount with name: " + exc.getType() +
                        " does not exist. /nYou can choose: " + discountStrategies
                        .entrySet()
                        .stream()
                        .map(Map.Entry::getKey)
                        .collect(Collectors.joining(", "))));
    }

    public record ErrorDto(String message) {
    }

    ;
}
