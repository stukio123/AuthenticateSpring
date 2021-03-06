package com.example.userservice.Services;

import com.example.userservice.Models.Token;
import com.example.userservice.Repositories.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Long saveToken(Token token){
        return tokenRepository.save(token).getId();
    }

    public Optional<Token> getToken(String payload){
        return tokenRepository.findByPayload(payload);
    }

    public int setConfirmedAt(String payload){
        return tokenRepository.updateConfirmedAt(payload, LocalDateTime.now());
    }
}
