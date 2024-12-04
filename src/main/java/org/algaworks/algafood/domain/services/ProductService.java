package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.exceptions.ProductPhotoNotFoundException;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.algaworks.algafood.domain.models.Restaurant;
import org.algaworks.algafood.domain.repositories.ProductPhotoRepository;
import org.algaworks.algafood.domain.repositories.ProductRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.ProductSpecsFactory;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;
    private final ProductPhotoRepository productPhotoRepository;

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
    public List<ProductPhoto> findAllPhotos(String restaurantCode, Long productId) {
        Product product = findByRestaurant(restaurantCode, productId);
        Hibernate.initialize(product.getProductPhoto());
        return product.getProductPhoto();
    }

    public ProductPhoto findPhotoById(String restaurantCode, Long productId, Long photoId){
        findByRestaurant(restaurantCode, productId);
        return productPhotoRepository.findPhotoByProduct(photoId, productId)
                .orElseThrow(() -> new ProductPhotoNotFoundException(photoId));
    }

    @Transactional
    public ProductPhoto addNewPhoto(ProductPhoto productPhoto, String restaurantCode, Long productId) {
        Product product = findByRestaurant(restaurantCode, productId);
        productPhoto.setProduct(product);
        return productPhotoRepository.save(productPhoto);
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