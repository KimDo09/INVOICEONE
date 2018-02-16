/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Lop AES
 * @author: DoanDM
 * @version: 1.2
 * @since: 1.1
 */
public class AESUtil {
	private SecretKeySpec skeySpec;
	String AESPadding = "AES";
	private Cipher cipher;
	byte[] keyByte;
	public String encrypt="";

	public AESUtil(){
	}
	
	public AESUtil(String key) throws Exception {
		keyByte = Base64.decodeBase64(key.getBytes("UTF-8"));
		skeySpec = new SecretKeySpec(keyByte, "AES");
		cipher = Cipher.getInstance(AESPadding);
	}

	/**
	 * 
	 * Tao doan key AES
	 * 
	 * @author: DoanDM
	 * @return
	 * @return: String
	 * @throws Exception
	 * @throws:
	 */
	public String getAESKey() throws Exception {
		String randomKey = "";
		byte[] seed;
		try {
		    // Create a secure random number generator
		    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

		    // Get 128 random bits
		    byte[] bytes = new byte[128];
		    sr.nextBytes(bytes);


		    // Create two secure number generators with the same seed
		    int seedByteCount = 16;
		    seed = sr.generateSeed(seedByteCount);

		    sr = SecureRandom.getInstance("SHA1PRNG");
		    sr.setSeed(seed);
		    SecureRandom sr2 = SecureRandom.getInstance("SHA1PRNG");
		    sr2.setSeed(seed);
		    byte[] oriByte = seed;
		    seed = Base64.encodeBase64(seed);
		    randomKey =new String(seed);
		    keyByte =  oriByte;//;
			skeySpec = new SecretKeySpec(keyByte, "AES");
			cipher = Cipher.getInstance(AESPadding);
		} catch (NoSuchAlgorithmException e) {
		}
		return randomKey;
	}

	/**
	 * 
	 * Ma hoa AES
	 * 
	 * @author: DoanDM
	 * @param plaintext
	 * @return
	 * @throws Exception
	 * @return: String
	 * @throws:
	 */
	public String encryptAES(byte[] plaintext) throws Exception {
		// returns byte array encrypted with key

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		return blockCipher(plaintext, Cipher.ENCRYPT_MODE);
	}
	/**
	 * 
	 * Ma hoa AES
	 * 
	 * @author: DoanDM
	 * @param plaintext
	 * @return
	 * @throws Exception
	 * @return: String
	 * @throws:
	 */
	public String encryptAES(String plaintext) throws Exception {
		// returns byte array encrypted with key
		byte[] plainByte = plaintext.getBytes("UTF-8");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		return blockCipher(plainByte, Cipher.ENCRYPT_MODE);
	}
	/**
	 * 
	 * Giai ma AES
	 * 
	 * @author: DoanDM
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 * @return: String
	 * @throws:
	 */
	public String decrypt(String ciphertext) throws Exception {
		// returns byte array decrypted with key
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] dec =Base64.decodeBase64(ciphertext.getBytes("UTF-8"));
        byte[] rawData = cipher.doFinal(dec);
        return new String(rawData,"UTF-8");
//		return blockCipher(ciphertext.getBytes("UTF-8"), Cipher.DECRYPT_MODE);
	}
	/**
	 * 
	*  Doc cac blog ma hoa/ giai ma
	*  @author: DoanDM
	*  @param bytes
	*  @param mode
	*  @return
	*  @throws IllegalBlockSizeException
	*  @throws BadPaddingException
	*  @return: String
	*  @throws:
	 */
	private String blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
//		byte[] toReturn = new byte[0];
		String strReturn="";
		// if we encrypt we use 100 byte long blocks. Decryption requires 172 byte long blocks (because of RSA encrypt 64)
		int length = (mode == Cipher.ENCRYPT_MODE)? cipher.getBlockSize() : 172;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0)){
				if(mode != Cipher.ENCRYPT_MODE){
					buffer = Base64.decodeBase64(buffer);
				}
				//execute the operation
				scrambled = cipher.doFinal(buffer);
				// add the result to our total result.
//				toReturn = append(toReturn, mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
				strReturn+= new String(mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the bytes array we shorten it.
				if (i + length > bytes.length) {
					 newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i%length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		if(mode != Cipher.ENCRYPT_MODE){
			buffer = Base64.decodeBase64(buffer);
		}
		scrambled = cipher.doFinal(buffer);

		// final step before we can return the modified data.
//		toReturn = append(toReturn,mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
		strReturn+= new String(mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
		strReturn = strReturn.replace("\r", "");
		strReturn = strReturn.replace("\n", "");
		return strReturn;
	}
	
}
