package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restservice.repositories.PasswordDao;
import restservice.services.core.Encryptor;
import restservice.services.core.IdGenerator;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

@Service
public class PasswordService {

    private PasswordDao passwordDao;
    private HexBinaryAdapter hexBinaryAdapter;

    @Value("${restservice.security.messageDigestAlgorithm}")
    private String messageDigestAlgorithm;

    @Autowired
    public PasswordService(PasswordDao passwordDao, HexBinaryAdapter hexBinaryAdapter) {
        this.passwordDao = passwordDao;
        this.hexBinaryAdapter = hexBinaryAdapter;
    }

    public Password getById(String id) throws Exception {
        return passwordDao.getById(id);
    }

    public String insert(Password password) {
        final String id = IdGenerator.generateId();
        password.setPassword(Encryptor.encrypt(password.getPassword(), hexBinaryAdapter, messageDigestAlgorithm));

        password.setId(id);
        passwordDao.insert(password);

        return id;
    }
}
