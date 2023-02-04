package com.api.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.meusgastos.config.EmailConfig;
import com.api.meusgastos.domain.exception.ResourceBadRequestException;
import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.Usuario;
import com.api.meusgastos.domain.repository.UsuarioRepository;
import com.api.meusgastos.dto.usuario.UsuarioAtualizarSenhaDto;
import com.api.meusgastos.dto.usuario.UsuarioRequestAtualizarDto;
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

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private RecoverService recoverService;

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
        if (optUsuario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    public UsuarioResponseDto obterPorEmail(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
        if (optUsuario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + email);
        }

        return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
    }

    @Override
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {

        validarUsuario(dto);

        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());

        if (optUsuario.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário com o e-mail: " + dto.getEmail());
        }

        Usuario usuario = mapper.map(dto, Usuario.class);

        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        
        usuario.setId(null);
        usuario.setDataCadastro(new Date());

        try {
            emailConfig.sendEmailCadastroUser(dto.getEmail(), dto.getNome());

        } catch (Exception e) {
            throw new ResourceNotFoundException("Houve um erro no envio do email, favor verificar se o email está correto.");
        }
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDto.class);

    }

    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioRequestAtualizarDto dto) {

        UsuarioResponseDto usuarioBanco = obterPorId(id);
        validarAtualizarUsuario(dto);

        Usuario usuario = mapper.map(dto, Usuario.class);

        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        usuario.setSenha(optUsuario.get().getSenha());

        usuario.setId(id);
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
     usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public UsuarioResponseDto update(String tokenSenha, UsuarioAtualizarSenhaDto usuarioDTO) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(recoverService.encontrarUsuarioId(tokenSenha));
        if (!optionalUsuario.isPresent()) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        Usuario usuarioDB = optionalUsuario.get();
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {

            usuarioDB.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }
        usuarioDB = usuarioRepository.save(usuarioDB);
        return mapper.map(usuarioDB, UsuarioResponseDto.class);

    }

    public UsuarioResponseDto validaToken(String tokenSenha) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(recoverService.encontrarUsuarioId(tokenSenha));
        if (!optionalUsuario.isPresent()) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        Usuario usuarioDB = optionalUsuario.get();
        return mapper.map(usuarioDB, UsuarioResponseDto.class);
    }

    // @Override
    // public void deletar(Long id) {

    //     Optional<Usuario> optUsuario = usuarioRepository.findById(id);
    //     if (optUsuario.isEmpty()) {
    //         throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
    //     }
    //     Usuario usuario = optUsuario.get();

    //     usuario.setDataInativacao(new Date());

    //     usuarioRepository.save(usuario);
    // }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        usuarioRepository.deleteById(id);
    }

    private void validarUsuario(UsuarioRequestDto dto) {
        if (dto.getEmail() == null || dto.getSenha() == null) {
            throw new ResourceBadRequestException("E-mail e senha são obrigatórios");
        }
    }

    private void validarAtualizarUsuario(UsuarioRequestAtualizarDto dto) {
        if (dto.getEmail() == null) {
            throw new ResourceBadRequestException("E-mail é obrigatórios");
        }
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {
        // TODO Auto-generated method stub
        return null;
    }

}
