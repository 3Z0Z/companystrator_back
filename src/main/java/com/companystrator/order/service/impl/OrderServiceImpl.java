package com.companystrator.order.service.impl;

import com.companystrator.db.model.*;
import com.companystrator.db.repository.*;
import com.companystrator.exceptions.exception.*;
import com.companystrator.order.dto.req.PlaceOrderDTO;
import com.companystrator.order.dto.res.OrderDTO;
import com.companystrator.order.dto.res.OrderDetailedDTO;
import com.companystrator.order.dto.res.OrderItemDTO;
import com.companystrator.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void placeOrder(String username, PlaceOrderDTO request) {
        User user = this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        Client client = this.clientRepository.findByUserIdAndCompanyNit(user.getId(), request.nit())
            .orElseGet(() -> this.createClient(user, request.nit()));
        List<OrderItem> orderItems = this.createOrderItems(request);
        double totalAmount = orderItems.stream().mapToDouble(OrderItem::getAmount).sum();
        Order newOrder = Order.builder()
            .client(client)
            .totalAmount(totalAmount)
            .currency(request.currency())
            .build();
        final Order order = this.orderRepository.save(newOrder);
        orderItems.forEach(item -> item.setOrder(order));
        this.orderItemRepository.saveAll(orderItems);
    }

    @Override
    public OrderDetailedDTO getOrderById(Long id) {
        Order order = this.orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        Client client = this.clientRepository.findById(order.getClient().getId())
            .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        List<OrderItemDTO> orderItems = this.orderItemRepository.findByOrderId(order.getId())
            .stream().map(orderItem -> OrderItemDTO.builder()
                .productCode(orderItem.getProduct().getCode())
                .name(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .amount(orderItem.getAmount())
                .build()).toList();
        return OrderDetailedDTO.builder()
            .orderId(order.getId())
            .clientId(client.getId())
            .totalAmount(order.getTotalAmount())
            .currency(order.getCurrency())
            .placetAt(order.getPlacedAt())
            .orderItems(orderItems)
            .build();
    }

    @Override
    public List<OrderDTO> getOrdersByNit(String nit) {
        List<Client> clientList = this.clientRepository.findByCompanyNit(nit);
        List<Long> clientIds = clientList.stream().map(Client::getId).toList();
        List<Order> orderList = this.orderRepository.findByClientIds(clientIds);
        return orderList.stream().map(order -> OrderDTO.builder()
            .orderId(order.getId())
            .clientId(order.getClient().getId())
            .totalAmount(order.getTotalAmount())
            .currency(order.getCurrency())
            .placetAt(order.getPlacedAt())
            .build()).toList();
    }

    @Override
    public List<OrderDTO> getUserOrders(String username) {
        User user = this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        List<Client> clientList = this.clientRepository.findByUserId(user.getId());
        List<Long> clientIds = clientList.stream().map(Client::getId).toList();
        List<Order> orderList = this.orderRepository.findByClientIds(clientIds);
        return orderList.stream().map(order -> OrderDTO.builder()
            .orderId(order.getId())
            .clientId(order.getClient().getId())
            .totalAmount(order.getTotalAmount())
            .currency(order.getCurrency())
            .placetAt(order.getPlacedAt())
            .build()).toList();
    }

    private Client createClient(User user, String nit) {
        Company company = this.companyRepository.findById(nit)
            .orElseThrow(() -> new CompanyNotFoundException("Company not found with NIT " + nit));
        Client newClient = Client.builder().user(user).company(company).build();
        return this.clientRepository.save(newClient);
    }

    private List<OrderItem> createOrderItems(PlaceOrderDTO request) {
        return request.orderItems()
            .stream().map(dto -> {
                Product product = this.productRepository.findById(dto.productCode())
                    .orElseThrow(() -> new ProductNotFoundException("Product code " + dto.productCode() + " not found"));
                double amount = switch (request.currency()) {
                    case USD -> dto.quantity() * product.getPriceUsd();
                    case COP -> dto.quantity() * product.getPriceCop();
                    case MXN -> dto.quantity() * product.getPriceMxn();
                };
                return OrderItem.builder()
                    .product(product)
                    .quantity(dto.quantity())
                    .amount(amount)
                    .build();
            }).toList();
    }

}
