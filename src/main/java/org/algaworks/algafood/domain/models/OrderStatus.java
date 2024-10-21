package org.algaworks.algafood.domain.models;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
public enum OrderStatus {

    CREATED("created"),
    CONFIRMED("confirmed", CREATED),
    DELIVERED("delivered", CONFIRMED),
    CANCELED("canceled", CREATED);

    private final String status;
    private final List<OrderStatus> previousStatuses;

    OrderStatus(String status, OrderStatus... previousStatuses) {
        this.status = status;
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    public boolean cannotChange(OrderStatus newOrderStatus) {
        return !(newOrderStatus.getPreviousStatuses().contains(this));
    }
}
