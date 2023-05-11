package com.appteam.template.repository;

import com.appteam.template.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
/*
    @Query("SELECT u FROM User u WHERE u.userName = :username")
    public User getUserByUsername(@Param("username") String username);

 */
}
