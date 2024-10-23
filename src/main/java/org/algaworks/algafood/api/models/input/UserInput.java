package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;
}
