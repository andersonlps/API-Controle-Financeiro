package com.api.meusgastos.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.meusgastos.common.ConversorData;
import com.api.meusgastos.domain.exception.ResourceBadRequestException;
import com.api.meusgastos.domain.exception.ResourceNotFoundException;
import com.api.meusgastos.domain.model.ErroResposta;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResposta> handlerResourceNotFoundException(ResourceNotFoundException ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErroResposta erro = new ErroResposta(
            dataHora, 
            HttpStatus.NOT_FOUND.value(), 
            "Not Found", 
            ex.getMessage());

            return new ResponseEntity<ErroResposta>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErroResposta> handlerResourceBadRequestException(ResourceBadRequestException ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErroResposta erro = new ErroResposta(
            dataHora, 
            HttpStatus.BAD_REQUEST.value(), 
            "Not Found", 
            ex.getMessage());

            return new ResponseEntity<ErroResposta>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handlerRequestException(Exception ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErroResposta erro = new ErroResposta(
            dataHora, 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Not Found", 
            ex.getMessage());

            return new ResponseEntity<ErroResposta>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
