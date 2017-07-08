package restservice.security;

import com.zeroisbiggerthanone.pcs.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import restservice.security.authentication.AuthenticatedUserProxy;
import restservice.security.authentication.CustomAuthenticationToken;
import restservice.services.TokenService;
import restservice.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * The CustomRESTAuthenticationManager is a custom implementation of {@link AuthenticationManager} interface.
 * Performs authentication based on input {@link CustomAuthenticationToken}.
 *
 */
@Component
public class CustomRESTAuthenticationManager implements AuthenticationManager {

    private final TokenService tokenService;
    private final UserService userService;

    public CustomRESTAuthenticationManager(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    /**
     * Performs custom authentication process based on input CustomAuthenticationToken.
     * Also sets up the user role to 'regular' if premium account duration has expired
     *
     * @param authentication {@link CustomAuthenticationToken} instance
     * @return an instance of {@link AuthenticatedUserProxy} with user details
     * @throws AuthenticationException if authentication was unsuccessful
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) authentication;

        if (authenticationToken == null) {
            throw new BadCredentialsException("Token required to perform authentication");
        }

        tokenService.validate(authenticationToken);
        if(!authenticationToken.isValid()){
            throw new BadCredentialsException("Token is not valid");
        }

        User user = tokenService.getUserByToken(authenticationToken);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getUserBase().getRole());

        return new AuthenticatedUserProxy(user, authenticationToken, true, authorities);
    }
}
