package com.appteam.template.repository;

import com.appteam.template.data.Token;
import com.appteam.template.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("SELECT u FROM Token u WHERE u.key = :token")
    public Token getTokenByKey(@Param("token") String key);
}
