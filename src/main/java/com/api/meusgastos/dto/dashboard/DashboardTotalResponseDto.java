package com.api.meusgastos.dto.dashboard;

import lombok.Data;

@Data
public class DashboardTotalResponseDto {
    
    private Double totalApagar;

    private Double totalAreceber;

    private Double saldo;

    public DashboardTotalResponseDto() {
    }

    public DashboardTotalResponseDto(Double totalApagar, Double totalAreceber, Double saldo) {
        this.totalApagar = totalApagar;
        this.totalAreceber = totalAreceber;
        this.saldo = saldo;
    }

}


