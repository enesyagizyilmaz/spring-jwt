package com.spring.springjwt.repository;

import com.spring.springjwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    // Since email is unique, we'll find users by email
    Optional<User> findByEmail(String email);

    User getUserById(Long id);
}
