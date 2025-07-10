package com.carpooling.backend.service;

import com.carpooling.backend.dao.UserRepo;
import com.carpooling.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public User save(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public String findIdByEmail(String email){
        return repo.findByEmail(email).getUserId();
    }

    @Override
    public User findUserById(String userId){
        return repo.findByUserId(userId);
    }
}
