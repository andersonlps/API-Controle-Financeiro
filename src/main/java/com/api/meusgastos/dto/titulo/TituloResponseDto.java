package com.api.meusgastos.dto.titulo;

import java.util.Date;
import java.util.List;

import com.api.meusgastos.domain.enums.ETipoTitulo;
import com.api.meusgastos.dto.centrodecusto.CentroDeCustoResponseDto;

import lombok.Data;

@Data
public class TituloResponseDto {
    
    private Long id;

    private String descricao;

    private String observacao;

    private ETipoTitulo tipo;

    private List<CentroDeCustoResponseDto> centrosDeCustos;

    private Double valor;

    private Date dataCadastro;

    private Date dataReferencia;

    private Date dataVencimento;

    private Date dataPagamento;
    
}
