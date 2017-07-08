package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restservice.repositories.UserDao;
import restservice.security.authentication.CustomAuthenticationToken;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

@Service
public class TokenService {

    @Value("${restservice.security.tokenEncryptionKey}")
    private String SECURITY_KEY;

    @Value("${restservice.security.tokenDelimiter}")
    private String TOKEN_DELIMITER;

    @Value("${restservice.security.messageDigestAlgorithm}")
    private String MESSAGE_DIGEST_ALGORITHM;

    @Value("${restservice.security.tokenValidityTimeInSeconds}")
    private int TOKEN_VALIDITY_TIME;

    private HexBinaryAdapter hexBinaryAdapter;
    private UserDao userDao;

    @Autowired
    public TokenService(HexBinaryAdapter hexBinaryAdapter, UserDao userDao) {
        this.hexBinaryAdapter = hexBinaryAdapter;
        this.userDao = userDao;
    }

    /**
     * Generates token string for given username
     *
     * @return string with token value
     */
    public String generateTokenFor(String userName) {
        long expirationDate = DateTime.now().plusSeconds(TOKEN_VALIDITY_TIME).getMillis();
        String tokenString = buildToken(userName, expirationDate);
        return new String(Base64.encodeBase64(tokenString.getBytes()));
    }

    /**
     * Generates token string based on given username and expiration date and signs it with confirmation key
     *
     * @param userName       given user's name
     * @param expirationDate token expiration date
     * @return token string
     * @see TokenService#buildTokenConfirmationKey
     */
    private String buildToken(String userName, long expirationDate) {
        String tokenConfirmationKey = buildTokenConfirmationKey(userName, expirationDate);

        return userName + TOKEN_DELIMITER + expirationDate + TOKEN_DELIMITER + tokenConfirmationKey;
    }

    /**
     * Generates encrypted confirmation key for token based on user's name and expiration date.
     *
     * @return built token confirmation key as UTF-8 string
     */
    private String buildTokenConfirmationKey(String userName, long expirationDate) {
        String token = userName + expirationDate + SECURITY_KEY;

        return hexBinaryAdapter.marshal(DigestUtils.getDigest(MESSAGE_DIGEST_ALGORITHM).digest(getBytesUtf8(token)));
    }

    /**
     * Perform decoding and validating of input token and if it's valid, fills its fields with parsed values.
     * If validation were successful, sets {@code isValid} value of token to true.
     *
     * @param token an {@link CustomAuthenticationToken} instance
     */
    public void validate(CustomAuthenticationToken token) {
        String[] tokenParts = decodeToken(token.getToken());
        if (tokenParts != null) {
            token.setUserName(tokenParts[0]);

            try {
                token.setExpirationDate(Long.parseLong(tokenParts[1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }

            if (new DateTime(token.getExpirationDate()).isBeforeNow()) {
                return;
            }

            token.setConfirmationKey(tokenParts[2]);
            String expectedKey = buildTokenConfirmationKey(token.getUserName(), token.getExpirationDate());
            if (!expectedKey.equals(token.getConfirmationKey())) {
                return;
            }

            //if there is no errors, set token as valid
            token.setValid(true);
        }
    }

    /**
     * Retrieves {@link User} instance from DB by given token
     *
     * @param token valid {@link CustomAuthenticationToken} instance
     * @return instance of {@link User} class retrieved from DB or null if such user not found
     */
    public User getUserByToken(CustomAuthenticationToken token) {
        return token.isValid() ? userDao.getByLogin(token.getUserName()) : null;
    }

    /**
     * Generates new token instead of the outdated one.
     *
     * @param authenticationToken token object to be renewed
     * @return String that contains renewed token
     */
    public String renewToken(CustomAuthenticationToken authenticationToken) {
        return authenticationToken.isValid() ? generateTokenFor(authenticationToken.getUserName()) : null;
    }

    /**
     * Decodes input token string and splits result into array contains token's parts
     *
     * @param tokenString an Base64 string which contains token
     * @return array with decoded token parts or null if decoding fails
     */
    private String[] decodeToken(String tokenString) {
        if (StringUtils.isBlank(tokenString) || !Base64.isBase64(tokenString)) {
            return null;
        }

        String token = new String(Base64.decodeBase64(tokenString));
        String[] tokenParts = token.split(TOKEN_DELIMITER);
        if (tokenParts.length != 3) {
            return null;
        }

        for (String tokenPart : tokenParts) {
            if (tokenPart.length() == 0)
                return null;
        }

        return tokenParts;
    }
}
