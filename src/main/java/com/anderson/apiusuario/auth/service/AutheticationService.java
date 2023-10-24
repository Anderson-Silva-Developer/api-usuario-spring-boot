package com.anderson.apiusuario.auth.service;

import com.anderson.apiusuario.dto.req.LoginReqDto;
import com.anderson.apiusuario.dto.req.UsuarioReqDto;
import com.anderson.apiusuario.dto.resp.UsuarioRespDto;
import com.anderson.apiusuario.auth.enums.Role;
import com.anderson.apiusuario.exception.ConflitoException;
import com.anderson.apiusuario.model.Usuario;
import com.anderson.apiusuario.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AutheticationService {

    private final UsuarioRepository usuarioRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    public AutheticationService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public UsuarioRespDto register(UsuarioReqDto request) {
        validarRegister(request);
        var usuario=new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setNome(request.getNome());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Role.USER);
        usuario=usuarioRepository.save(usuario);
        var jwtToken=jwtService.generateToken(usuario);
        UsuarioRespDto usuarioRespDto=modelMapper.map(usuario, UsuarioRespDto.class);
        usuarioRespDto.setToken(jwtToken);
        return usuarioRespDto;
    }

    private void validarRegister(UsuarioReqDto request)  {
        Optional<Usuario> usuario=usuarioRepository.findByEmail(request.getEmail());
        if(usuario.isPresent()){
            throw  new ConflitoException("Este email já está em uso por outro usuário !");
        }
    }

    public UsuarioRespDto authenticate(LoginReqDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );
        Usuario usuario=usuarioRepository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));
        var jwtToken=jwtService.generateToken(usuario);
        UsuarioRespDto usuarioRespDto=modelMapper.map(usuario, UsuarioRespDto.class);
        usuarioRespDto.setToken(jwtToken);
        return usuarioRespDto;

    }
}
