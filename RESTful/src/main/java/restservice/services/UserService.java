package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restservice.repositories.UserDao;
import restservice.services.core.Encryptor;
import restservice.services.core.IdGenerator;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordService passwordService;
    private UserBaseService userBaseService;

    private HexBinaryAdapter hexBinaryAdapter;

    @Value("${restservice.security.messageDigestAlgorithm}")
    private String messageDigestAlgorithm;

    @Autowired
    public UserService(UserDao userDao, PasswordService passwordService, UserBaseService userBaseService, HexBinaryAdapter hexBinaryAdapter) {
        this.userDao = userDao;
        this.passwordService = passwordService;
        this.userBaseService = userBaseService;
        this.hexBinaryAdapter = hexBinaryAdapter;
    }

    public User getById(String id) {
        return userDao.getById(id);
    }

    public String insert(User user) {


        try {
            String passwordId = passwordService.insert(user.getPassword());
            String userBaseId = userBaseService.insert(user.getUserBase());

            final String id = IdGenerator.generateId();

            user.setId(IdGenerator.generateId());
            user.getPassword().setId(passwordId);
            user.getUserBase().setId(userBaseId);


            userDao.insert(user);

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public String encode(String text) {
        return Encryptor.encrypt(text, hexBinaryAdapter, messageDigestAlgorithm);
    }
}
