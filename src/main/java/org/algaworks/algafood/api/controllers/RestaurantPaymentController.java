package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.PaymentMethodMapper;
import org.algaworks.algafood.api.models.PaymentMethodModel;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.models.PaymentMethod;
import org.algaworks.algafood.domain.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantCode}/payment-methods")
@AllArgsConstructor
public class RestaurantPaymentController {

    private final RestaurantService restaurantService;
    private final PaymentMethodMapper paymentMethodMapper;

    @CheckSecurity.Restaurant.Consult
    @GetMapping
    public ResponseEntity<Set<PaymentMethodModel>> findAllPaymentsMethods(@PathVariable String restaurantCode) {
        Set<PaymentMethod> allPaymentMethods = restaurantService.findAllPaymentMethods(restaurantCode);
        return ResponseEntity.ok(allPaymentMethods.stream().map(paymentMethodMapper::toPaymentModel)
                .collect(Collectors.toSet()));
    }

    @CheckSecurity.Restaurant.Manage
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachPaymentMethod(@PathVariable String restaurantCode, @PathVariable Long paymentMethodId) {
        restaurantService.attachPaymentMethod(restaurantCode, paymentMethodId);
    }

    @CheckSecurity.Restaurant.Manage
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detachPaymentMethod(@PathVariable String restaurantCode, @PathVariable Long paymentMethodId) {
        restaurantService.detachPaymentMethod(restaurantCode, paymentMethodId);
    }
}