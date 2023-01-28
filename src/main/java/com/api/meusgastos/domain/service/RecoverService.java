package com.api.meusgastos.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.meusgastos.config.EmailConfig;
import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.TokenSenha;
import com.api.meusgastos.domain.model.Usuario;
import com.api.meusgastos.domain.repository.TokenSenhaRepository;
import com.api.meusgastos.domain.repository.UsuarioRepository;

@Service
public class RecoverService {

  Random random = new Random();

  @Autowired
  BCryptPasswordEncoder encoder;

  @Autowired
  private EmailConfig emailConfig;

  @Autowired
  private TokenSenhaRepository tokenSenhaRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  public void recuperarSenha(String email) {

    // checar se o email corresponde a um usuario
    Optional<Usuario> OptUsuario = usuarioRepository.findByEmail(email);
    if (OptUsuario.isEmpty()) {
      throw new ResourceNotFoundException("Email não encontrado!");
    }
    // gerar chave aleatoria
    String token = "";
    for (int i = 0; i < 4; i++) {
      token = "" + token + random.nextInt(10);
    }
    // String token = UUID.randomUUID().toString();
    System.out.println(token);
    TokenSenha tokenSenha = new TokenSenha(token, OptUsuario.get(), LocalDateTime.now().plusMinutes(5));

    tokenSenhaRepository.save(tokenSenha);
    // enviar email
    // com o link valindando o token na URl
    try {

      emailConfig.sendEmail(email, "Redefinição de Senha",
          tokenSenha.getToken());
    } catch (Exception e) {
      throw new ResourceNotFoundException("Email não encontrado!");
    }
  }

  public Long encontrarUsuarioId(String tokenSenha) {
    Optional<TokenSenha> optional = tokenSenhaRepository.findByToken(tokenSenha);
    if (!optional.isPresent()) {
      throw new ResourceNotFoundException("Token não existe.");
    }
    if (optional.get().getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new ResourceNotFoundException("Token expirado.");
    }
    return optional.get().getUser().getId();
  }
}
