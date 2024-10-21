package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.KitchenNotFoundException;
import org.algaworks.algafood.domain.models.Kitchen;
import org.algaworks.algafood.domain.repositories.KitchenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class KitchenService {

    private static final String MSG_KITCHEN_IN_USE = "Kitchen %d cannot be removed as it is in use.";
    private final KitchenRepository kitchenRepository;

    public Kitchen findById(Long id) {
        return kitchenRepository.findById(id).orElseThrow(() -> new KitchenNotFoundException(id));
    }

    public Page<Kitchen> findAll(Pageable pageable) {
        return kitchenRepository.findAll(pageable);
    }

    @Transactional
    public Kitchen add(Kitchen kitchen) {
        return kitchenRepository.saveAndFlush(kitchen);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, id));
        }
    }
}
