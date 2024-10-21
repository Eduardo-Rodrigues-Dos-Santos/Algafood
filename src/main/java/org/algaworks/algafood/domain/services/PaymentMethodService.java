package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.PaymentNotFoundException;
import org.algaworks.algafood.domain.models.PaymentMethod;
import org.algaworks.algafood.domain.repositories.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodService {

    private static final String MSG_PAYMENT_IN_USE = "Payment method with id %d cannot be removed as it is in use.";
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    @Transactional
    public PaymentMethod add(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            paymentMethodRepository.deleteById(id);
            paymentMethodRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_PAYMENT_IN_USE, id));
        }
    }
}
