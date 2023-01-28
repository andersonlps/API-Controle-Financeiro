package com.api.meusgastos.dto.titulo;

import java.time.LocalDate;

import com.api.meusgastos.domain.enums.ETipoTitulo;
import com.api.meusgastos.dto.centrodecusto.CentroDeCustoRequestDto;

import lombok.Data;

@Data
public class TituloRequestDto {
    
    private Long id;

    private String descricao;

    private String observacao;

    private ETipoTitulo tipo;

    private CentroDeCustoRequestDto centroDeCusto;

    private Double valor;

    private LocalDate dataCadastro;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;
    
}
