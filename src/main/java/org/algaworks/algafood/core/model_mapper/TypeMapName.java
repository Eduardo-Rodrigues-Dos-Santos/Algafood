package org.algaworks.algafood.core.model_mapper;

import lombok.Getter;

@Getter
public enum TypeMapName {

    RESTAURANT_INPUT_COPY_TO_RESTAURANT("copyToDomainRestaurant"),
    TO_RESTAURANT("toRestaurant"),
    CITY_INPUT_COPY_TO_CITY("copyToDomainCity"),
    TO_CITY("toCity"),
    TO_RESTAURANT_MODEL("toRestaurantModel"),
    TO_RESTAURANT_SIMPLE_MODEL("toRestaurantSimpleModel");

    private final String value;

    TypeMapName(String value) {
        this.value = value;
    }
}
