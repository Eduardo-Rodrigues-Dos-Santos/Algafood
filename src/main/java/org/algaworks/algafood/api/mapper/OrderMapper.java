package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.OrderModel;
import org.algaworks.algafood.api.models.OrderSimpleModel;
import org.algaworks.algafood.api.models.input.OrderInput;
import org.algaworks.algafood.domain.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderModel toOrderModel(Order order) {
        return modelMapper.map(order, OrderModel.class);
    }

    public OrderSimpleModel toOrderSimpleModel(Order order) {
        return modelMapper.map(order, OrderSimpleModel.class);
    }

    public Order toOrder(OrderInput orderInput) {
        return modelMapper.map(orderInput, Order.class);
    }
}
