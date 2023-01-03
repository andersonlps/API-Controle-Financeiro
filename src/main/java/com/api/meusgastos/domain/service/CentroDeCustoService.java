package com.api.meusgastos.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.CentroDeCusto;
import com.api.meusgastos.domain.model.Usuario;
import com.api.meusgastos.domain.repository.CentroDeCustoRepository;
import com.api.meusgastos.dto.centrodecusto.CentroDeCustoRequestDto;
import com.api.meusgastos.dto.centrodecusto.CentroDeCustoResponseDto;

@Service
public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDto, CentroDeCustoResponseDto> {

    @Autowired
    private CentroDeCustoRepository centroDeCustoRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDto> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
       List<CentroDeCusto> lista = centroDeCustoRepository.findByUsuario(usuario);
        
       return lista.stream()
                .map(centroDeCusto -> mapper.map(centroDeCusto, CentroDeCustoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CentroDeCustoResponseDto obterPorId(Long id) {
        Optional<CentroDeCusto> optCentroDeCusto = centroDeCustoRepository.findById(id);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(optCentroDeCusto.isEmpty() || optCentroDeCusto.get().getUsuario().getId() != usuario.getId()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o centro de custo com o id " + id);
        }

        return mapper.map(optCentroDeCusto.get(), CentroDeCustoResponseDto.class);
    }

    @Override
    public CentroDeCustoResponseDto cadastrar(CentroDeCustoRequestDto dto) {
        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        centroDeCusto.setUsuario(usuario);

        centroDeCusto.setId(null);

        centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
        
        return mapper.map(centroDeCusto, CentroDeCustoResponseDto.class);
    }

    @Override
    public CentroDeCustoResponseDto atualizar(Long id, CentroDeCustoRequestDto dto) {
       
        obterPorId(id);

        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        centroDeCusto.setUsuario(usuario);

        centroDeCusto.setId(id);        

        centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
        
        return mapper.map(centroDeCusto, CentroDeCustoResponseDto.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        centroDeCustoRepository.deleteById(id); 
    }
    
}
