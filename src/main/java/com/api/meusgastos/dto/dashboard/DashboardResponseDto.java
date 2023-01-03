package com.api.meusgastos.dto.dashboard;

import java.util.List;

import com.api.meusgastos.dto.titulo.TituloResponseDto;

import lombok.Data;

@Data
public class DashboardResponseDto {
    
    private Double totalApagar;

    private Double totalAreceber;

    private Double saldo;

    private List<TituloResponseDto> titulosApagar;

    private List<TituloResponseDto> titulosAreceber;

    public DashboardResponseDto() {
    }

    public DashboardResponseDto(Double totalApagar, Double totalAreceber, Double saldo,
            List<TituloResponseDto> titulosApagar, List<TituloResponseDto> titulosAreceber) {
        this.totalApagar = totalApagar;
        this.totalAreceber = totalAreceber;
        this.saldo = saldo;
        this.titulosApagar = titulosApagar;
        this.titulosAreceber = titulosAreceber;
    }

}


