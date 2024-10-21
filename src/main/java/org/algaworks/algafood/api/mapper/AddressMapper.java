package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.AddressModel;
import org.algaworks.algafood.api.models.input.AddressInput;
import org.algaworks.algafood.domain.models.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapper {

    private ModelMapper modelMapper;

    public AddressModel toAddressModel(Address address) {
        modelMapper.typeMap(Address.class, AddressModel.class)
                .addMapping(src -> src.getCity().getName(), AddressModel::setCityName)
                .addMapping(src -> src.getCity().getState().getName(), AddressModel::setStateName);
        return modelMapper.map(address, AddressModel.class);
    }

    public Address toAddress(AddressInput addressInput) {
        modelMapper.typeMap(AddressInput.class, Address.class)
                .<Long>addMapping(AddressInput::getCityId, (address, id) -> address.getCity().setId(id));
        return modelMapper.map(addressInput, Address.class);
    }
}
