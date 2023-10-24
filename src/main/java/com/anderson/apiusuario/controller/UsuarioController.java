package com.anderson.apiusuario.controller;


import com.anderson.apiusuario.dto.req.AtualizacaoUsuarioReqDto;
import com.anderson.apiusuario.dto.req.LoginReqDto;
import com.anderson.apiusuario.dto.req.UsuarioReqDto;
import com.anderson.apiusuario.dto.resp.UsuarioRespDto;
import com.anderson.apiusuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

   private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioRespDto>> getUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuario());
    }
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioRespDto> cadastrarUsuario(@RequestBody @Valid  UsuarioReqDto usuarioReqDto){
       return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioReqDto));
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioRespDto> loginUsuario(@RequestBody @Valid LoginReqDto loginReqDto){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.loginUsuario(loginReqDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespDto> buscarUsuario(@PathVariable("id") UUID id, @RequestBody @Valid AtualizacaoUsuarioReqDto atualizacaoUsuarioReqDto){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id,atualizacaoUsuarioReqDto));
    }
}

