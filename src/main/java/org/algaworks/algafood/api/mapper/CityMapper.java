package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.CityModel;
import org.algaworks.algafood.api.models.input.CityInput;
import org.algaworks.algafood.core.model_mapper.TypeMapName;
import org.algaworks.algafood.domain.models.City;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CityMapper {

    private ModelMapper modelMapper;

    public CityModel toCityModel(City city) {
        return modelMapper.map(city, CityModel.class);
    }

    public City toCity(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class, TypeMapName.TO_CITY.getValue());
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        modelMapper.map(cityInput, city, TypeMapName.CITY_INPUT_COPY_TO_CITY.getValue());
    }
}
