package com.api.meusgastos.dto.titulo;

import java.time.LocalDate;

import com.api.meusgastos.domain.enums.ETipoTitulo;
import com.api.meusgastos.dto.centrodecusto.CentroDeCustoResponseDto;

import lombok.Data;

@Data
public class TituloResponseDto {
    
    private Long id;

    private String descricao;

    private String observacao;

    private ETipoTitulo tipo;

    private CentroDeCustoResponseDto centroDeCusto;

    private Double valor;

    private LocalDate dataCadastro;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;
    
}
