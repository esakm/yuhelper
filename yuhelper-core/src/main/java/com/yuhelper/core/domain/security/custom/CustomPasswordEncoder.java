package com.yuhelper.core.domain.security.custom;

import com.yuhelper.core.utils.StringConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class CustomPasswordEncoder implements PasswordEncoder {

    private String salt;

    public CustomPasswordEncoder(String salt) {
        this.salt = salt;
    }

    public CustomPasswordEncoder(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        this.salt = Arrays.toString(salt);

    }

    public String encode(CharSequence charSequence) {
        try {
            KeySpec spec = new PBEKeySpec(charSequence.toString().toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return StringConverter.bytesToHex(factory.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException exception) {
            System.out.println(exception.getMessage());
            return "-1";
        } catch (InvalidKeySpecException exception){
            System.out.println(exception.getMessage());
            return "-1";
        }
    }


    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRawPassword = encode(rawPassword);
        if (encodedRawPassword.equals("-1")) {
            return false;
        }
        return encodedRawPassword.equals(encodedPassword);
    }

    public String getSalt(){
        return salt;
    }
}
