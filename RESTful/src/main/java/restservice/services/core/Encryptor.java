package restservice.services.core;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * Interface for encrypting user data
 */
public interface Encryptor {

    //@NotNull
    static String encrypt(String text, HexBinaryAdapter hexBinaryAdapter, String messageDigestAlgorithm) {
        return hexBinaryAdapter.marshal(DigestUtils.getDigest(messageDigestAlgorithm).digest(getBytesUtf8(text)));
    }
}
