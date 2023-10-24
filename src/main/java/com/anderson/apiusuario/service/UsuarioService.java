package com.anderson.apiusuario.service;

import com.anderson.apiusuario.dto.req.AtualizacaoUsuarioReqDto;
import com.anderson.apiusuario.dto.req.LoginReqDto;
import com.anderson.apiusuario.dto.req.UsuarioReqDto;
import com.anderson.apiusuario.dto.resp.UsuarioRespDto;
import com.anderson.apiusuario.exception.BadRequestException;
import com.anderson.apiusuario.exception.ConflitoException;
import com.anderson.apiusuario.model.Usuario;
import com.anderson.apiusuario.auth.enums.Role;
import com.anderson.apiusuario.auth.service.AutheticationService;
import com.anderson.apiusuario.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private  final ModelMapper modelMapper;
    private final  AutheticationService autheticationService;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, com.anderson.apiusuario.auth.service.AutheticationService autheticationService) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.autheticationService = autheticationService;
    }
    public  UsuarioRespDto loginUsuario(LoginReqDto loginReqDto){
        return autheticationService.authenticate(loginReqDto);

    }
    @Transactional
    public UsuarioRespDto cadastrarUsuario(UsuarioReqDto usuarioReqDto) {
        return autheticationService.register(usuarioReqDto);
    }

    public List<UsuarioRespDto> buscarUsuario() {
      return   usuarioRepository.findAllByRole(Role.USER)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioRespDto.class))
                .collect(Collectors.toList());

    }
    @Transactional
    public UsuarioRespDto atualizarUsuario(UUID id, AtualizacaoUsuarioReqDto atualizacaoUsuarioReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuario= usuarioRepository.findByEmail(username);
        validarAtualizacaoUsuario(id,usuario.get(),atualizacaoUsuarioReqDto);
        usuario.get().setEmail(atualizacaoUsuarioReqDto.getEmail());
        usuario.get().setNome(atualizacaoUsuarioReqDto.getNome());
        return modelMapper.map(usuarioRepository.save(usuario.get()), UsuarioRespDto.class);
    }

    private void validarAtualizacaoUsuario(UUID id,Usuario usuario, AtualizacaoUsuarioReqDto atualizacaoUsuarioReqDto) {
        if(!usuario.getId().equals(id)){
            throw new BadRequestException("Usuário informado  não é o mesmo logado!");
        }
        Optional<Usuario> usuarioBuscado= usuarioRepository.findByEmail(atualizacaoUsuarioReqDto.getEmail());
        if(usuarioBuscado.isPresent() && !usuarioBuscado.get().getEmail().equals(usuario.getEmail())){
            throw new ConflitoException("O E-mail informado já está em uso!");
        }

    }
}
