/**
 * Copyright 2012 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common.encrypt.rsa;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: TruongHN
 * @version: 1.0
 * @since: Jan 20, 2012
 */
public class RSATest {
	Activity activity;
	Cipher cipher;
	public RSATest(Activity activity) {
		this.activity = activity;
		try {
			this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException ex){
			
		}
		
	}
	public byte[] rsaEncrypt1(byte[] data) {
		byte[] cipherData = null;
		try {
			PublicKey pubKey =readPubKeyFromFile("public.key");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);

			cipherData = cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;
	}

	public byte[] rsaDecrypt(byte[] data) {
		byte[] cipherData = null;
		try {
			PrivateKey priKey = readPriKeyFromFile("private.key");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, priKey);

			cipherData = cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;
	}

	PublicKey readPubKeyFromFile(String keyFileName) throws IOException {
		PublicKey publicKey = null;
		try {
			InputStream in = activity.getAssets().open(keyFileName);
			PEMReader rd = new PEMReader(in);
		    byte[] encodedPublicKey = rd.getDerBytes();
		    in.close();
		    KeyFactory fact = KeyFactory.getInstance("RSA");
		    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
		    		encodedPublicKey);
		    publicKey = fact.generatePublic(publicKeySpec);
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		}
		return publicKey;
	}
	
	PrivateKey readPriKeyFromFile(String keyFileName) throws IOException {
		PrivateKey privateKey = null;
		try {
			InputStream in = activity.getAssets().open(keyFileName);
			PEMReader rd = new PEMReader(in);
		    byte[] encodedPrivateKey = rd.getDerBytes();
		    in.close();
		    KeyFactory fact = KeyFactory.getInstance("RSA");
//			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
//					encodedPrivateKey);
		    KeySpec privateKeySpec = new PKCS1EncodedKeySpec(
					encodedPrivateKey).getKeySpec();
					privateKey = fact.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} 
		return privateKey;
	}
	
	public String rsaEncrypt(String str) {
		
		
		String sEncrypted = "";
		try {
			byte[] data = str.getBytes("UTF8");
			PublicKey pubKey =readPubKeyFromFile("public.key");
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return blockCipher(data,Cipher.ENCRYPT_MODE);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sEncrypted;
	}
	
	public String rsaDecrypt(String encrypted) throws Exception{
		PrivateKey priKey = readPriKeyFromFile("private.key");
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		
		byte[] bts =(encrypted.getBytes("UTF8"));
		return blockCipher(bts,Cipher.DECRYPT_MODE);
	}
	private String blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
//		byte[] toReturn = new byte[0];
		String strReturn="";
		// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE)? 43 : 172;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0)){
				if(mode != Cipher.ENCRYPT_MODE){
					buffer = android.util.Base64.decode(buffer, android.util.Base64.NO_WRAP);
				}
				//execute the operation
				scrambled = cipher.doFinal(buffer);
				// add the result to our total result.
//				toReturn = append(toReturn, mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
				strReturn+= new String(mode == Cipher.ENCRYPT_MODE?android.util.Base64.encode(scrambled,android.util.Base64.NO_WRAP):scrambled);
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
			buffer = android.util.Base64.decode(buffer, android.util.Base64.NO_WRAP);
		}
		scrambled = cipher.doFinal(buffer);

		// final step before we can return the modified data.
//		toReturn = append(toReturn,mode == Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
		strReturn+= new String(mode == Cipher.ENCRYPT_MODE?android.util.Base64.encode(scrambled,android.util.Base64.DEFAULT):scrambled);
		strReturn = strReturn.replace("\r", "");
		strReturn = strReturn.replace("\n", "");
		return strReturn;
	}
	
}
