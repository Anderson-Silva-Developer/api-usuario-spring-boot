package com.anderson.apiusuario.dto.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AtualizacaoUsuarioReqDto {
    @NotNull(message = "Informe o nome do usuário!")
    private String nome;
    @Email(message = "E-mail inválido !")
    @NotNull(message = "Informe o E-mail !")
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
