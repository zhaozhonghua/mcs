package com.fjsmu.comm.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by fangcm on 2017/1/22.
 */
public class AesEncrypter {

    private static final String ALGORITHM = "AES";
    private static final String AES_KEY = "B3C58BAFE578C593";


    public static String encrypt(String plainTextData) throws Exception {
        SecretKey secretKey = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher theCipher = Cipher.getInstance(ALGORITHM);
        theCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encoderValue = theCipher.doFinal(plainTextData.getBytes());
        String encryptedData = parseByte2HexStr(encoderValue);
        return encryptedData;
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher theCipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), ALGORITHM);
        theCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoderValue = parseHexStr2Byte(encryptedData);
        byte[] decodeValue = theCipher.doFinal(decoderValue);
        String plainTextData = new String(decodeValue);
        return plainTextData;
    }

    /*
     * Because the encrypted byte array cannot be converted to String, so it
	 * needs to be change to HEX String, 2 bits -> 16 bits
	 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /*
	 * When a Hex string comes from server, it needs to be changed to 2 bits
	 * data, 16 bytes -> 2 bytes
	 */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String value = "05a068670f164c2489353fcf974bca8c|05a068670f164c2489353fcf974bca8c";
        String encodeValue = encrypt(value);
        System.out.println("en:" + encodeValue);
        String decodeValue = decrypt(encodeValue);
        System.out.println("de:" + decodeValue);
    }
}
