package com.anderson.apiusuario.dto.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginReqDto {
    @Email(message = "E-mail inválido !")
    @NotNull(message = "Informe o E-mail !")
    private String email;
    @NotNull(message = "Informe a senha !")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
