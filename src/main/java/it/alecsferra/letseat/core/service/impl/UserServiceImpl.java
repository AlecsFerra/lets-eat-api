package it.alecsferra.letseat.core.service.impl;

import it.alecsferra.letseat.core.jwt.JwtUtils;
import it.alecsferra.letseat.core.model.dto.in.LoginUser;
import it.alecsferra.letseat.core.model.dto.out.LoginResult;
import it.alecsferra.letseat.core.model.entity.User;
import it.alecsferra.letseat.core.repository.UserRepository;
import it.alecsferra.letseat.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.signing-key}")
    private String signingKey;

    @Value("${jwt.expire-time}")
    private long expireTime;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean saveUser(User user) {

        Optional<User> oldUser = userRepository.findByUsername(user.getUsername());

        if (oldUser.isPresent())
            return false; // Return false if there is an error in saving process

        // Encrypt password
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        user.setRole(User.Type.USER);

        userRepository.save(user);
        return true;

    }

    @Override
    public LoginResult generateToken(LoginUser loginUser) {

        LoginResult result = new LoginResult();

        String username = loginUser.getUsername();

        result.setUsername(username);

        Optional<User> user = findByUsername(username);

        if(!user.isPresent()) return result;

        boolean passwordCheck =
                passwordEncoder.matches(loginUser.getPassword(), user.get().getPassword());

        if (passwordCheck){

            long expDate = System.currentTimeMillis() + expireTime*1000;
            String token = JwtUtils.getToken(username, expDate, signingKey.getBytes());
            result.setToken(token);
            result.setExpireDate(expDate);

        }

        return result;

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}