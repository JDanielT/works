package br.com.zone.meu.trabalho.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arkson
 * @version 1.0
 *
 */
public class HashUtil {

    public static final String ALGORITIMO = "SHA-256";

    public static String hash(String original) {
        String resultado = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance(ALGORITIMO);
            byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            resultado = hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(HashUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

}
