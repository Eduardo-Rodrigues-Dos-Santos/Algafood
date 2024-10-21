package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.GroupMapper;
import org.algaworks.algafood.api.models.GroupModel;
import org.algaworks.algafood.api.models.input.GroupInput;
import org.algaworks.algafood.domain.models.Group;
import org.algaworks.algafood.domain.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private GroupService groupService;
    private GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupModel>> findAll() {
        List<GroupModel> groups = groupService.findAll().stream().map(groupMapper::toGroupModel).toList();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(groupMapper.toGroupModel(groupService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<GroupModel> add(@Valid @RequestBody GroupInput groupInput) {
        Group group = groupService.add(groupMapper.toGroup(groupInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMapper.toGroupModel(group));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupModel> update(@PathVariable Long id, @Valid @RequestBody GroupInput groupInput) {
        Group group = groupService.findById(id);
        groupMapper.copyToDomainObject(groupInput, group);
        return ResponseEntity.ok(groupMapper.toGroupModel(groupService.add(group)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        groupService.deleteById(id);
    }
}
