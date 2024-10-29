package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.GroupMapper;
import org.algaworks.algafood.api.models.GroupModel;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/groups")
@AllArgsConstructor
public class UserGroupController {

    private UserService userService;
    private GroupMapper groupMapper;


    @CheckSecurity.User.ConsultGroup
    @GetMapping
    public ResponseEntity<Set<GroupModel>> findAllGroupsByUser(@PathVariable Long userId) {
        Set<GroupModel> groups = userService.findAllGroups(userId).stream().map(groupMapper::toGroupModel)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(groups);
    }

    @CheckSecurity.User.ManageGroup
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.attachGroup(userId, groupId);
    }

    @CheckSecurity.User.ManageGroup
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detachGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.detachGroup(userId, groupId);
    }
}
