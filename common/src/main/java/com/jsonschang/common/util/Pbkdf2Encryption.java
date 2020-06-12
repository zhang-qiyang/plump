package com.jsonschang.common.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * @author zhangqiyang
 * @date 2020/4/27 - 15:40
 * @description
 */
public class Pbkdf2Encryption {
    /**密码加密操作
     * @param plain 明文密码
     * @param salt 随机盐
     * @return
     */
    public static String sha1(String plain,String salt){
        byte[] bytes = encodeHex(salt).getBytes();
        char[] chars = plain.toCharArray();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, 1024,16*8);
        try {
            SecretKeyFactory sha1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] encoded = sha1.generateSecret(spec).getEncoded();
            return toHex(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**对随机盐进行加密
     * @param salt  随机盐
     * @return
     */
    private static String encodeHex(String salt){
        return Hex.encodeHexString(salt.getBytes());
    }

    /**转16进制
     * @param encode
     * @return
     */
    private static String toHex(byte[] encode){
        BigInteger bigInteger = new BigInteger(1, encode);
        String s = bigInteger.toString(16);
        int i=(encode.length*2)-s.length();
        if(i>0) {
            return String.format("%0"+i+"d",0)+s;
        }else {
            return s;
        }
    }

    /**随机盐
     * @return
     */
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

//    public static void main(String[] args) {
//        //执行加密操作
//        String uuid = getUuid();
//        System.out.println("盐："+uuid.length());
//        System.out.println(uuid);
//        String s = sha1("111111", uuid);
//        System.out.println(s.length());
//        System.out.println(s);
//    }
}
