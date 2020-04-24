package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.exception.UtilRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashUtil {

    private static final Logger LOG = LogManager.getLogger(HashUtil.class);

    private static final String ALGORITHM = "SHA-512";
    private static final String ARRAY_IS_NULL = "Source array to hash is null";
    private static final String SALT_IS_NULL = "Salt to make hash is null";

    private HashUtil() {

    }

    /**
     * Return SHA-512 hash of {@code array} with {@code salt} as salt
     * @param array {@code char[]} array to create hash from
     * @param salt {@link String} data to use as salt while create hash
     * @return hash {@code String}
     */
    public static String getHash(char[] array, String salt) {
        if (array == null) {
            throw new UtilRuntimeException(ARRAY_IS_NULL);
        }

        if (salt == null) {
            throw new UtilRuntimeException(SALT_IS_NULL);
        }

        String result;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] sourceBytes = getBytes(array);
            byte[] bytes = md.digest(sourceBytes);
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
            Arrays.fill(sourceBytes, (byte) 0);
            Arrays.fill(bytes, (byte) 0);
        } catch (NoSuchAlgorithmException e) {
            LOG.warn(e);
            throw new UtilRuntimeException(e);
        }
        return result;
    }

    private static byte[] getBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
