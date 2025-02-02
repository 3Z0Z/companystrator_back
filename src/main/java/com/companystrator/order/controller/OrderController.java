package com.companystrator.order.controller;

import com.companystrator.config.JwtService;
import com.companystrator.order.dto.req.PlaceOrderDTO;
import com.companystrator.order.dto.res.OrderDTO;
import com.companystrator.order.dto.res.OrderDetailedDTO;
import com.companystrator.order.service.OrderService;
import com.companystrator.product.dto.res.SuccessResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    @PostMapping("/place-order")
    public ResponseEntity<SuccessResponseDTO> placeOrder(HttpServletRequest request, @RequestBody @Valid PlaceOrderDTO orderDTO) {
        String username = this.jwtService.extractUsernameFromRequest(request);
        this.orderService.placeOrder(username, orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO("Order created"));
    }

    @GetMapping("/get-order-by-id/{id}")
    public ResponseEntity<OrderDetailedDTO> getOrderById(@PathVariable("id") Long id) {
        OrderDetailedDTO response = this.orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-orders-by-nit/{nit}")
    public ResponseEntity<List<OrderDTO>> getOrdersByNit(@PathVariable("nit") String nit) {
        List<OrderDTO> response = this.orderService.getOrdersByNit(nit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-user-orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(HttpServletRequest request) {
        String username = this.jwtService.extractUsernameFromRequest(request);
        List<OrderDTO> userOrders = this.orderService.getUserOrders(username);
        return ResponseEntity.status(HttpStatus.OK).body(userOrders);
    }

}
