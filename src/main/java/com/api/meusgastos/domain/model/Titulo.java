package com.api.meusgastos.domain.model;

import java.util.Date;
import java.util.List;

import com.api.meusgastos.domain.enums.ETipoTitulo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "titulo")
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTitulo")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(nullable = false)
    private ETipoTitulo tipo;

    // @ManyToOne
    // @JoinColumn(name = "idCentroDeCusto")
    // private CentroDeCusto centroDeCusto;

    @ManyToMany
    @JoinTable(
        name = "titulo_centrodecusto",
        joinColumns = @JoinColumn(name = "idTitulo"),
        inverseJoinColumns = @JoinColumn(name = "idCentroDeCusto")
    )
    private List<CentroDeCusto> centrosDeCustos;

    @Column(nullable = false)
    private Double valor;

    private Date dataCadastro;

    private Date dataReferencia;

    private Date dataVencimento;

    private Date dataPagamento;

    @Column(columnDefinition = "TEXT")
    private String observacao;
    
}
