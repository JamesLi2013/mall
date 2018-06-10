package com.james.mall.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    private static String algorithm = "AES";
    private static String INIT_KEY = "HRuP7cIOeMIuupcN7kDayQ==";

	public static void main(String[] args) {
		String data="hello android 123456数据的是";
		System.out.println("要加密的数据:"+data);
		try {
//			byte[] keyString=getInitKey();//生成随机的初始key
			byte[] keyString=Base64.decode(INIT_KEY.getBytes("utf-8"));
//			byte[] another = Base64.encode(keyString);
//			System.out.println(new String(another));
			String result=encryptData(keyString, data.getBytes());
			decryptResult(keyString, Base64.decode(result.getBytes("utf-8")));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 解密数据
	 *@param keyString 密钥
	 *@param data 要加密的数据
	 */
	public static String decryptResult(byte[] keyString, byte[] data) {
		//SecretKeySpec 为SecretKey的具体实现类
		SecretKey key = new SecretKeySpec(keyString, algorithm);
		try {
			//Cipher 加密和解密中提供访问密码的实现,应该是进行加解密.
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptBytes = cipher.doFinal(data);
//			System.out.println("解密结果:" + new String(decryptBytes));
			return new String(decryptBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptDataDefault(String encryptDate){
		try {
			byte[] keyString=Base64.decode(INIT_KEY.getBytes("utf-8"));
			return decryptResult(keyString, Base64.decode(encryptDate.getBytes("utf-8")));
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String encryptDataDefault(String text){
		try {
			byte[] keyString=Base64.decode(INIT_KEY.getBytes("utf-8"));
			return encryptData(keyString,text.getBytes());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 加密数据
	 *@param keyString 密钥
	 *@param data 要加密的数据
	 */
	public static String encryptData(byte[] keyString, byte[] data) {
		SecretKey key = new SecretKeySpec(keyString, algorithm);
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptBytes = cipher.doFinal(data);
			String result=new String(Base64.encode(encryptBytes));
//			System.out.println("加密结果:"
//					+ result);
			return  result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到一个随机的密钥 
	 */
	public static byte[] getInitKey() {
		// 得到一个安全的伪随机数.默认的构造器十分安全,强烈推荐使用默认的.
		//因为构造器传入byte[]不安全,setSeed()也不安全.
		SecureRandom secureRandom = new SecureRandom();
		try {
			//KeyGenerator 提供对称的密钥
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(secureRandom);
			//SecretKey 安全并对称的密钥
			SecretKey key = keyGenerator.generateKey();
//			String keyStr = Base64.encodeToString(key.getEncoded(),
//					Base64.DEFAULT);
//			System.out.println("密钥:" + keyStr);
			return key.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}