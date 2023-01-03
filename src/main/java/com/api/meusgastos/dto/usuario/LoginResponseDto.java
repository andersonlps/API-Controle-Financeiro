package com.api.meusgastos.dto.usuario;

import lombok.Data;

@Data
public class LoginResponseDto {
    
    private String token;

    private UsuarioResponseDto usuario;
}
