package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.RestaurantNotFoundException;
import org.algaworks.algafood.domain.models.*;
import org.algaworks.algafood.domain.repositories.RestaurantRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.RestaurantSpecsFactory;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RestaurantService {

    private static final String MSG_RESTAURANT_IN_USE = "Restaurant %s cannot be removed as it is in use.";
    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;


    public Restaurant findByCode(String code) {
        return restaurantRepository.findByCode(code).orElseThrow(() -> new RestaurantNotFoundException(code));
    }

    public Restaurant findByCodeForValidation(String code) {
        return restaurantRepository.findRestaurantByCodeForValidation(code)
                .orElseThrow(() -> new RestaurantNotFoundException(code));
    }

    public Page<Restaurant> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public Page<Restaurant> findByLikeName(String name, Pageable pageable) {
        return restaurantRepository.findAll(RestaurantSpecsFactory.byLikeName(name), pageable);
    }

    public Page<Restaurant> restaurantsWithFreeDelivery(Pageable pageable) {
        return restaurantRepository.findAll(RestaurantSpecsFactory.withFreeDelivery(), pageable);
    }

    @Transactional
    public Set<PaymentMethod> findAllPaymentMethods(String restaurantCode) {
        Restaurant restaurant = this.findByCode(restaurantCode);
        Hibernate.initialize(restaurant.getPaymentMethods());
        return restaurant.getPaymentMethods();
    }


    @Transactional
    public Restaurant add(Restaurant restaurant) {
        Kitchen kitchen = kitchenService.findById(restaurant.getKitchen().getId());
        City city = cityService.findById(restaurant.getAddress().getCity().getId());
        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);
        return restaurantRepository.saveAndFlush(restaurant);
    }

    @Transactional
    public void deleteByCode(String code) {
        try {
            Restaurant restaurant = this.findByCode(code);
            restaurantRepository.deleteById(restaurant.getId());
            restaurantRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_RESTAURANT_IN_USE, code));
        }
    }

    @Transactional
    public void attachPaymentMethod(String restaurantCode, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
        Restaurant restaurant = this.findByCode(restaurantCode);
        restaurant.addPaymentMethod(paymentMethod);
    }

    @Transactional
    public void detachPaymentMethod(String restaurantCode, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
        Restaurant restaurant = this.findByCode(restaurantCode);
        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public Set<User> findAllResponsible(String restaurantCode) {
        Restaurant restaurant = this.findByCode(restaurantCode);
        Hibernate.initialize(restaurant.getResponsible());
        return restaurant.getResponsible();
    }

    @Transactional
    public void addResponsible(String restaurantCode, Long restaurants) {
        Restaurant restaurant = this.findByCode(restaurantCode);
        User responsible = userService.findById(restaurants);
        restaurant.addResponsible(responsible);
    }

    @Transactional
    public void removeResponsible(String restaurantCode, Long restaurants) {
        Restaurant restaurant = this.findByCode(restaurantCode);
        User responsible = userService.findById(restaurants);
        restaurant.removeResponsible(responsible);
    }

    @Transactional
    public void activate(String code) {
        Restaurant restaurant = findByCode(code);
        restaurant.activate();
    }

    @Transactional
    public void inactivate(String code) {
        Restaurant restaurant = findByCode(code);
        restaurant.inactivate();
    }

    @Transactional
    public void activateMultiples(List<String> restaurantCodes) {
        restaurantCodes.forEach(this::activate);
    }

    @Transactional
    public void inactivateMultiples(List<String> restaurantCodes) {
        restaurantCodes.forEach(this::inactivate);
    }

    @Transactional
    public void opening(String code) {
        Restaurant restaurant = findByCode(code);
        restaurant.opening();
    }

    @Transactional
    public void closing(String code) {
        Restaurant restaurant = findByCode(code);
        restaurant.closing();
    }

    @Transactional()
    public Restaurant updateAddress(String code, Address address) {
        City city = cityService.findById(address.getCity().getId());
        Restaurant restaurant = findByCode(code);
        restaurant.setAddress(address);
        restaurant.getAddress().setCity(city);
        return restaurantRepository.saveAndFlush(restaurant);
    }
}
