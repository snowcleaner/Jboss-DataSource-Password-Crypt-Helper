package com.manman.xiaoa.util;

import java.lang.reflect.Method;
import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.picketbox.datasource.security.SecureIdentityLoginModule;

/**
 * ��������: ���ܽ��ܸ�������
 * 
 * <pre>
 *  �漰������������: jboss-logging-3.1.2.GA.jar,picketbox-4.0.15.Final.jar
 * </pre>
 * 
 * @author XiaoA
 * @version 2018��5��4��
 * @see com.manman.xiaoa.util
 * @since JDK 1.7
 */
public class CryptHelper{

    /**
     * ��������: ����
     * @param decodedText
     *            �����ַ���
     * @return �����ַ���
     * @throws Exception
     *             �쳣����
     */
    public static String encode(String decodedText) throws Exception {
        SecureIdentityLoginModule loginModule= new SecureIdentityLoginModule();
        Class<?> clazz= loginModule.getClass();
        Method method= clazz.getDeclaredMethod("encode", new Class[]{String.class});
        method.setAccessible(true);
        String encodedText= (String)method.invoke(loginModule, new Object[]{decodedText});
        return encodedText;
    }

    /**
     * ��������: ����
     * @param encodedTxt
     *            �����ַ���
     * @return �����ַ���
     * @throws Exception
     *             �쳣����
     */
    public static String decode(String encodedTxt) throws Exception {
        String decodedText= null;
        String transformation= "Blowfish";
        SecretKey secretKey= new SecretKeySpec("jaas is the way".getBytes(), transformation);
        Cipher cipher= Cipher.getInstance(transformation);

        BigInteger bigInteger= new BigInteger(encodedTxt, 16);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encodedbytes= cipher.doFinal(bigInteger.toByteArray());
        decodedText= new String(encodedbytes);
        return decodedText;
    }

}
