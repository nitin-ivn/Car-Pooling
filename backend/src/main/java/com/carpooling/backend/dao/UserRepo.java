package com.carpooling.backend.dao;

import com.carpooling.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    User findByEmail(String email);
    User findByUserId(String userId);
}
