package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.OrderItemModel;
import org.algaworks.algafood.api.models.input.OrderItemInput;
import org.algaworks.algafood.domain.models.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderItemMapper {

    private final ModelMapper modelMapper;

    public OrderItemModel toOrderItemModel(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemModel.class);
    }

    public OrderItem toOrderItem(OrderItemInput orderItemInput) {
        return modelMapper.map(orderItemInput, OrderItem.class);
    }

}
