package com.crypto.tokens.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
public class EnrichedTokenDTO {

    @NotEmpty(message = "Symbol field should not be empty")
    private String symbol;

    @NotEmpty(message = "Token field should not be empty")
    private String token;

    private String volume;
    private String price;
}
