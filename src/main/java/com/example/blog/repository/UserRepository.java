package com.example.blog.repository;

import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    //@Query(value = "select * from user where username = ?1 and password = ?2", nativeQuery = true)
    //User login(String username, String password);
}
