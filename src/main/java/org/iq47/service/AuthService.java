package org.iq47.service;

import org.iq47.model.UserRepository;
import org.iq47.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Qualifier("userRepositoryImpl")
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user != null) {
            List<SimpleGrantedAuthority> grantedAuthorities = (List<SimpleGrantedAuthority>) user.getAuthorities();
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            return null;
        }
    }

    public void saveUser(User user) {
        User userRes = repository.findByUsername(user.getUsername());
        if (userRes != null) return;

        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

}
