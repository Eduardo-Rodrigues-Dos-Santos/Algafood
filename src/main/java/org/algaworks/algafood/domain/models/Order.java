package org.algaworks.algafood.domain.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Embedded
    private Address deliveryAddress;

    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @CreationTimestamp
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @Column(name = "confirmation_date")
    private OffsetDateTime confirmationDate;

    @Column(name = "cancellation_date")
    private OffsetDateTime cancellationDate;

    @Column(name = "delivery_date")
    private OffsetDateTime deliveryDate;

    public Order() {
        this.orderStatus = OrderStatus.CREATED;
    }

    private void setOrderStatus(OrderStatus newOrderStatus) {
        if (orderStatus.cannotChange(newOrderStatus)) {
            throw new BusinessException(String.format("Order status %d cannot be changed from %s to %s",
                    getId(), getOrderStatus().getStatus(), newOrderStatus.getStatus()));
        }
        this.orderStatus = newOrderStatus;
    }

    public void confirm() {
        setOrderStatus(OrderStatus.CONFIRMED);
        this.confirmationDate = OffsetDateTime.now();
    }

    public void deliver() {
        setOrderStatus(OrderStatus.DELIVERED);
        this.deliveryDate = OffsetDateTime.now();
    }

    public void cancel() {
        setOrderStatus(OrderStatus.CANCELED);
        this.cancellationDate = OffsetDateTime.now();
    }
}
