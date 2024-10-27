package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.OrderMapper;
import org.algaworks.algafood.api.models.OrderModel;
import org.algaworks.algafood.api.models.OrderSimpleModel;
import org.algaworks.algafood.api.models.input.OrderInput;
import org.algaworks.algafood.domain.models.Order;
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

    @GetMapping
    public ResponseEntity<Page<OrderSimpleModel>> findAll(@PageableDefault Pageable pageable) {
        Page<OrderSimpleModel> orders = orderService.findAll(pageable).map(orderMapper::toOrderSimpleModel);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{code}")
    public ResponseEntity<OrderModel> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(orderMapper.toOrderModel(orderService.findByCode(code)));
    }

    @PostMapping
    public ResponseEntity<OrderModel> add(@RequestBody @Valid OrderInput orderInput) {
        Order order = orderService.add(orderMapper.toOrder(orderInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toOrderModel(order));
    }
}
