package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.services.OrderFlowService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{orderCode}")
@AllArgsConstructor
public class OrderFlowController {

    private final OrderFlowService orderFlowService;

    @CheckSecurity.Order.Manage
    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmOrder(@PathVariable String orderCode) {
        orderFlowService.confirm(orderCode);
    }

    @CheckSecurity.Order.Manage
    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliverOrder(@PathVariable String orderCode) {
        orderFlowService.deliver(orderCode);
    }

    @CheckSecurity.Order.Manage
    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable String orderCode) {
        orderFlowService.cancel(orderCode);
    }
}


