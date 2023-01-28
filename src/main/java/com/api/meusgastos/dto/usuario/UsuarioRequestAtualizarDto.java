package com.api.meusgastos.dto.usuario;

import lombok.Data;

@Data
public class UsuarioRequestAtualizarDto {
    
    private String nome;

    private String email;

    private String foto;

}
