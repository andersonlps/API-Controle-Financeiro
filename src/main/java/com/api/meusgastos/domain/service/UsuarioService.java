package com.api.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.meusgastos.domain.exception.ResourceBadRequestException;
import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.Usuario;
import com.api.meusgastos.domain.repository.UsuarioRepository;
import com.api.meusgastos.dto.usuario.UsuarioRequestDto;
import com.api.meusgastos.dto.usuario.UsuarioResponseDto;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDto, UsuarioResponseDto> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponseDto> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
            .map(usuario -> mapper.map(usuario, UsuarioResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDto obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(optUsuario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    public UsuarioResponseDto obterPorEmail(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
        if(optUsuario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + email);
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {

        validarUsuario(dto);

        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());

        if(optUsuario.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário com o e-mail: " + dto.getEmail());
        }

        Usuario usuario = mapper.map(dto, Usuario.class);

        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);

        usuario.setId(null);
        usuario.setDataCadastro(new Date());
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {

        UsuarioResponseDto usuarioBanco = obterPorId(id);
        validarUsuario(dto);

        Usuario usuario = mapper.map(dto, Usuario.class);
        
        String senha = passwordEncoder.encode(dto.getSenha());
        usuario.setSenha(senha);

        usuario.setId(id);
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    @Override
    public void deletar(Long id) {

        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if(optUsuario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }
        Usuario usuario = optUsuario.get();

        usuario.setDataInativacao(new Date());

        usuarioRepository.save(usuario);
    }

    private void validarUsuario(UsuarioRequestDto dto) {
        if(dto.getEmail() == null || dto.getSenha() == null) {
            throw new ResourceBadRequestException("E-mail e senha são obrigatórios");
        }
    }
    
}
