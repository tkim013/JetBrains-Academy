package recipes.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.exception.EmailNotFoundException;
import recipes.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new EmailNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user);
    }
}