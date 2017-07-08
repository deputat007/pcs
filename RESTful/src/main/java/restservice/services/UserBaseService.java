package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.Code;
import com.zeroisbiggerthanone.pcs.entities.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restservice.repositories.UserBaseDao;
import restservice.services.core.Encryptor;
import restservice.services.core.IdGenerator;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

@Service
public class UserBaseService {

    private UserBaseDao userBaseDao;
    private HexBinaryAdapter hexBinaryAdapter;

    @Value("${restservice.security.messageDigestAlgorithm}")
    private String messageDigestAlgorithm;

    @Autowired
    public UserBaseService(UserBaseDao userDao, HexBinaryAdapter hexBinaryAdapter) {
        this.userBaseDao = userDao;
        this.hexBinaryAdapter = hexBinaryAdapter;
    }

    public UserBase getById(String id) {
        return userBaseDao.getById(id);
    }

    public String insert(UserBase userBase) {
        final String id = IdGenerator.generateId();
        userBase.setSecretDigit(Encryptor.encrypt(userBase.getSecretDigit(), hexBinaryAdapter, messageDigestAlgorithm));

        userBase.setId(id);
        userBaseDao.insert(userBase);

        return id;
    }

    public void addSmsCode(final String id, final Code smsCode) {
        smsCode.setCode(Encryptor.encrypt(smsCode.getCode(), hexBinaryAdapter, messageDigestAlgorithm));
        userBaseDao.addSmsCode(smsCode, id);
    }

    public void deleteSmsCode(final String id) {
        userBaseDao.deleteSmsCode(id);
    }

    public String getSmsCode(final String id) {
        return userBaseDao.getSmsCode(id);
    }
}
