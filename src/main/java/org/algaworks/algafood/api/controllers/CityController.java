package org.algaworks.algafood.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.mapper.CityMapper;
import org.algaworks.algafood.api.models.CityModel;
import org.algaworks.algafood.api.models.input.CityInput;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.StateNotFoundException;
import org.algaworks.algafood.domain.models.City;
import org.algaworks.algafood.domain.services.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @CheckSecurity.City.Consult
    @GetMapping("/{id}")
    public ResponseEntity<CityModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cityMapper.toCityModel(cityService.findById(id)));
    }

    @CheckSecurity.City.Consult
    @GetMapping(path = "by-state-id", params = "stateId")
    public ResponseEntity<Page<CityModel>> citiesByState(@RequestParam("stateId") Long stateId,
                                                         @PageableDefault() Pageable pageable) {
        return ResponseEntity.ok(cityService.citiesByState(stateId, pageable).map(cityMapper::toCityModel));
    }

    @CheckSecurity.City.Manage
    @PostMapping
    public ResponseEntity<CityModel> add(@RequestBody @Valid CityInput cityInput) {
        try {
            CityModel cityModel = cityMapper.toCityModel(cityService.add(cityMapper.toCity(cityInput)));
            return ResponseEntity.status(HttpStatus.CREATED).body(cityModel);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.City.Manage
    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@PathVariable Long id, @RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityService.findById(id);
            cityMapper.copyToDomainObject(cityInput, city);
            return ResponseEntity.ok(cityMapper.toCityModel(cityService.add(city)));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.City.Manage
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        cityService.deleteById(id);
    }
}
