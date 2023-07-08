package com.example.amigossecurity.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository  extends JpaRepository<MyUser, Integer> {

    Optional<MyUser> findByEmail(String email);
}
