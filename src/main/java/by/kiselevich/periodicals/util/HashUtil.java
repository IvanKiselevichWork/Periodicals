package by.kiselevich.periodicals.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashUtil {

    private static final String ALGORITHM = "SHA-512";

    public static String getHash(char[] array, String salt) {
        if (array == null) {
            return "";
        }

        String result = null;
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
        } catch (NoSuchAlgorithmException ignored) {
            // ignored, never happens
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
