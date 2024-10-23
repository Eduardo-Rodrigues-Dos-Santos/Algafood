package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.KitchenModel;
import org.algaworks.algafood.api.models.input.KitchenInput;
import org.algaworks.algafood.domain.models.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KitchenMapper {

    private ModelMapper modelMapper;

    public KitchenModel toKitchenModel(Kitchen kitchen) {
        return modelMapper.map(kitchen, KitchenModel.class);
    }

    public Kitchen toKitchen(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }
}
