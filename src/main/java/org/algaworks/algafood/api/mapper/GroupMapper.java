package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.GroupModel;
import org.algaworks.algafood.api.models.input.GroupInput;
import org.algaworks.algafood.domain.models.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupMapper {

    private ModelMapper modelMapper;

    public GroupModel toGroupModel(Group group) {
        return modelMapper.map(group, GroupModel.class);
    }

    public Group toGroup(GroupInput groupInput) {
        return modelMapper.map(groupInput, Group.class);
    }

    public void copyToDomainObject(GroupInput groupInput, Group group) {
        modelMapper.map(groupInput, group);
    }
}
