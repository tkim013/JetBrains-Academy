package com.example.AntiFraudSystem.security.service;

import com.example.AntiFraudSystem.entity.User;
import com.example.AntiFraudSystem.exception.NotFoundException;
import com.example.AntiFraudSystem.repository.RoleRepository;
import com.example.AntiFraudSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // find user in user table, return userdetails
        Optional<User> o = userRepository.findByUsername(username.toLowerCase());

        if (o.isEmpty()) {
            throw new NotFoundException("not found");
        }

        return new UserDetailsImpl(o.get(), getAuthorities(o.get()));
    }

    private Collection<GrantedAuthority> getAuthorities(User user){

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleRepository.findByCode(user.getRole().toLowerCase()).getName()));

        return authorities;
    }
}
