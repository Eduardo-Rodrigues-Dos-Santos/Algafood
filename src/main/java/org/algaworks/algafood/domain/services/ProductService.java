package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.FileNotFoundException;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.exceptions.ProductPhotoNotFoundException;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.algaworks.algafood.domain.models.Restaurant;
import org.algaworks.algafood.domain.repositories.ProductPhotoRepository;
import org.algaworks.algafood.domain.repositories.ProductRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.ProductSpecsFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.algaworks.algafood.domain.services.PhotoStorageService.NewPhoto;

@Service
@AllArgsConstructor
public class ProductService {

    private static final String MSG_PRODUCT_BY_RESTAURANT_NOT_FOUND = "There is no product with id %d registered in restaurant with code %s";
    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;
    private final ProductPhotoRepository productPhotoRepository;
    private final PhotoStorageService photoStorageService;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product findProductByRestaurant(String restaurantCode, Long productId) {
        Product product = productRepository.findProductByRestaurant(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if (!product.getRestaurant().getCode().equals(restaurantCode)) {
            throw new ProductNotFoundException(String.format(MSG_PRODUCT_BY_RESTAURANT_NOT_FOUND, productId, restaurantCode));
        }
        return product;
    }

    public Page<Product> findProductsByRestaurant(String restaurantCode, Pageable pageable) {
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

    public List<ProductPhoto> findAllPhotos(String restaurantCode, Long productId) {
        return productPhotoRepository.findAllPhotosByProduct(productId, restaurantCode);
    }

    public ProductPhoto findPhotoByCode(String restaurantCode, Long productId, String photoCode) {
        return productPhotoRepository.findPhotoByCode(photoCode, productId, restaurantCode)
                .orElseThrow(() -> new ProductPhotoNotFoundException(photoCode));
    }

    public PhotoInputStreamAndContentType getPhotoByCode(String restaurantCode, Long productId, String photoCode) {
        try {
            ProductPhoto photo = findPhotoByCode(restaurantCode, productId, photoCode);
            return new PhotoInputStreamAndContentType(photo.getContentType(), photoStorageService.recover(photo.getCode()));
        } catch (ProductPhotoNotFoundException e) {
            throw new FileNotFoundException(null);
        }
    }

    @Transactional
    public ProductPhoto addNewPhoto(ProductPhoto productPhoto, InputStream inputStream, String restaurantCode, Long productId) {
        Product product = findProductByRestaurant(restaurantCode, productId);
        productPhoto.setProduct(product);
        productPhoto.setCode(UUID.randomUUID().toString());

        NewPhoto newPhoto = NewPhoto.builder()
                .code(productPhoto.getCode())
                .inputStream(inputStream).build();

        ProductPhoto photo = productPhotoRepository.save(productPhoto);
        productPhotoRepository.flush();
        photoStorageService.storage(newPhoto);
        return photo;
    }

    @Transactional
    public void activate(String restaurantCode, Long productId) {
        Product product = findProductByRestaurant(restaurantCode, productId);
        product.setActive(true);
    }

    @Transactional
    public void inactivate(String restaurantCode, Long productId) {
        Product product = findProductByRestaurant(restaurantCode, productId);
        product.setActive(false);
    }

    public record PhotoInputStreamAndContentType(String mediaType, InputStream inputStream) {
    }
}