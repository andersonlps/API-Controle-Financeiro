package com.api.meusgastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.meusgastos.domain.service.TituloService;
import com.api.meusgastos.dto.titulo.TituloRequestDto;
import com.api.meusgastos.dto.titulo.TituloResponseDto;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/titulos")
public class TituloController {

    @Autowired
    private TituloService tituloService;
    
    @GetMapping
    public ResponseEntity<List<TituloResponseDto>> obterTodos() {

        return ResponseEntity.ok().body(tituloService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TituloResponseDto> obterPorId(@PathVariable Long id) {

        return ResponseEntity.ok().body(tituloService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<TituloResponseDto> cadastrar(@RequestBody TituloRequestDto dto) {
        TituloResponseDto response = tituloService.cadastrar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TituloResponseDto> atualizar(@PathVariable Long id, @RequestBody TituloRequestDto dto) {
        return ResponseEntity.ok(tituloService.atualizar(id, dto));
    }

    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> pagar(@PathVariable Long id) {
        tituloService.pagar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/despagar/{id}")
    public ResponseEntity<?> despagar(@PathVariable Long id) {
        tituloService.despagar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        tituloService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
