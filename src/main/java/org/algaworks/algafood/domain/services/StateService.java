package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.StateNotFoundException;
import org.algaworks.algafood.domain.models.State;
import org.algaworks.algafood.domain.repositories.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StateService {

    private static final String MSG_STATE_IN_USE = "State %d cannot be removed as it is in use.";
    private final StateRepository stateRepository;

    public State findById(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new StateNotFoundException(id));
    }

    public Page<State> findAll(Pageable pageable) {
        return stateRepository.findAll(pageable);
    }

    @Transactional
    public State add(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            stateRepository.delete(findById(id));
            stateRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_STATE_IN_USE, id));
        }
    }
}
