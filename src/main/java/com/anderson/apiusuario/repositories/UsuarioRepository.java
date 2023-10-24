package com.anderson.apiusuario.repositories;

import com.anderson.apiusuario.model.Usuario;
import com.anderson.apiusuario.auth.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByRole(Role role);

}
