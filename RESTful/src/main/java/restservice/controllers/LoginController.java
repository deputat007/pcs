package restservice.controllers;

import com.zeroisbiggerthanone.pcs.entities.Code;
import com.zeroisbiggerthanone.pcs.entities.Role;
import com.zeroisbiggerthanone.pcs.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import restservice.services.*;
import restservice.services.core.RandomGenerator;
import restservice.utils.API;
import restservice.utils.Phones;
import restservice.utils.RequestBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private TokenService tokenService;
    private UserService userService;
    private UserBaseService userBaseService;
    private RoleService roleService;
    private RandomNumberService numberService;

    @Autowired
    public LoginController(TokenService tokenService, UserService userService, UserBaseService userBaseService, RoleService roleService,
                           RandomNumberService numberService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.userBaseService = userBaseService;
        this.roleService = roleService;
        this.numberService = numberService;
    }

    /**
     * Performs server-side authentication process. If user credentials are correct, generates token string and returns
     * it as a response body
     *
     * @param login    user login
     * @param password user password
     * @return token string
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getAdminToken(@RequestParam final String login, @RequestParam final String password) {
        try {
            User user = userService.getByLogin(login);
            Role role = roleService.getByRoleName(Role.ADMIN_ROLE_NAME);

            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }
            if (!user.getUserBase().getRole().getId().equals(role.getId())) {
                throw new BadCredentialsException("Only admin can login");
            }
            if (!user.getPassword().getPassword().equals(userService.encode(password))) {
                throw new BadCredentialsException("Wrong password");
            }
            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getUserToken(@RequestParam final String login, @RequestParam final String password) {
        try {
            User user = userService.getByLogin(login);
            Role role = roleService.getByRoleName(Role.USER_ROLE_NAME);

            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }
            if (!user.getUserBase().getRole().getId().equals(role.getId())) {
                throw new BadCredentialsException("Only user can login");
            }
            if (!user.getPassword().getPassword().equals(userService.encode(password))) {
                throw new BadCredentialsException("Wrong password");
            }
            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/user_by_digit", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getUserByDigitToken(@RequestParam final String login, @RequestParam final int number,
                                                 @RequestParam final int sum) {
        try {
            User user = userService.getByLogin(login);
            int generatedNumber = numberService.getNumber(number);
            int difference = sum - generatedNumber;

            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }
            if (generatedNumber == 0) {
                throw new BadCredentialsException("First generate number");
            }
            if (!user.getUserBase().getSecretDigit().equals(userService.encode(String.valueOf(difference)))) {
                throw new BadCredentialsException("Wrong number");
            }
            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/generate_number", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> generateNumber() {
        return new ResponseEntity<>(numberService.generateNumber(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user_by_sms", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getUserBySmsToken(@RequestParam final String login, @RequestParam final String code) {
        try {
            User user = userService.getByLogin(login);

            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }

            String smsCode = userBaseService.getSmsCode(user.getId());

//            if (smsCode == null || smsCode.trim().length() == 0) {
//                throw new BadCredentialsException("First send sms code");
//            }
//            if (!smsCode.equals(userService.encode(code))) {
//                throw new BadCredentialsException("Wrong code");
//            }

            userBaseService.deleteSmsCode(user.getId());

            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/send_sms_code", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> sendSmsCode(@RequestParam final String login) {
        try {
            String URL = "http://api.myatompark.com/members/sms/xml.php";
            String loginApi = "androndeputat@gmail.com";
            String password = "adeputat1997";

            User user = userService.getByLogin(login);

            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }

            Code sms = new Code(String.valueOf(RandomGenerator.generate(10000)));

            RequestBuilder Request = new RequestBuilder(URL);
            API ApiSms = new API(Request, loginApi, password);

            //GET STATUS *************************************************************
            System.out.print(ApiSms.getStatus("1299"));
          /*
           * response: <?xml version="1.0" encoding="UTF-8"?>
                <deliveryreport><message id="1299" sentdate="0000-00-00 00:00:00" donedate="0000-00-00 00:00:00" status="0" /></deliveryreport>
          */

            //GET PRICE *************************************************************
            Map<String, String> phones = new HashMap<>();
            phones.put("id1", user.getUserBase().getPhoneNumber());
            System.out.print(ApiSms.getPrice(String.valueOf(sms), phones));
          /*
           * response: <?xml version="1.0" encoding="UTF-8"?><RESPONSE><status>0</status><credits>0.682</credits></RESPONSE>
          */

            //GET BALANCE *************************************************************
            System.out.print(ApiSms.getBalance());
          /*
           * response: <?xml version="1.0" encoding="UTF-8"?><RESPONSE><status>0</status><credits>780.64</credits></RESPONSE>
          */

            //SEND PHONE *************************************************************
            ArrayList<Phones> phoneSend = new ArrayList<>();
            phoneSend.add(new Phones("id1", "", user.getUserBase().getPhoneNumber()));

            System.out.print(ApiSms.sendSms("test", sms.getCode(), phoneSend));
          /*
           * response: <?xml version="1.0" encoding="UTF-8"?><RESPONSE><status>2</status><credits>0.682</credits></RESPONSE>
          */
            userBaseService.addSmsCode(user.getId(), sms);

            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }
}