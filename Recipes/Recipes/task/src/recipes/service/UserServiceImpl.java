package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.exception.UserExistsException;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void addUser(User user) {

        Optional<User> o = Optional.ofNullable(this.userRepository.findUserByEmail(user.getEmail()));

        if (o.isPresent()) {
            throw new UserExistsException("(Bad Request)");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        this.userRepository.save(user);
    }
}
