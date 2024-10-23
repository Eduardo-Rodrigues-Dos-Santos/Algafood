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
@RequestMapping("/restaurants/{restaurantId}/products")
@AllArgsConstructor
public class RestaurantProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @CheckSecurity.Restaurant.Consult
    @GetMapping
    public ResponseEntity<Set<ProductModel>> findAllProducts(@PathVariable Long restaurantId) {
        Set<Product> products = productService.findAll(restaurantId);
        return ResponseEntity.ok(products.stream().map(productMapper::toProductModel).collect(Collectors.toSet()));
    }

    @CheckSecurity.Restaurant.Consult
    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> findByRestaurant(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.toProductModel(productService.findByRestaurant(restaurantId, productId)));
    }

    @CheckSecurity.Restaurant.Edit
    @PostMapping
    public ResponseEntity<ProductModel> add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
        Product product = productMapper.toProduct(productInput);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper.toProductModel(productService.add(restaurantId, product)));
    }

    @CheckSecurity.Restaurant.Edit
    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> update(@PathVariable Long restaurantId, @PathVariable Long productId,
                                               @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = productService.findByRestaurant(restaurantId, productId);
        productMapper.copyToDomainObject(productInput, currentProduct);
        return ResponseEntity.ok(productMapper.toProductModel(productService.add(restaurantId, currentProduct)));
    }
}
