package com.api.meusgastos.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.meusgastos.domain.model.TokenSenha;

@Repository

    public interface TokenSenhaRepository extends JpaRepository<TokenSenha, Long> {
        Optional<TokenSenha> findByToken(String token);
}
