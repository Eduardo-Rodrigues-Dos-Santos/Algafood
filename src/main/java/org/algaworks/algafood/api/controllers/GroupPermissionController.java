package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.PermissionMapper;
import org.algaworks.algafood.api.models.PermissionModel;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.GroupNotFoundException;
import org.algaworks.algafood.domain.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups/{groupId}/permissions")
@AllArgsConstructor
public class GroupPermissionController {

    private GroupService groupService;
    private PermissionMapper permissionMapper;

    @CheckSecurity.User.ConsultGroup
    @GetMapping
    public ResponseEntity<Set<PermissionModel>> findAllPermissions(@PathVariable Long groupId) {
        try {
            Set<PermissionModel> permissions = groupService.findAllPermissions(groupId).stream()
                    .map(permissionMapper::toPermissionModel).collect(Collectors.toSet());
            return ResponseEntity.ok(permissions);
        } catch (GroupNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.User.ManageGroup
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachPermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
        try {
            groupService.attachPermission(groupId, permissionId);
        } catch (GroupNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.User.ManageGroup
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detachPermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
        try {
            groupService.detachPermission(groupId, permissionId);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
