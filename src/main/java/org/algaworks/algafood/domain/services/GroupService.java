package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.GroupNotFoundException;
import org.algaworks.algafood.domain.models.Group;
import org.algaworks.algafood.domain.models.Permission;
import org.algaworks.algafood.domain.repositories.GroupRepository;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupService {

    private GroupRepository groupRepository;
    private PermissionService permissionService;

    public Group findById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    public Set<Permission> findAllPermissions(Long groupId) {
        Group group = this.findById(groupId);
        Hibernate.initialize(group.getPermissions());
        return group.getPermissions();
    }

    @Transactional
    public Group add(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public void deleteById(Long groupId) {
        this.findById(groupId);
        try {
            groupRepository.deleteById(groupId);
            groupRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(e.getMessage());
        }
    }

    @Transactional
    public void attachPermission(Long groupId, Long permissionId) {
        Group group = this.findById(groupId);
        Permission permission = permissionService.findById(permissionId);
        group.attachPermission(permission);
    }

    @Transactional
    public void detachPermission(Long groupId, Long permissionId) {
        Group group = this.findById(groupId);
        Permission permission = permissionService.findById(permissionId);
        group.detachPermission(permission);
    }
}
