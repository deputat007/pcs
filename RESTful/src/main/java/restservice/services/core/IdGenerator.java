package restservice.services.core;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * Interface for generation unique id from md5 string
 */
public interface IdGenerator {

    /**
     * The method generates unique md5 string;
     *
     * @return unique string(md5) value;
     */
    //@NotNull
    static String generateId() {
        return DigestUtils.md2Hex(String.valueOf(System.currentTimeMillis() * new Random().nextDouble()));
    }
}
