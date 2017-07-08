package com.zeroisbiggerthanone.pcs.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class UserRegistrationForm extends UserLoginForm {

    @NotBlank(message = "Phone number cannot be null")
    @Size(min = 12, max = 12,message = "Phone number must contain 12 symbols")
    private String phoneNumber;

    @NotBlank(message = "Secret digit cannot be null")
    private String secretDigit;

    private String role;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 4, max = 64, message = "Password must be between 4 and 64 symbols")
    private String confirmPassword;

    public UserRegistrationForm() { }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecretDigit() {
        return secretDigit;
    }

    public void setSecretDigit(String secretDigit) {
        this.secretDigit = secretDigit;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
