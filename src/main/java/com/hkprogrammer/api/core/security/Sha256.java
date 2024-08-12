package com.hkprogrammer.api.core.security;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.hkprogrammer.api.core.exception.GenericException;

public class Sha256 {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;
    private static final Logger log = Logger.getLogger(Sha256.class.getName());

    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(KEY_SIZE);
            return keyGen.generateKey();
        } catch (Exception e) {
            String message = "NÃO FOI POSSÍVEL GERAR A CHAVE DA SENHA. ";
            log.severe(message + e.getMessage());
            throw new GenericException(message);
        }
    }

    public static String encrypt(String data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            byte[] ivAndEncryptedData = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, ivAndEncryptedData, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, ivAndEncryptedData, iv.length, encryptedBytes.length);

            // Log para depuração
            log.info("IV: " + Base64.getEncoder().encodeToString(iv));
            log.info("Encrypted Data: " + Base64.getEncoder().encodeToString(encryptedBytes));

            return Base64.getEncoder().encodeToString(ivAndEncryptedData);
        } catch (Exception e) {
            String message = "NÃO FOI POSSÍVEL ENCRIPTAR A SENHA. ";
            log.severe(message + e.getMessage());
            throw new GenericException(message);
        }
    }

    public static String decrypt(String encryptedData, SecretKey key) {
        try {
            byte[] ivAndEncryptedData = Base64.getDecoder().decode(encryptedData);
            byte[] iv = new byte[16];
            byte[] encryptedBytes = new byte[ivAndEncryptedData.length - iv.length];
            System.arraycopy(ivAndEncryptedData, 0, iv, 0, iv.length);
            System.arraycopy(ivAndEncryptedData, iv.length, encryptedBytes, 0, encryptedBytes.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Log para depuração
            log.info("IV: " + Base64.getEncoder().encodeToString(iv));
            log.info("Encrypted Data: " + Base64.getEncoder().encodeToString(encryptedBytes));

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            String message = "NÃO FOI POSSIVEL DESENCRIPTAR A SENHA. ";
            log.severe(message + e.getMessage());
            throw new GenericException(message);
        }
    }
}
