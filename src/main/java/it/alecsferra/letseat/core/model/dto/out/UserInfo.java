package it.alecsferra.letseat.core.model.dto.out;

import it.alecsferra.letseat.core.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    private String username;

    private String id;

    private User.Type role;

}
