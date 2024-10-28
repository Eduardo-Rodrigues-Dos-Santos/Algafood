package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.core.security.SecurityUtils;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.OrderNotFoundException;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.models.*;
import org.algaworks.algafood.domain.repositories.OrderRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.OrderSpecsFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private static final String MSG_PRODUCT_NOT_FOUND = "There is no product with id %d registered in the restaurant with code %s";
    private static final String PAYMENT_METHOD_NOT_ACCEPTED = "Credit card payment method is not accepted by this restaurant.";

    private final SecurityUtils securityUtils;
    private final OrderRepository orderRepository;
    private final PaymentMethodService paymentMethodService;
    private final CityService cityService;
    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final UserService userService;


    public Page<Order> findAllByRestaurant(String restaurantCode, Pageable pageable) {
        Restaurant restaurant = restaurantService.findByCode(restaurantCode);
        return orderRepository.findAll(OrderSpecsFactory.ordersByRestaurant(restaurant.getId()), pageable);
    }

    public Page<Order> findAllByClient(Long clientId, Pageable pageable) {
        return orderRepository.findAll(OrderSpecsFactory.ordersByClient(clientId), pageable);
    }

    public Order findByCode(String code) {
        return orderRepository.findByCode(code).orElseThrow(() -> new OrderNotFoundException(code));
    }

    @Transactional
    public Order add(Order order) {
        Order validatedOrder = validateOrder(order);
        calculateOrderValue(validatedOrder);
        return orderRepository.save(validatedOrder);
    }

    private Order validateOrder(Order order) {
        Restaurant restaurant = restaurantService.findByCodeForValidation(order.getRestaurant().getCode());
        PaymentMethod paymentMethod = paymentMethodService.findById(order.getPaymentMethod().getId());
        City city = cityService.findById(order.getDeliveryAddress().getCity().getId());

        order.getItems().forEach(orderItem -> {
            Long productId = orderItem.getProduct().getId();
            Product product = validateOrderItems(restaurant, productId);
            orderItem.setProduct(product);
            orderItem.setOrder(order);
        });
        validatePaymentMethod(restaurant, paymentMethod);

        Long userId = securityUtils.getUserId();
        order.setClient(userService.findById(userId));
        order.setPaymentMethod(paymentMethod);
        order.setRestaurant(restaurant);
        order.getDeliveryAddress().setCity(city);
        return order;
    }

    private void validatePaymentMethod(Restaurant restaurant, PaymentMethod paymentMethod) {

        Set<PaymentMethod> allPaymentMethods = restaurant.getPaymentMethods();
        if (!allPaymentMethods.contains(paymentMethod)) {
            throw new BusinessException(PAYMENT_METHOD_NOT_ACCEPTED);
        }
    }

    private Product validateOrderItems(Restaurant restaurant, Long productId) {
        try {
            Product product = productService.findById(productId);
            if (product.getRestaurant().equals(restaurant)) {
                return product;
            }
            throw new BusinessException(String.format(MSG_PRODUCT_NOT_FOUND, productId, restaurant.getCode()));
        } catch (ProductNotFoundException e) {
            throw new BusinessException(String.format(MSG_PRODUCT_NOT_FOUND, restaurant.getId(), productId));
        }
    }

    private void calculateOrderValue(Order order) {
        order.getItems().forEach(orderItem -> {
            Product product = orderItem.getProduct();
            BigDecimal totalValue = product.getValue().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderItem.setUnitValue(product.getValue());
            orderItem.setTotalValue(totalValue);
            order.setSubtotal(order.getSubtotal().add(totalValue));
        });
        order.setDeliveryFee(order.getRestaurant().getFee());
        order.setTotalValue(order.getSubtotal().add(order.getDeliveryFee()));
    }
}
