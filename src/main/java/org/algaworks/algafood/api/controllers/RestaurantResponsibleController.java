package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.UserMapper;
import org.algaworks.algafood.api.models.UserModel;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantCode}/responsible")
@AllArgsConstructor
public class RestaurantResponsibleController {

    private RestaurantService restaurantService;
    private UserMapper userMapper;

    @CheckSecurity.Restaurant.Consult
    @GetMapping
    public ResponseEntity<Set<UserModel>> findAllResponsible(@PathVariable String restaurantCode) {
        Set<UserModel> responsible = restaurantService.findAllResponsible(restaurantCode).stream()
                .map(userMapper::toUserModel).collect(Collectors.toSet());
        return ResponseEntity.ok(responsible);
    }

    @CheckSecurity.Restaurant.Edit
    @PutMapping("/{responsibleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addResponsible(@PathVariable String restaurantCode, @PathVariable Long responsibleId) {
        restaurantService.addResponsible(restaurantCode, responsibleId);
    }

    @CheckSecurity.Restaurant.Edit
    @DeleteMapping("/{responsibleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeResponsible(@PathVariable String restaurantCode, @PathVariable Long responsibleId) {
        restaurantService.removeResponsible(restaurantCode, responsibleId);
    }
}
