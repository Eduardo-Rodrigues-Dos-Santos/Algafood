package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.ProductPhotoMapper;
import org.algaworks.algafood.api.models.ProductPhotoModel;
import org.algaworks.algafood.api.models.input.ProductPhotoInput;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.ProductNotFoundException;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.algaworks.algafood.domain.services.ProductService;
import org.algaworks.algafood.domain.services.ProductService.PhotoInputStreamAndContentType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("restaurants/{restaurantCode}/products/{productId}/photos")
@AllArgsConstructor
public class RestaurantProductPhotoController {

    private ProductService productService;
    private ProductPhotoMapper productPhotoMapper;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductPhotoModel> addNewPhoto(@PathVariable String restaurantCode,
                                                         @PathVariable Long productId,
                                                         @Valid ProductPhotoInput productPhotoInput) throws IOException {
        try {
            InputStream inputStream = productPhotoInput.getPhoto().getInputStream();
            ProductPhoto productPhoto = productPhotoMapper.toProductPhoto(productPhotoInput);
            ProductPhoto newProductPhoto = productService.addNewPhoto(productPhoto, inputStream, restaurantCode, productId);
            ProductPhotoModel productPhotoModel = productPhotoMapper.productPhotoModel(newProductPhoto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productPhotoModel);
        } catch (ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping(path = "/{photoCode}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<InputStreamResource> getPhotoByCode(@PathVariable String restaurantCode,
                                                              @PathVariable Long productId,
                                                              @PathVariable String photoCode) {
        PhotoInputStreamAndContentType photoByCode = productService.getPhotoByCode(restaurantCode, productId, photoCode);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(photoByCode.mediaType()))
                .body(new InputStreamResource(photoByCode.inputStream()));
    }

    @GetMapping(path = "/{photoCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPhotoModel> findPhotoByCode(@PathVariable String restaurantCode,
                                                             @PathVariable Long productId,
                                                             @PathVariable String photoCode) {
        ProductPhoto photo = productService.findPhotoByCode(restaurantCode, productId, photoCode);
        return ResponseEntity.ok(productPhotoMapper.productPhotoModel(photo));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductPhotoModel>> findAllPhotos(@PathVariable String restaurantCode,
                                                                 @PathVariable Long productId) {
        try {
            List<ProductPhotoModel> list = productService.findAllPhotos(restaurantCode, productId)
                    .stream().map(productPhotoMapper::productPhotoModel).toList();
            return ResponseEntity.ok(list);
        } catch (ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
