package com.codeprism.springboot.TodoMicroService.daos;

import com.codeprism.springboot.TodoMicroService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String> {

  // Named Strategy
  Optional<User> findUserByEmail(String email);

    // Query Strategy
//  @Query(value = "SELECT * from users where email = :email", nativeQuery = true)
//  Optional<User> findUserByEmailQuery(@Param("email") String email);

  // Native Method
//  User findOne(String email);

}
