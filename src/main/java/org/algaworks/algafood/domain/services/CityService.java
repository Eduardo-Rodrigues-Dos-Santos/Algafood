package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.CityNotFoundException;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.models.City;
import org.algaworks.algafood.domain.models.State;
import org.algaworks.algafood.domain.repositories.CityRepository;
import org.algaworks.algafood.infrastructure.repository.specifications.CitySpecsFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CityService {

    private static final String MSG_CITY_IN_USE = "City %d cannot be removed as it is in use.";
    private final CityRepository cityRepository;
    private final StateService stateService;

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    public Page<City> citiesByState(Long stateId, Pageable pageable) {
        return cityRepository.findAll(CitySpecsFactory.citiesByState(stateId), pageable);
    }

    @Transactional
    public City add(City city) {
        State state = stateService.findById(city.getState().getId());
        city.setState(state);
        return cityRepository.save(city);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.findById(id);
            cityRepository.deleteById(id);
            cityRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_CITY_IN_USE, id));
        }
    }
}
