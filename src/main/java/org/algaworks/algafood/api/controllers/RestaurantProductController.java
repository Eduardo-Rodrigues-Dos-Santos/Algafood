package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.ProductMapper;
import org.algaworks.algafood.api.models.ProductModel;
import org.algaworks.algafood.api.models.input.ProductInput;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.RestaurantNotFoundException;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{restaurantCode}/products")
@AllArgsConstructor
public class RestaurantProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @CheckSecurity.Restaurant.Consult
    @GetMapping
    public ResponseEntity<Page<ProductModel>> findAllProducts(@PathVariable String restaurantCode,
                                                              @PageableDefault Pageable pageable,
                                                              @RequestParam(required = false) boolean includeInactive) {
        Page<Product> products;
        try {
            products = includeInactive ? productService.findAllByRestaurant(restaurantCode, pageable) :
                    productService.findAllActiveProductsByRestaurant(restaurantCode, pageable);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
        return ResponseEntity.ok(products.map(productMapper::toProductModel));
    }

    @CheckSecurity.Restaurant.Consult
    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> findById(@PathVariable String restaurantCode, @PathVariable Long productId) {
        try {
            Product product = productService.findByRestaurant(restaurantCode, productId);
            ProductModel productModel = productMapper.toProductModel(product);
            return ResponseEntity.ok(productModel);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurant.Manage
    @PostMapping
    public ResponseEntity<ProductModel> add(@PathVariable String restaurantCode, @RequestBody @Valid ProductInput productInput) {
        try {
            Product product = productMapper.toProduct(productInput);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productMapper.toProductModel(productService.add(restaurantCode, product)));
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurant.Manage
    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> update(@PathVariable String restaurantCode, @PathVariable Long productId,
                                               @RequestBody @Valid ProductInput productInput) {
        try {
            Product currentProduct = productService.findByRestaurant(restaurantCode, productId);
            productMapper.copyToDomainObject(productInput, currentProduct);
            return ResponseEntity.ok(productMapper.toProductModel(productService.add(restaurantCode, currentProduct)));
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurant.Manage
    @PutMapping("/{productId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateProduct(@PathVariable String restaurantCode, @PathVariable Long productId) {
        try {
            productService.activate(restaurantCode, productId);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurant.Manage
    @DeleteMapping("/{productId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateProduct(@PathVariable String restaurantCode, @PathVariable Long productId) {
        try {
            productService.inactivate(restaurantCode, productId);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
