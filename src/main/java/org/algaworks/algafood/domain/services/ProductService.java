package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.models.Restaurant;
import org.algaworks.algafood.domain.repositories.ProductRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product findByRestaurant(String restaurantCode, Long productId) {
        restaurantService.findByCode(restaurantCode);
        return productRepository.findByRestaurant(restaurantCode, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    public Set<Product> findAll(String restaurantCode) {
        Restaurant restaurant = restaurantService.findByCode(restaurantCode);
        Hibernate.initialize(restaurant.getProducts());
        return restaurant.getProducts();
    }

    @Transactional
    public Product add(String restaurantCode, Product product) {
        Restaurant restaurant = restaurantService.findByCode(restaurantCode);
        product.setRestaurant(restaurant);
        return productRepository.saveAndFlush(product);
    }
}
