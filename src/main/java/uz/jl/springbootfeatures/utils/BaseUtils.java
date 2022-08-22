package uz.jl.springbootfeatures.utils;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author "Elmurodov Javohir"
 * @since 22/08/22/11:31 (Monday)
 * spring-boot-features/IntelliJ IDEA
 */

@Component
public class BaseUtils {
    public  final Charset UTF_8 = StandardCharsets.UTF_8;
    public  final String OUTPUT_FORMAT = "%-20s:%s";

    private byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest(input);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String encode(@NonNull String codeForEncoding) {
        byte[] digest = digest(codeForEncoding.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }
}