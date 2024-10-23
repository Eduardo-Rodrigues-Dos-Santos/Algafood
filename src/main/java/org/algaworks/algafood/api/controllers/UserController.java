package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.UserMapper;
import org.algaworks.algafood.api.models.UserModel;
import org.algaworks.algafood.api.models.input.UserInput;
import org.algaworks.algafood.api.models.input.UserUpdateInput;
import org.algaworks.algafood.api.models.input.UserUpdatePasswordInput;
import org.algaworks.algafood.domain.models.User;
import org.algaworks.algafood.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toUserModel(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserModel> add(@Valid @RequestBody UserInput userInput) {
        User user = userService.add(userMapper.toUser(userInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserModel(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @Valid @RequestBody UserUpdateInput userUpdateInput) {
        User currentUser = userService.findById(id);
        userMapper.copyToDomainObject(userUpdateInput, currentUser);
        User userUpdated = userService.update(currentUser);
        return ResponseEntity.ok(userMapper.toUserModel(userUpdated));
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId, @RequestBody @Valid UserUpdatePasswordInput updatePasswordInput) {
        userService.updatePassword(userId, updatePasswordInput.getCurrentPassword(), updatePasswordInput.getNewPassword());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
