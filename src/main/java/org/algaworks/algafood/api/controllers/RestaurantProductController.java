package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.ProductMapper;
import org.algaworks.algafood.api.models.ProductModel;
import org.algaworks.algafood.api.models.input.ProductInput;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantCode}/products")
@AllArgsConstructor
public class RestaurantProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @CheckSecurity.Restaurant.Consult
    @GetMapping
    public ResponseEntity<Set<ProductModel>> findAllProducts(@PathVariable String restaurantCode) {
        Set<Product> products = productService.findAll(restaurantCode);
        return ResponseEntity.ok(products.stream().map(productMapper::toProductModel).collect(Collectors.toSet()));
    }

    @CheckSecurity.Restaurant.Consult
    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> findByRestaurant(@PathVariable String restaurantCode, @PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.toProductModel(productService.findByRestaurant(restaurantCode, productId)));
    }

    @CheckSecurity.Restaurant.Manage
    @PostMapping
    public ResponseEntity<ProductModel> add(@PathVariable String restaurantCode, @RequestBody @Valid ProductInput productInput) {
        Product product = productMapper.toProduct(productInput);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper.toProductModel(productService.add(restaurantCode, product)));
    }

    @CheckSecurity.Restaurant.Manage
    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> update(@PathVariable String restaurantCode, @PathVariable Long productId,
                                               @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = productService.findByRestaurant(restaurantCode, productId);
        productMapper.copyToDomainObject(productInput, currentProduct);
        return ResponseEntity.ok(productMapper.toProductModel(productService.add(restaurantCode, currentProduct)));
    }
}
