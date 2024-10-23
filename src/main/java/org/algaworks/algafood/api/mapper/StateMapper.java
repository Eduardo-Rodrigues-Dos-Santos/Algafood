package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.StateModel;
import org.algaworks.algafood.api.models.input.StateInput;
import org.algaworks.algafood.domain.models.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StateMapper {

    private ModelMapper modelMapper;

    public StateModel toStateModel(State state) {
        return modelMapper.map(state, StateModel.class);
    }

    public State toState(StateInput stateInput) {
        return modelMapper.map(stateInput, State.class);
    }

    public void copyToDomainObject(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }
}


