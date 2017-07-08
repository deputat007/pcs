package com.zeroisbiggerthanone.pcs.entities;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginForm {

    @NotBlank(message = "Login cannot be null")
    @Size(min = 5, max = 64, message = "Login must be between 5 and 64 characters")
    private String login;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 4, max = 64, message = "Password must be between 5 and 64 symbols")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
