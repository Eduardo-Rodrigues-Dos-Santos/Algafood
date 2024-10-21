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

    private static final String MSG_RESTAURANT_IN_USE = "Restaurant %d cannot be removed as it is in use.";
    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
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
    public Set<PaymentMethod> findAllPaymentMethods(Long restaurantId) {
        Restaurant restaurant = this.findById(restaurantId);
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
    public void deleteById(Long id) {
        try {
            this.findById(id);
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_RESTAURANT_IN_USE, id));
        }
    }

    @Transactional
    public void attachPaymentMethod(Long restaurantId, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
        Restaurant restaurant = this.findById(restaurantId);
        restaurant.addPaymentMethod(paymentMethod);
    }

    @Transactional
    public void detachPaymentMethod(Long restaurantId, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
        Restaurant restaurant = this.findById(restaurantId);
        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public Set<User> findAllResponsible(Long restaurantId) {
        Restaurant restaurant = this.findById(restaurantId);
        Hibernate.initialize(restaurant.getResponsible());
        return restaurant.getResponsible();
    }

    @Transactional
    public void addResponsible(Long restaurantId, Long restaurants) {
        Restaurant restaurant = this.findById(restaurantId);
        User responsible = userService.findById(restaurants);
        restaurant.addResponsible(responsible);
    }

    @Transactional
    public void removeResponsible(Long restaurantId, Long restaurants) {
        Restaurant restaurant = this.findById(restaurantId);
        User responsible = userService.findById(restaurants);
        restaurant.removeResponsible(responsible);
    }

    @Transactional
    public void activate(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.activate();
    }

    @Transactional
    public void inactivate(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.inactivate();
    }

    @Transactional
    public void activateMultiples(List<Long> restaurantsIds) {
        restaurantsIds.forEach(this::activate);
    }

    @Transactional
    public void inactivateMultiples(List<Long> restaurantsIds) {
        restaurantsIds.forEach(this::inactivate);
    }

    @Transactional
    public void opening(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.opening();
    }

    @Transactional
    public void closing(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.closing();
    }

    @Transactional()
    public Restaurant updateAddress(Long id, Address address) {
        City city = cityService.findById(address.getCity().getId());
        Restaurant restaurant = findById(id);
        restaurant.setAddress(address);
        restaurant.getAddress().setCity(city);
        return restaurantRepository.saveAndFlush(restaurant);
    }
}
