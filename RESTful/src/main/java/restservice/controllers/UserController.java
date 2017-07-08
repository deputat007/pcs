package restservice.controllers;

import com.zeroisbiggerthanone.pcs.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import restservice.security.authentication.AuthenticatedUserProxy;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/current")
    @ResponseBody
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) authentication;

            return userProxy.getUser();
        }

        return null;
    }

}
