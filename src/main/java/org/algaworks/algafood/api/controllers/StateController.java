package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.StateMapper;
import org.algaworks.algafood.api.models.StateModel;
import org.algaworks.algafood.api.models.input.StateInput;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.models.State;
import org.algaworks.algafood.domain.services.StateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/states")
@AllArgsConstructor
public class StateController {

    private final StateService stateService;
    private final StateMapper stateMapper;

    @CheckSecurity.State.Consult
    @GetMapping
    public ResponseEntity<Page<StateModel>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(stateService.findAll(pageable).map(stateMapper::toStateModel));
    }

    @CheckSecurity.State.Consult
    @GetMapping(value = "/{id}")
    public ResponseEntity<StateModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(stateMapper.toStateModel(stateService.findById(id)));
    }

    @CheckSecurity.State.Manage
    @PostMapping
    public ResponseEntity<StateModel> add(@RequestBody @Valid StateInput stateInput) {
        StateModel stateModel = stateMapper.toStateModel(stateService.add(stateMapper.toState(stateInput)));
        return new ResponseEntity<>(stateModel, HttpStatus.CREATED);
    }

    @CheckSecurity.State.Manage
    @PutMapping("/{id}")
    public ResponseEntity<StateModel> update(@PathVariable Long id, @Valid @RequestBody StateInput stateInput) {
        State state = stateService.findById(id);
        stateMapper.copyToDomainObject(stateInput, state);
        return ResponseEntity.ok(stateMapper.toStateModel(stateService.add(state)));
    }

    @CheckSecurity.State.Manage
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        stateService.deleteById(id);
    }
}
