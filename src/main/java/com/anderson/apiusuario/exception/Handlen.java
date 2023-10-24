package com.anderson.apiusuario.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handlen {

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<DetalhesErro> handlenConflitoExceptionException(ConflitoException e) {
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Conflito de informações já cadastradas!");
        erro.setMensagem(e.getMessage());
        erro.setStatus(409L);
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DetalhesErro> handlenBadRequestExceptionException(BadRequestException e) {
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Informações não aceitas!");
        erro.setMensagem(e.getMessage());
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }


}
