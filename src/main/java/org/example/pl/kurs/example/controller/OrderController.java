package org.example.pl.kurs.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.pl.kurs.example.dto.Order;
import org.example.pl.kurs.example.error.OrderDoesNotExistException;
import org.example.pl.kurs.example.request.CreateOrderRequest;
import org.example.pl.kurs.example.request.UpdateOrderRequest;
import org.example.pl.kurs.example.response.CreateOrderResponse;
import org.example.pl.kurs.example.response.DiscountOrderResponse;
import org.example.pl.kurs.example.response.ReadOrderResponse;
import org.example.pl.kurs.example.response.UpdateOrderResponse;
import org.example.pl.kurs.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<CreateOrderResponse> addOrder(@RequestBody CreateOrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(orderRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadOrderResponse> readOrder(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id).orElseThrow(() -> new OrderDoesNotExistException(id));
        ReadOrderResponse readOrderResponse = new ReadOrderResponse(order.getId(), order.getCustomerName(), order.getBasePrice());
        return ResponseEntity.ok(readOrderResponse);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateOrderResponse> updateOrder(@PathVariable Integer id, @RequestBody UpdateOrderRequest orderRequest) {
        return ResponseEntity.ok().body(orderService.updateOrder(id,orderRequest));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if(orderService.deleteOrder(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/discount")
    public ResponseEntity<DiscountOrderResponse> applyDiscount(@PathVariable Integer id, @RequestParam String discountType) {
        return ResponseEntity.ok().body(orderService.applyDiscount(id, discountType));
    }

}
