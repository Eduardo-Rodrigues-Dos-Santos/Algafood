package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.UserModel;
import org.algaworks.algafood.api.models.input.UserInput;
import org.algaworks.algafood.api.models.input.UserUpdateInput;
import org.algaworks.algafood.domain.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private ModelMapper modelMapper;

    public User toUser(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public UserModel toUserModel(User user) {
        return modelMapper.map(user, UserModel.class);
    }

    public void copyToDomainObject(UserUpdateInput userUpdateInput, User user) {
        modelMapper.map(userUpdateInput, user);
    }
}
