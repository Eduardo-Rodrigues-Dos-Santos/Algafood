package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.PaymentMethodMapper;
import org.algaworks.algafood.api.models.PaymentMethodModel;
import org.algaworks.algafood.api.models.input.PaymentMethodInput;
import org.algaworks.algafood.domain.models.PaymentMethod;
import org.algaworks.algafood.domain.services.PaymentMethodService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-methods")
@AllArgsConstructor
public class PaymentMethodController {

    private PaymentMethodService paymentMethodService;
    private PaymentMethodMapper paymentMethodMapper;

    @GetMapping
    public ResponseEntity<Set<PaymentMethodModel>> findAll() {
        Set<PaymentMethodModel> paymentMethods = paymentMethodService.findAll().stream()
                .map(paymentMethodMapper::toPaymentModel).collect(Collectors.toSet());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES)).body(paymentMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(paymentMethodMapper.toPaymentModel(paymentMethodService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PaymentMethodModel> add(@RequestBody @Valid PaymentMethodInput paymentInput) {
        PaymentMethod newPaymentMethod = paymentMethodService.add(paymentMethodMapper.toPaymentMethod(paymentInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodMapper.toPaymentModel(newPaymentMethod));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        paymentMethodService.deleteById(id);
    }
}
