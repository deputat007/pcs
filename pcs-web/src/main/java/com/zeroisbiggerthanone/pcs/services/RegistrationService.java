package com.zeroisbiggerthanone.pcs.services;

import com.zeroisbiggerthanone.pcs.entities.Role;
import com.zeroisbiggerthanone.pcs.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This service class contains methods that handle user registration process
 */
@Service
public class RegistrationService {

    @Value("${service.url.register.user}")
    private String registerUserUrl;

    @Value("${service.url.register.admin}")
    private String registerAdminUrl;

    @Value("${service.url.register.alreadyExists}")
    private String registerAlreadyExistsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public RegistrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Performs user registration on RESTful service.
     *
     * @param user filled in user instance
     * @return true if user has been successfully registered
     */
    public boolean performUserRegistration(User user) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerUserUrl, user, String.class);
        return responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

    /**
     * Performs admin registration on RESTful service.
     *
     * @param user filled in user instance
     * @return true if user has been successfully registered
     */
    public boolean performAdminRegistration(User user) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerAdminUrl, user, String.class);
        return responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

/*    *//**
     * Checks if the given username are already registered in the system
     *
     * @param username form received from model
     * @return true if such user has found in th DB
     *//*
    public boolean alreadyExist(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerAlreadyExistsUrl, username, String.class);
        return responseEntity.hasBody() && responseEntity.getBody().equals(username);
    }*/
}
