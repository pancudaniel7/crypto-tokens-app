package com.crypto.tokens.controller;

import com.crypto.tokens.model.dto.EnrichedTokenDTO;
import com.crypto.tokens.service.InMemoryTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TokenController {
    private final InMemoryTokenService inMemoryTokenService;

    @GetMapping("/tokens")
    public ResponseEntity<Map<String, EnrichedTokenDTO>> getTokens() {
        Map<String, EnrichedTokenDTO> inMemoryTokens = inMemoryTokenService.getInMemoryTokens();

        if (inMemoryTokens.size() == 0) {
            return status(503).header("Retry-After", "3").build();
        }

        return ok().body(inMemoryTokens);
    }

    @PostMapping("/tokens")
    public ResponseEntity<EnrichedTokenDTO> createToken(@Valid @RequestBody EnrichedTokenDTO enrichedTokenDTO) {
        enrichedTokenDTO = inMemoryTokenService.createInMemoryToken(enrichedTokenDTO);
        return ResponseEntity.status(201).body(enrichedTokenDTO);
    }

}
