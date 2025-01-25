package com.companystrator.order.service;

import com.companystrator.order.dto.req.PlaceOrderDTO;
import com.companystrator.order.dto.res.OrderDTO;
import com.companystrator.order.dto.res.OrderDetailedDTO;

import java.util.List;

public interface OrderService {

    void placeOrder(String username, PlaceOrderDTO request);

    OrderDetailedDTO getOrderById(Long id);

    List<OrderDTO> getOrdersByNit(String nit);

}
