package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.ProductPhotoMapper;
import org.algaworks.algafood.api.models.ProductPhotoModel;
import org.algaworks.algafood.api.models.input.ProductPhotoInput;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.exceptions.RestaurantNotFoundException;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.algaworks.algafood.domain.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("restaurants/{restaurantCode}/products{productId}/photos")
@AllArgsConstructor
public class RestaurantProductPhotoController {

    private ProductService productService;
    private ProductPhotoMapper productPhotoMapper;

    @PutMapping(consumes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<ProductPhotoModel> addNewPhoto(@PathVariable String restaurantCode,
                                                         @PathVariable Long productId,
                                                         @Valid ProductPhotoInput productPhotoInput) {
        try {
            ProductPhoto productPhoto = productPhotoMapper.toProductPhoto(productPhotoInput);
            productPhoto.setContentType(productPhotoInput.getFile().getContentType());
            ProductPhotoModel productPhotoModel = productPhotoMapper.productPhotoModel(
                    productService.addNewPhoto(productPhoto, restaurantCode, productId));
            return ResponseEntity.ok(productPhotoModel);
        } catch (RestaurantNotFoundException | ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductPhotoModel>> findAllPhotos(@PathVariable String restaurantCode,
                                                                 @PathVariable Long productId) {
        try {
            List<ProductPhotoModel> list = productService.findAllPhotos(restaurantCode, productId)
                    .stream().map(productPhotoMapper::productPhotoModel).toList();
            return ResponseEntity.ok(list);
        } catch (RestaurantNotFoundException | ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
