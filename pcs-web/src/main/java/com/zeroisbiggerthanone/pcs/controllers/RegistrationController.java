package com.zeroisbiggerthanone.pcs.controllers;

import com.zeroisbiggerthanone.pcs.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.zeroisbiggerthanone.pcs.services.RegistrationService;
import com.zeroisbiggerthanone.pcs.validators.RegistrationValidator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The controller contains methods that handle user registration process
 */

@Controller
@RequestMapping("/register")
public class RegistrationController {

//    @Value("registration.alreadyExists")
//    private String alreadyExistMessage;

    private RegistrationValidator registrationValidator;
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationValidator registrationValidator, RegistrationService registrationService) {
        this.registrationValidator = registrationValidator;
        this.registrationService = registrationService;
    }

    /**
     * Creates empty {@link UserRegistrationForm} and puts it with into model.
     * Displays user registration page.
     */
    @GetMapping
    public String getRegisterPage(Model model) {
        List<String> roles = new ArrayList<>();
        roles.add("User");
        roles.add("Admin");

        model.addAttribute("registrationForm", new UserRegistrationForm());
        model.addAttribute("roles", roles);

        return "register";
    }

    /**
     * Handles user registration process. In case of errors (such as already existed account,
     * incorrect account type etc.) redirects to register page with displaying error message.
     *
     * @param registrationForm   an {@link ModelAttribute} filled in with user's data
     * @param result             validation result
     * @param redirectAttributes attributes with custom messages to be displayed on the page
     * @return view name
     */
    @PostMapping
    public String postRegisterPage(@Validated @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        registrationValidator.validate(registrationForm, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationErrors", result.getAllErrors());
            return "redirect:/register";
        }

        User user = new User();
        user.setPassword(new Password(registrationForm.getPassword()));
        user.setUserBase(new UserBase(registrationForm.getLogin(), null,
                registrationForm.getPhoneNumber(), registrationForm.getSecretDigit()));

try {
    switch (registrationForm.getRole()) {
        case "User": {
            if (registrationService.performUserRegistration(user)) {
                redirectAttributes.addFlashAttribute("successMessage", "Registration of user was done successful");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "General error");
            }
        }

        case "Admin": {
            if (registrationService.performAdminRegistration(user)) {
                redirectAttributes.addFlashAttribute("successMessage", "Registration of admin was done successful");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "General error");
            }
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}

        return "redirect:/register";
    }
}
