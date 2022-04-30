package com.crypto.tokens.service;

import com.crypto.tokens.model.entity.TokenEntity;
import com.crypto.tokens.repository.TokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;

    public List<TokenEntity> getAll() {
        return tokenRepo.findAll();
    }
}
