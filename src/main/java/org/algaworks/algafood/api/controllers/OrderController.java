package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.OrderMapper;
import org.algaworks.algafood.api.models.OrderModel;
import org.algaworks.algafood.api.models.OrderSimpleModel;
import org.algaworks.algafood.api.models.input.OrderInput;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.RestaurantNotFoundException;
import org.algaworks.algafood.domain.models.Order;
import org.algaworks.algafood.domain.repositories.filters.OrderFilter;
import org.algaworks.algafood.domain.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @CheckSecurity.Order.Consult
    @GetMapping("/{orderCode}")
    public ResponseEntity<OrderModel> findByCode(@PathVariable String orderCode) {
        OrderModel orderModel = orderMapper.toOrderModel(orderService.findByCode(orderCode));
        return ResponseEntity.ok(orderModel);
    }

    @CheckSecurity.Order.ConsultByClient
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<Page<OrderSimpleModel>> findAllByClient(@PathVariable Long clientId,
                                                                  @PageableDefault Pageable pageable) {
        Page<OrderSimpleModel> orders = orderService.findAllByClient(clientId, pageable)
                .map(orderMapper::toOrderSimpleModel);
        return ResponseEntity.ok(orders);
    }

    @CheckSecurity.Order.ConsultByRestaurant
    @GetMapping("/restaurants/{restaurantCode}")
    public ResponseEntity<Page<OrderSimpleModel>> findAllByRestaurant(OrderFilter orderFilter,
                                                                      @PathVariable String restaurantCode,
                                                                      @PageableDefault Pageable pageable) {
        try {
            Page<Order> orders = orderService.findAllByRestaurant(restaurantCode, orderFilter, pageable);
            return ResponseEntity.ok(orders.map(orderMapper::toOrderSimpleModel));
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Order.PlaceOrder
    @PostMapping
    public ResponseEntity<OrderModel> add(@RequestBody @Valid OrderInput orderInput) {
        Order order = orderService.add(orderMapper.toOrder(orderInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toOrderModel(order));
    }
}
