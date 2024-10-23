package org.algaworks.algafood.core.model_mapper;

import lombok.Getter;
import org.algaworks.algafood.api.models.OrderModel;
import org.algaworks.algafood.api.models.RestaurantModel;
import org.algaworks.algafood.api.models.RestaurantSimpleModel;
import org.algaworks.algafood.api.models.input.CityInput;
import org.algaworks.algafood.api.models.input.OrderInput;
import org.algaworks.algafood.api.models.input.OrderItemInput;
import org.algaworks.algafood.api.models.input.RestaurantInput;
import org.algaworks.algafood.domain.models.City;
import org.algaworks.algafood.domain.models.Order;
import org.algaworks.algafood.domain.models.OrderItem;
import org.algaworks.algafood.domain.models.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter

@Configuration
public class ModelMapperConfig {

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(RestaurantInput.class, Restaurant.class, TypeMapName.RESTAURANT_INPUT_COPY_TO_RESTAURANT.getValue())
                .addMapping(RestaurantInput::getShippingFee, Restaurant::setFee)
                .<Long>addMapping(RestaurantInput::getKitchenId, (target, value) -> target.getKitchen().setId(value))
                .<Long>addMapping(source -> source.getAddress().getCityId(), (target, value)
                        -> target.getAddress().getCity().setId(value))
                .addMappings(mapping -> mapping.skip(Restaurant::setId));

        modelMapper.typeMap(RestaurantInput.class, Restaurant.class, TypeMapName.TO_RESTAURANT.getValue())
                .addMapping(RestaurantInput::getAddress, Restaurant::setAddress)
                .addMapping(RestaurantInput::getShippingFee, Restaurant::setFee)
                .<Long>addMapping(RestaurantInput::getKitchenId, (restaurant, kitchenId) ->
                        restaurant.getKitchen().setId(kitchenId))
                .addMappings(mapping -> mapping.skip(Restaurant::setId));

        modelMapper.typeMap(CityInput.class, City.class, TypeMapName.CITY_INPUT_COPY_TO_CITY.getValue())
                .addMapping(CityInput::getStateId, City::setId)
                .addMappings(mapping -> mapping.skip(City::setId));

        modelMapper.typeMap(CityInput.class, City.class, TypeMapName.TO_CITY.getValue())
                .<Long>addMapping(CityInput::getStateId, (city, value) -> city.getState().setId(value))
                .addMappings(mapping -> mapping.skip(City::setId));

        modelMapper.typeMap(Restaurant.class, RestaurantModel.class, TypeMapName.TO_RESTAURANT_MODEL.getValue())
                .<String>addMapping(restaurant -> restaurant.getAddress().getCity().getState().getName(),
                        ((destination, value) -> destination.getAddress().setStateName(value)))
                .addMapping(Restaurant::getFee, RestaurantModel::setShippingFee);

        modelMapper.typeMap(Restaurant.class, RestaurantSimpleModel.class, TypeMapName.TO_RESTAURANT_SIMPLE_MODEL.getValue())
                .addMapping(src -> src.getKitchen().getName(), RestaurantSimpleModel::setKitchenName)
                .addMapping(Restaurant::getFee, RestaurantSimpleModel::setShippingFee);

        modelMapper.typeMap(Order.class, OrderModel.class)
                .<String>addMapping(src -> src.getDeliveryAddress().getCity().getState().getName(),
                        (destination, value) -> destination.getDeliveryAddress().setStateName(value));

        modelMapper.typeMap(OrderItemInput.class, OrderItem.class)
                .addMappings(mapping -> mapping.skip(OrderItem::setId));

        modelMapper.typeMap(OrderInput.class, Order.class)
                .addMappings(mapping -> mapping.skip(Order::setId));

        return modelMapper;
    }
}
