package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.core.security.SecurityUtils;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.OrderNotFoundException;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.models.*;
import org.algaworks.algafood.domain.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private static final String MSG_PRODUCT_NOT_FOUND = "There is no product with id %d registered in the restaurant with id %d";
    private static final String PAYMENT_METHOD_NOT_ACCEPTED = "Credit card payment method is not accepted by this restaurant.";

    private final SecurityUtils securityUtils;
    private final OrderRepository orderRepository;
    private final PaymentMethodService paymentMethodService;
    private final CityService cityService;
    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Order add(Order order) {
        Order validatedOrder = validateOrder(order);
        calculateOrderValue(validatedOrder);
        return orderRepository.save(validatedOrder);
    }


    private Order validateOrder(Order order) {
        Restaurant restaurant = restaurantService.findById(order.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodService.findById(order.getPaymentMethod().getId());
        City city = cityService.findById(order.getDeliveryAddress().getCity().getId());

        order.getItems().forEach(orderItem -> {
            Long productId = orderItem.getProduct().getId();
            Product product = validateOrderItems(restaurant, productId);
            orderItem.setProduct(product);
            orderItem.setOrder(order);
        });
        validatePaymentMethod(restaurant.getId(), paymentMethod.getId());

        Long userId = securityUtils.getUserId();
        order.setClient(userService.findById(userId));
        order.setPaymentMethod(paymentMethod);
        order.setRestaurant(restaurant);
        order.getDeliveryAddress().setCity(city);
        return order;
    }

    private void validatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Set<PaymentMethod> allPaymentMethods = restaurantService.findAllPaymentMethods(restaurantId);
        PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);

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
            throw new BusinessException(String.format(MSG_PRODUCT_NOT_FOUND, restaurant.getId(), productId));
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
