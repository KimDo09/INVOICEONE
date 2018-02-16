package com.viettel.vpmt.template.common.encrypt.rsa;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author ToanTM
 * 
 */
public class RSACryptoDer {

	private static RSACryptoDer instance;

	public static RSACryptoDer getInstance() {
		if (instance == null) {
			instance = new RSACryptoDer();
		}
		return instance;
	}

	public static RSAPublicKey loadPublicKey_X509_RSA(InputStream input)
			throws GeneralSecurityException, IOException {

		InputStream fis = input;
		int size = input.available();
		byte[] keyBytes = new byte[size];
		fis.read(keyBytes);
		fis.close();
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) factory
				.generatePublic(publicKeySpec);
		return pubKey;
	}

	/**
	 * @author ToanTM Feb 28, 2013 1:09:58 PM
	 * @description
	 */
	public static RSAPrivateKey loadPrivateKey_X509_RSA(InputStream input)
			throws GeneralSecurityException, IOException {

		InputStream fis = input;
		int size = input.available();
		byte[] keyBytes = new byte[size];
		fis.read(keyBytes);
		fis.close();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(keyBytes);
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory
				.generatePrivate(privSpec);
		return privKey;
	}

	/**
	 * @author ToanTM Feb 28, 2013 1:10:51 PM
	 * @description
	 */
	public static String encrypt(byte[] dataToEncrypt, RSAPublicKey key)
			throws Exception {
		String transformation = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(1, key);
		int keySize = key.getModulus().bitLength() / 8;
		int maxLength = keySize - 42;
		int dataLength = dataToEncrypt.length;
		int iterations = dataLength / maxLength;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= iterations; i++) {
			byte[] tempBytes = new byte[dataLength - maxLength * i > maxLength ? maxLength
					: dataLength - maxLength * i];
			System.arraycopy(dataToEncrypt, maxLength * i, tempBytes, 0,
					tempBytes.length);
			byte[] encryptedBytes = cipher.doFinal(tempBytes);
			sb.append(encodeBase64(encryptedBytes));
		}
		String sEncrypted = sb.toString();
		sEncrypted = sEncrypted.replace("\r", "");
		sEncrypted = sEncrypted.replace("\n", "");
		return sEncrypted;
	}

	/**
	 * @author ToanTM Feb 28, 2013 1:10:54 PM
	 * @description
	 */
	public static String decrypt(String dataEncrypted, RSAPrivateKey key)
			throws Exception {
		String transformation = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
		Cipher cipher = Cipher.getInstance(transformation);

		cipher.init(2, key);

		int dwKeySize = key.getModulus().bitLength();
		int base64BlockSize = dwKeySize / 8 % 3 != 0 ? dwKeySize / 8 / 3 * 4
				+ 4 : dwKeySize / 8 / 3 * 4;
		int iterations = dataEncrypted.length() / base64BlockSize;
		ByteBuffer bb = ByteBuffer.allocate(1000);
		for (int i = 0; i < iterations; i++) {
			String sTemp = dataEncrypted.substring(base64BlockSize * i,
					base64BlockSize * i + base64BlockSize);
			byte[] bTemp = decodeBase64(sTemp);

			byte[] encryptedBytes = cipher.doFinal(bTemp);
			bb.put(encryptedBytes);
		}
		byte[] bDecrypted = bb.array();
		return new String(bDecrypted).trim();
	}

	/**
	 * @author Duchha Feb 28, 2013 1:10:58 PM
	 * @description
	 */
	public static String encodeBase64(byte[] dataToEncode) {
		String strEncoded = "";
		try {
			strEncoded = new String(Base64.encodeBase64(dataToEncode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strEncoded;
	}

	/**
	 * @author Duchha Feb 28, 2013 1:11:01 PM
	 * @description
	 */
	public static byte[] decodeBase64(String dataToDecode) {
		byte[] bDecoded = (byte[]) null;
		bDecoded = Base64.decodeBase64(dataToDecode.getBytes());
		return bDecoded;
	}
}
