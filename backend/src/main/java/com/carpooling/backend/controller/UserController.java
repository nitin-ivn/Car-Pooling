package com.carpooling.backend.controller;

import com.carpooling.backend.model.User;
import com.carpooling.backend.service.JwtService;
import com.carpooling.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("register")
    public User register(@RequestBody User user){
        System.out.println(user);
        return service.save(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        System.out.println(user);

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail(),service.findIdByEmail(user.getEmail()));
        }else{
            return "Failed";
        }
    }

    @GetMapping("getUser/{userId}")
    public User getUserById(@PathVariable String userId){
        System.out.println(userId);
        System.out.println(service.findUserById(userId));
        return service.findUserById(userId);
    }
}
