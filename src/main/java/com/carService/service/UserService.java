package com.carService.service;

import com.carService.model.Role;
import com.carService.model.User;
import com.carService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        Collection<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(authorities).build();
        return userDetails;
    }

    public User registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        } else if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
            return null;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //user.setRole(roleRepository.findByName("USER"));
            return userRepository.save(user);
        }
        
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
