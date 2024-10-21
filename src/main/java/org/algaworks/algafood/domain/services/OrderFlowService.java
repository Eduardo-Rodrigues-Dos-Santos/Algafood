package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.models.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderFlowService {

    private final OrderService orderService;

    @Transactional
    public void confirm(Long orderId) {
        Order order = orderService.findById(orderId);
        order.confirm();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderService.findById(orderId);
        order.cancel();
    }

    @Transactional
    public void deliver(Long orderId) {
        Order order = orderService.findById(orderId);
        order.deliver();
    }
}
