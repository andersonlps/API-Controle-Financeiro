package com.api.meusgastos.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.meusgastos.domain.exception.ResourceBadRequestException;
import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.Titulo;
import com.api.meusgastos.domain.model.Usuario;
import com.api.meusgastos.domain.repository.TituloRepository;
import com.api.meusgastos.dto.titulo.TituloRequestDto;
import com.api.meusgastos.dto.titulo.TituloResponseDto;

@Service
public class TituloService implements ICRUDService<TituloRequestDto, TituloResponseDto> {

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TituloResponseDto> obterTodos() {
       
       Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       List<Titulo> titulos = tituloRepository.findByUsuario(usuario);
        
       return titulos.stream()
                .map(titulo -> mapper.map(titulo, TituloResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TituloResponseDto obterPorId(Long id) {
        Optional<Titulo> optTitulo = tituloRepository.findById(id);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(optTitulo.isEmpty() || optTitulo.get().getUsuario().getId() != usuario.getId()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o título com o id " + id);
        }

        return mapper.map(optTitulo.get(), TituloResponseDto.class);
    }

    @Override
    public TituloResponseDto cadastrar(TituloRequestDto dto) {

        validarTitulo(dto);

        Titulo titulo = mapper.map(dto, Titulo.class);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(null);
        titulo.setDataCadastro(LocalDate.now());
        titulo = tituloRepository.save(titulo);

        return mapper.map(titulo, TituloResponseDto.class);
    }

    @Override
    public TituloResponseDto atualizar(Long id, TituloRequestDto dto) {
       
        TituloResponseDto tituloBanco = obterPorId(id);
        validarTitulo(dto);

        Titulo titulo = mapper.map(dto, Titulo.class);
        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        titulo.setUsuario(usuario);

        titulo.setId(id);   
        titulo.setDataCadastro(tituloBanco.getDataCadastro());     
        titulo = tituloRepository.save(titulo);
        
        return mapper.map(titulo, TituloResponseDto.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        tituloRepository.deleteById(id); 
    }

    public void pagar(Long id) {

        Optional<Titulo> optTitulo = tituloRepository.findById(id);
        if (optTitulo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
        }
        Titulo titulo = optTitulo.get();

        titulo.setDataPagamento(LocalDate.now());

        tituloRepository.save(titulo);
    }

    public void despagar(Long id) {

        Optional<Titulo> optTitulo = tituloRepository.findById(id);
        if (optTitulo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
        }
        Titulo titulo = optTitulo.get();

        titulo.setDataPagamento(null);

        tituloRepository.save(titulo);
    }

    public List<TituloResponseDto> obterPorDataDeVencimento(String periodoInicial, String periodoFinal) {
        List<Titulo> titulos = tituloRepository.obterFluxoCaixaPorDataVencimento(periodoInicial, periodoFinal);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return titulos.stream()
                .filter(titulo -> titulo.getUsuario().getId().equals(usuario.getId()))
                .map(titulo -> mapper.map(titulo, TituloResponseDto.class))
                .collect(Collectors.toList());

    }

    private void validarTitulo(TituloRequestDto dto) {
        
        if(dto.getTipo() == null || 
           dto.getDataVencimento() == null || 
           dto.getValor() == null ||
           dto.getDescricao() == null) {

            throw new ResourceBadRequestException("Os campos Tipo, Data de vencimento, valor e descrição são obrigatórios");
        }
    }
    
}
