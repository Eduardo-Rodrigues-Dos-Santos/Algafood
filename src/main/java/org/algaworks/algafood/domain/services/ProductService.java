package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.models.Restaurant;
import org.algaworks.algafood.domain.repositories.ProductRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.ProductSpecsFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Product> findAllByRestaurant(String restaurantCode, Pageable pageable) {
        return productRepository.findAll(ProductSpecsFactory.restaurantCode(restaurantCode), pageable);
    }

    public Page<Product> findAllActiveProductsByRestaurant(String restaurantCode, Pageable pageable) {
        restaurantService.findByCode(restaurantCode);
        return productRepository.findAll(ProductSpecsFactory.activesByRestaurant(restaurantCode), pageable);
    }

    @Transactional
    public Product add(String restaurantCode, Product product) {
        Restaurant restaurant = restaurantService.findByCode(restaurantCode);
        product.setRestaurant(restaurant);
        return productRepository.saveAndFlush(product);
    }

    @Transactional
    public void activate(String restaurantCode, Long productId) {
        Product product = findByRestaurant(restaurantCode, productId);
        product.setActive(true);
    }

    @Transactional
    public void inactivate(String restaurantCode, Long productId) {
        Product product = findByRestaurant(restaurantCode, productId);
        product.setActive(false);
    }
}
