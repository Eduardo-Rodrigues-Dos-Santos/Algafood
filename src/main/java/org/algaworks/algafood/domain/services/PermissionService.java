package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.PermissionNotFoundException;
import org.algaworks.algafood.domain.models.Permission;
import org.algaworks.algafood.domain.repositories.PermissionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {

    private static final String MSG_PERMISSION_IN_USE = "Permission %d cannot be removed as it is in use.";
    private PermissionRepository permissionRepository;

    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException(id));
    }

    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Transactional
    public Permission add(Permission permission) {
        return permissionRepository.saveAndFlush(permission);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            permissionRepository.deleteById(id);
            permissionRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_PERMISSION_IN_USE, id));
        }
    }
}
