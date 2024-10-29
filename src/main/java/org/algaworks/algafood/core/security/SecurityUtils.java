package org.algaworks.algafood.core.security;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.models.Order;
import org.algaworks.algafood.domain.repositories.OrderRepository;
import org.algaworks.algafood.domain.repositories.RestaurantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtils {

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Object principal = getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) principal;
        return jwt.getClaim("user_id");
    }

    public boolean isResponsibleForRestaurant(String restaurantCode) {
        return restaurantRepository.isResponsible(restaurantCode, getUserId());
    }

    public boolean isResponsibleForOrder(String orderCode) {
        Order order = orderRepository.findByCode(orderCode)
                .orElseThrow(() -> new BusinessException(String.format("There is no registered order with code %s", orderCode)));
        String restaurantCode = order.getRestaurant().getCode();
        return isResponsibleForRestaurant(restaurantCode);
    }

    public boolean isOwnerTheOrder(Long clientId) {
        return getUserId().equals(clientId);
    }

    public boolean isAccountOwner(Long userId) {
        return getUserId().equals(userId);
    }
}
