package com.api.meusgastos.dto.usuario;

import lombok.Data;

@Data
public class UsuarioRequestDto {
    
    private String nome;

    private String email;

    private String senha;

    private String foto;

}
