package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.input.PaymentMethodInput;
import org.algaworks.algafood.api.models.PaymentMethodModel;
import org.algaworks.algafood.domain.models.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentMethodMapper {

    private ModelMapper modelMapper;

    public PaymentMethod toPaymentMethod(PaymentMethodInput paymentInput) {
        return modelMapper.map(paymentInput, PaymentMethod.class);
    }

    public PaymentMethodModel toPaymentModel(PaymentMethod paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethodModel.class);
    }
}
