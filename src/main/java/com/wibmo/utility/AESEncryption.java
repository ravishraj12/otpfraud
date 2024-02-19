package com.wibmo.utility;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESEncryption 
{	
	public static String encrypt_gcm(String data, String key)
	{
		String encryptedString = "";
		IvParameterSpec ivParameterSpec = null;
		GCMParameterSpec gcmParameterSpec = null;
		
		try 
		{
			// Generate IV.
			byte[] iv = new byte[12];
			new SecureRandom().nextBytes(iv);
			ivParameterSpec = new IvParameterSpec(iv);
			gcmParameterSpec = new GCMParameterSpec(128, iv);
			
			// Generate AAD.
			byte[] aad = new byte[16];
			new SecureRandom().nextBytes(aad);
						
			// Generate Key
			byte[] keyBytes = hexToBytes(key);
			SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
			
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey, gcmParameterSpec);
			
			if (aad != null) 
			{
		        //cipher.updateAAD(aad);
		    }
			
			byte[] cipherText = cipher.doFinal(data.getBytes());
						
			byte[] cipherWithIV = new byte[cipherText.length + iv.length];
			System.arraycopy(iv, 0, cipherWithIV, 0, iv.length);
			System.arraycopy(cipherText, 0, cipherWithIV, iv.length, cipherText.length);
			
			// Encode to base64
			encryptedString = Base64.getEncoder().encodeToString(cipherWithIV);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		
		return encryptedString;
	}
	
	public static String decrypt_gcm(String encryptedTextWithIV, String key)
	{
		String decryptedString = "";
		
		try 
		{
			byte[] keyBytes = hexToBytes(key);
			
			 // Decode the Base64 encoded encrypted data
	        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedTextWithIV);
	        
	        // Extract the IV from the encrypted data
	        byte[] ivBytes = new byte[12];
	        System.arraycopy(encryptedBytes, 0, ivBytes, 0, ivBytes.length);
	        	        
	        // Extract the AAD from the encrypted data
	        byte[] aadBytes = new byte[16];
	        System.arraycopy(encryptedBytes, encryptedBytes.length - aadBytes.length, aadBytes, 0, aadBytes.length);
	                
	        // Extract the actual encrypted value
	        //byte[] encryptedValueBytes = new byte[encryptedBytes.length - (ivBytes.length + aadBytes.length)];
	        byte[] encryptedValueBytes = new byte[encryptedBytes.length - ivBytes.length];
	        System.arraycopy(encryptedBytes, ivBytes.length, encryptedValueBytes, 0, encryptedValueBytes.length);
	        
	        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	        
	        // Initialize the cipher with the AES key and IV
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, ivBytes));
	        
	        if(null != aadBytes) 
	        {
	        	//cipher.updateAAD(aadBytes);
	        }
	        
	        // Decrypt the data
	        byte[] decryptedBytes = cipher.doFinal(encryptedValueBytes);

	        // Convert the decrypted bytes to a string
	        decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

	        //System.out.println(decryptedString);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		
		return decryptedString;
	}	
		
	private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
