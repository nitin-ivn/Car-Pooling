package com.carpooling.backend.service;

import com.carpooling.backend.dao.UserRepo;
import com.carpooling.backend.model.User;
import com.carpooling.backend.model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);

        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user 404");
        }

        return new UserPrinciple(user);
    }
}
