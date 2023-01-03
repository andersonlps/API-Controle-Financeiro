package com.api.meusgastos.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.meusgastos.domain.model.CentroDeCusto;
import com.api.meusgastos.domain.model.Usuario;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long> {
    
    List<CentroDeCusto> findByUsuario(Usuario usuario);
}
