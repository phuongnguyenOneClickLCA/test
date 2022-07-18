package com.bionova.optimi.security

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.commons.codec.binary.Hex

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.Key;

@CompileStatic
class AESCryption {

    private static final String ALGO = "AES";
    // UUID string has constant length: 36
    private static final int HALF_LENGTH_UUID = 18;
    // DO NOT CHANGE THIS KEY
    static final def keyValue = "1Rna6bmEKhUhHsdw".getBytes()


    /**
     * Encrypt the data + a random string
     * @param data
     * @return
     */
    static String encryptWithRandomString(String data) {
        if (!data) {
            return ''
        }
        return addRandomString(encrypt(data))
    }

    static String decryptWithRandomString(String encryptedTextWithRandom) {
        if (!encryptedTextWithRandom) {
            return ''
        }

        return decrypt(removeRandomString(encryptedTextWithRandom))
    }

    /**
     * Add a random string to data so it is a bit harder for user to realize our 'simple' encryption.
     * An UUID is generated, split in half and attach it to before and after the string
     * HALF UUID + data + HALF UUID
     * @param data
     * @return
     */
    private static String addRandomString(String data) {
        if (!data) {
            return ''
        }

        String random = UUID.randomUUID().toString()
        return random.substring(0, HALF_LENGTH_UUID) + data + random.substring(HALF_LENGTH_UUID)
    }

    /**
     * Remove the random string that was added by {@link #addRandomString}
     * @param textWithRandom
     * @return
     */
    private static String removeRandomString(String textWithRandom) {
        if (!textWithRandom) {
            return ''
        }

        int length = textWithRandom.length()
        return textWithRandom - textWithRandom.substring(0, HALF_LENGTH_UUID) - textWithRandom.substring(length - HALF_LENGTH_UUID, length)

    }

    /**
     * A simple encrypting method.
     * @param data
     * @return
     */
    private static String encrypt(String data) {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes("UTF-8"));

        return new String(Hex.encodeHex(encVal))
    }

    private static String decrypt(String encryptedData) {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = c.doFinal(Hex.decodeHex(encryptedData.toCharArray()));
        return new String(decValue);
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGO);
    }
}