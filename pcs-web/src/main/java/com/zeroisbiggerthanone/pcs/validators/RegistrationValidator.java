package com.zeroisbiggerthanone.pcs.validators;

import com.zeroisbiggerthanone.pcs.entities.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.zeroisbiggerthanone.pcs.services.RegistrationService;

import java.util.Locale;

/**
 * Validates the user input from Registration form
 */
@Component
public class RegistrationValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm userForm = (UserRegistrationForm) target;
        if(!userForm.getPassword().equals(userForm.getConfirmPassword())){
            errors.reject("confirmPassword", "Password doesn't match");
        }
    }
}
