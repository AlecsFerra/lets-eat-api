package it.alecsferra.letseat.core.controller;

import it.alecsferra.letseat.core.model.dto.in.LoginUser;
import it.alecsferra.letseat.core.model.dto.in.SignUpUser;
import it.alecsferra.letseat.core.model.dto.out.LoginResult;
import it.alecsferra.letseat.core.model.dto.out.SimpleResult;
import it.alecsferra.letseat.core.model.dto.out.UserInfo;
import it.alecsferra.letseat.core.model.entity.User;
import it.alecsferra.letseat.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static it.alecsferra.letseat.core.Utils.getCurrentUsername;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public SimpleResult register(@RequestBody @Validated SignUpUser userDto, BindingResult bindingResult) {

        SimpleResult result = new SimpleResult();

        if (bindingResult.hasErrors())
            return result;

        User user = modelMapper.map(userDto, User.class);

        boolean success = userService.saveUser(user);

        if (!success)
            result.setMessage("Username already exists.");

        result.setSuccess(success);

        return result;
    }

    @PostMapping("login")
    public LoginResult login(@RequestBody @Validated LoginUser loginUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            LoginResult result = new LoginResult();
            result.setUsername(loginUser.getUsername());
            return result;

        }

        return userService.generateToken(loginUser);
    }

    @GetMapping("/user/me")
    public UserInfo me(){

        String username = getCurrentUsername();

        User user = userService.findByUsername(username).get();

        return modelMapper.map(user, UserInfo.class);

    }
}
