package it.alecsferra.letseat.core.service;

import it.alecsferra.letseat.core.model.dto.in.LoginUser;
import it.alecsferra.letseat.core.model.dto.out.LoginResult;
import it.alecsferra.letseat.core.model.entity.User;

import java.util.Optional;

public interface UserService {

    boolean saveUser(User user);

    Optional<User> findByUsername(String username);

    LoginResult generateToken(LoginUser loginUser);

}
