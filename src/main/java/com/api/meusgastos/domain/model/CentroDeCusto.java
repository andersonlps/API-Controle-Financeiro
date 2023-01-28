package com.api.meusgastos.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "centrodecusto")
public class CentroDeCusto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCentrodecusto")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String observacao; 

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "centroDeCusto", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Titulo> titulos; 
}
