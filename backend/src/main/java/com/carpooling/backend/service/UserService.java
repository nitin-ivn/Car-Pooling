package com.carpooling.backend.service;

import com.carpooling.backend.model.User;

public interface UserService {
    User save(User user);

    String findIdByEmail(String email);

    User findUserById(String userId);
}
