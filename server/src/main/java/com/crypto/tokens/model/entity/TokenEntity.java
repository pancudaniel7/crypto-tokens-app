package com.crypto.tokens.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @Column
    private String symbol;

    @Column
    private String token;
}