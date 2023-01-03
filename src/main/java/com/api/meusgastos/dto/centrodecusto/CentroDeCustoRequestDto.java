package com.api.meusgastos.dto.centrodecusto;

import lombok.Data;

@Data
public class CentroDeCustoRequestDto {
    
    private Long id;

    private String descricao;

    private String observacao;
}
