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
    public void confirm(String orderCode) {
        Order order = orderService.findByCode(orderCode);
        order.confirm();
    }

    @Transactional
    public void cancel(String orderCode) {
        Order order = orderService.findByCode(orderCode);
        order.cancel();
    }

    @Transactional
    public void deliver(String orderCode) {
        Order order = orderService.findByCode(orderCode);
        order.deliver();
    }
}
