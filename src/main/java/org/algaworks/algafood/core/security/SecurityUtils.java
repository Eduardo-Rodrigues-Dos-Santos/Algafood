package org.algaworks.algafood.core.security;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.repositories.RestaurantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtils {

    private final RestaurantRepository restaurantRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Object principal = getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) principal;
        return jwt.getClaim("user_id");
    }

    public boolean existsResponsible(Long restaurantId) {
        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }
}
