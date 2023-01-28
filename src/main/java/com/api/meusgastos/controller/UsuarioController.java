package com.api.meusgastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.meusgastos.domain.service.RecoverService;
import com.api.meusgastos.domain.service.UsuarioService;
import com.api.meusgastos.dto.usuario.UsuarioAtualizarSenhaDto;
import com.api.meusgastos.dto.usuario.UsuarioRequestAtualizarDto;
import com.api.meusgastos.dto.usuario.UsuarioRequestDto;
import com.api.meusgastos.dto.usuario.UsuarioResponseDto;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecoverService recoverService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obterTodos() {
        return ResponseEntity.ok().body(usuarioService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto usuario = usuarioService.cadastrar(dto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id, @RequestBody UsuarioRequestAtualizarDto dto) {
        UsuarioResponseDto usuario = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> buscarPorEmail(@PathVariable String email) {
        UsuarioResponseDto usuario = usuarioService.obterPorEmail(email);
        return ResponseEntity.ok(usuario);

    }

    @PutMapping("/novaSenha/{tokenSenha}")
    public ResponseEntity<UsuarioResponseDto> atualizaUsuario(@PathVariable String tokenSenha,
            @RequestBody UsuarioAtualizarSenhaDto usuario) {
        return ResponseEntity.ok(usuarioService.update(tokenSenha, usuario));
    }

    @GetMapping("/token/{tokenSenha}")
    public ResponseEntity<UsuarioResponseDto> validaToken(@PathVariable String tokenSenha) {
        return ResponseEntity.ok(usuarioService.validaToken(tokenSenha));
    }

    @PostMapping("/recover/{email}")
    public ResponseEntity<Void> recuperarSenha(@PathVariable String email) {
        recoverService.recuperarSenha(email);
        return ResponseEntity.ok().build();
    }

}
