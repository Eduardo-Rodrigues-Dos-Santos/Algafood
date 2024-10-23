package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.PermissionModel;
import org.algaworks.algafood.api.models.input.PermissionInput;
import org.algaworks.algafood.domain.models.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PermissionMapper {

    private ModelMapper modelMapper;

    public Permission toPermission(PermissionInput permissionInput) {
        return modelMapper.map(permissionInput, Permission.class);
    }

    public PermissionModel toPermissionModel(Permission permission) {
        return modelMapper.map(permission, PermissionModel.class);
    }

    public void copyToDomainObject(PermissionInput permissionInput, Permission permission) {
        modelMapper.map(permissionInput, permission);
    }
}
