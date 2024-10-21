package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.PaymentMethodMapper;
import org.algaworks.algafood.api.models.PaymentMethodModel;
import org.algaworks.algafood.domain.models.PaymentMethod;
import org.algaworks.algafood.domain.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
@AllArgsConstructor
public class RestaurantPaymentController {

    private final RestaurantService restaurantService;
    private final PaymentMethodMapper paymentMethodMapper;

    @GetMapping
    public ResponseEntity<Set<PaymentMethodModel>> findAllPaymentsMethods(@PathVariable Long restaurantId) {
        Set<PaymentMethod> allPaymentMethods = restaurantService.findAllPaymentMethods(restaurantId);
        return ResponseEntity.ok(allPaymentMethods.stream().map(paymentMethodMapper::toPaymentModel)
                .collect(Collectors.toSet()));
    }

    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachPaymentMethod(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantService.attachPaymentMethod(restaurantId, paymentMethodId);
    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detachPaymentMethod(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantService.detachPaymentMethod(restaurantId, paymentMethodId);
    }
}
