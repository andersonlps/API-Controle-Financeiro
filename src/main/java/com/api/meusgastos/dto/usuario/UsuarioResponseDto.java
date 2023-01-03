package com.api.meusgastos.dto.usuario;

import java.util.Date;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    
    private Long id;

    private String nome;

    private String email;

    private String foto;

    private Date dataCadastro;

    private Date dataInativacao;

}
