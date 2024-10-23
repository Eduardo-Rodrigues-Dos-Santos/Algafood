package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePasswordInput {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
