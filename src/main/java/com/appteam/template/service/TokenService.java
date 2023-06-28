package com.appteam.template.service;

import com.appteam.template.data.Token;
import com.appteam.template.dto.TokenData;
import com.appteam.template.repository.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("tokenService")
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public TokenService(){
    }

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenData saveToken(TokenData tokenData) {
        Token token = new Token(tokenData);
        return new TokenData(tokenRepository.save(token));
    }

    public Boolean deleteToken(String key) {
        Token token = tokenRepository.getTokenByKey(key);
        if (token != null) {
            tokenRepository.delete(token);
            return true;
        }
        return false;
    }

    public List<TokenData> getAllTokens() {
        List<Token> tokens = tokenRepository.findAll();
        List<TokenData> data = new ArrayList<>();
        tokens.forEach(token -> data.add(new TokenData(token)));
        return data;
    }

    public TokenData getTokenByKey(String key) throws EntityNotFoundException {
        Token token = tokenRepository.getTokenByKey(key);
        if(token != null) {
            return new TokenData(token);
        } else {
            return null;
        }
    }
}
