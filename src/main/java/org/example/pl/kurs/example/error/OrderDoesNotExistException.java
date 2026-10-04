package org.example.pl.kurs.example.error;

import lombok.Value;

@Value
public class OrderDoesNotExistException extends RuntimeException {
    Integer orderId;
}
