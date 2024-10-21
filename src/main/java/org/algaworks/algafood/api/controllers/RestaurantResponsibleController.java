package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.UserMapper;
import org.algaworks.algafood.api.models.UserModel;
import org.algaworks.algafood.domain.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantId}/responsible")
@AllArgsConstructor
public class RestaurantResponsibleController {

    private RestaurantService restaurantService;
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Set<UserModel>> findAllResponsible(@PathVariable Long restaurantId) {
        Set<UserModel> responsible = restaurantService.findAllResponsible(restaurantId).stream()
                .map(userMapper::toUserModel).collect(Collectors.toSet());
        return ResponseEntity.ok(responsible);
    }

    @PutMapping("/{responsibleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addResponsible(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
        restaurantService.addResponsible(restaurantId, responsibleId);
    }

    @DeleteMapping("/{responsibleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeResponsible(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
        restaurantService.removeResponsible(restaurantId, responsibleId);
    }
}
