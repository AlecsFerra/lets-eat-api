package it.alecsferra.letseat.core.model.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}