package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.RestaurantCodeAndName;
import org.algaworks.algafood.api.models.RestaurantSimpleModel;
import org.algaworks.algafood.api.models.RestaurantModel;
import org.algaworks.algafood.api.models.input.RestaurantInput;
import org.algaworks.algafood.core.model_mapper.TypeMapName;
import org.algaworks.algafood.domain.models.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurantMapper {

    private ModelMapper modelMapper;

    public RestaurantSimpleModel toRestaurantSimpleModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantSimpleModel.class, TypeMapName.TO_RESTAURANT_SIMPLE_MODEL.getValue());
    }

    public Restaurant toRestaurant(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class, TypeMapName.TO_RESTAURANT.getValue());
    }

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        modelMapper.map(restaurantInput, restaurant, TypeMapName.RESTAURANT_INPUT_COPY_TO_RESTAURANT.getValue());
    }

    public RestaurantModel toRestaurantModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantModel.class, TypeMapName.TO_RESTAURANT_MODEL.getValue());
    }

    public RestaurantCodeAndName toRestaurantCodeAndName(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantCodeAndName.class);
    }
}
