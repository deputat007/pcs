package restservice.controllers;

import com.zeroisbiggerthanone.pcs.entities.Role;
import com.zeroisbiggerthanone.pcs.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import restservice.services.RoleService;
import restservice.services.UserService;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<String> performRegistrationUser(@RequestBody User user) {
        if (user == null || StringUtils.isBlank(user.getUserBase().getLogin()) ||
                StringUtils.isBlank(user.getPassword().getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User alreadyRegisteredUser = userService.getByLogin(user.getUserBase().getLogin());
        if (alreadyRegisteredUser != null) {
            return new ResponseEntity<>("User already registered", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.getUserBase().setRole(roleService.getByRoleName(Role.USER_ROLE_NAME));

        return new ResponseEntity<>(userService.insert(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/", method = RequestMethod.POST)
    public ResponseEntity<String> performRegistrationAdmin(@RequestBody User user) {
        if (user == null || StringUtils.isBlank(user.getUserBase().getLogin()) ||
                StringUtils.isBlank(user.getPassword().getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User alreadyRegisteredUser = userService.getByLogin(user.getUserBase().getLogin());
        if (alreadyRegisteredUser != null) {
            return new ResponseEntity<>("User already registered", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.getUserBase().setRole(roleService.getByRoleName(Role.ADMIN_ROLE_NAME));

        return new ResponseEntity<>(userService.insert(user), HttpStatus.CREATED);
    }
}
