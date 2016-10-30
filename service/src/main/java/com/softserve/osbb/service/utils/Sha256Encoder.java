package com.softserve.osbb.service.utils;

import com.softserve.osbb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cavayman on 16.09.2016.
 */
@Service
public final class Sha256Encoder {

    @Autowired
    UserService userService;

    private Map<Integer, String> secretKeysForForgotPassword = new ConcurrentHashMap<Integer, String>();

    public void setSecretKeyForUser(Integer id) {
        if (!secretKeysForForgotPassword.containsKey(id)) {
            secretKeysForForgotPassword.put(id, getSHA256Hash(userService.getOne(id).getEmail()));
            System.out.println("Added key  to HashMap");
        }

    }

    public String getSecretKeyForEmail(Integer id) {
        return this.secretKeysForForgotPassword.containsKey(id) ? secretKeysForForgotPassword.get(id) : null;
    }

    /**
     * If hashmap contains key method returns true and deleting it.
     *
     * @param email
     * @return boolean
     */
    public Boolean validateSecretKeyForEmail(Integer email, String key) {
        if (this.secretKeysForForgotPassword.containsKey(email)) {
            System.out.println("pass contain key");
            if (secretKeysForForgotPassword.get(email).equals(key)) {
                System.out.println("pass get equals");
                secretKeysForForgotPassword.remove(email);
                return true;
            } else return false;
        } else return false;
    }

    private String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}
