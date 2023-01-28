package com.api.meusgastos.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TokenSenha {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String token;
 
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario user;
 
    private LocalDateTime expiryDate;

    public TokenSenha() {
    }

    public TokenSenha( String token, Usuario user, LocalDateTime expiryDate) {
        
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }
    
}