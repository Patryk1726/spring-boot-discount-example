package org.example.pl.kurs.example.service;

import lombok.Setter;
import org.example.pl.kurs.example.dto.Order;
import org.example.pl.kurs.example.error.DiscountDoesNotExistException;
import org.example.pl.kurs.example.error.OrderDoesNotExistException;
import org.example.pl.kurs.example.request.CreateOrderRequest;
import org.example.pl.kurs.example.request.UpdateOrderRequest;
import org.example.pl.kurs.example.response.CreateOrderResponse;
import org.example.pl.kurs.example.response.DiscountOrderResponse;
import org.example.pl.kurs.example.response.ReadOrderResponse;
import org.example.pl.kurs.example.response.UpdateOrderResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    Map<String, DiscountStrategy> discountStrategies;
    List<Order> orders = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger(0);

    public OrderService(Set<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies.stream()
                .collect(Collectors.toMap(DiscountStrategy::getDiscountName, Function.identity()));
    }

    public CreateOrderResponse addOrder(CreateOrderRequest orderRequest) {
        Order order = new Order(counter.getAndIncrement(), orderRequest.customerName(), orderRequest.basePrice());
        orders.add(order);
        return new CreateOrderResponse(order.getId(), order.getCustomerName(), order.getBasePrice());
    }

    public Optional<Order> getOrderById(Integer id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public UpdateOrderResponse updateOrder(Integer id, UpdateOrderRequest orderRequest) {
        Order order = getOrderById(id).orElseThrow(() -> new OrderDoesNotExistException(id));
        order.setBasePrice(orderRequest.basePrice());
        order.setCustomerName(orderRequest.customerName());
        return new UpdateOrderResponse(order.getCustomerName(), order.getBasePrice());
    }
    public boolean deleteOrder(Integer id) {
        return orders.removeIf(order -> order.getId().equals(id));
    }
    public DiscountOrderResponse applyDiscount(Integer id, String type){
        DiscountStrategy ds = Optional.ofNullable(discountStrategies.get(type)).orElseThrow(() -> new DiscountDoesNotExistException(discountStrategies, type));
        Order order = getOrderById(id).orElseThrow(() -> new OrderDoesNotExistException(id));
        Double afterDiscountPrice = ds.applyDiscount(order.getBasePrice());
        return new DiscountOrderResponse(afterDiscountPrice);
    }
}
