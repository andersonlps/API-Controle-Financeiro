package com.api.meusgastos.dto.usuario;

import lombok.Data;

@Data
public class LoginRequestDto {
    
    private String email;
    
    private String senha;
}
