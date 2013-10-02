package com.janpix.servidordocumentos

import java.security.MessageDigest

class FileUtils {

	/**
	 * Retorna el calculo Hash SHA1 de un array de bytes
	 * @param binaryData
	 * @return
	 */
	public static String calculateSHA1(byte[] binaryData){
		
		def messageDigest = MessageDigest.getInstance("SHA1")
		
		int MB = 1024*1024
		ByteArrayInputStream inputStream = new ByteArrayInputStream(binaryData)
		inputStream.eachByte(MB) { byte[] buf, int bytesRead ->
		  messageDigest.update(buf, 0, bytesRead);
		}	
	
		/*
		 * Why pad up to 40 characters? Because SHA-1 has an output
		 * size of 160 bits. Each hexadecimal character is 4-bits.
		 * 160 / 4 = 40
		 */
		def sha1Hex = new BigInteger(1, messageDigest.digest()).toString(16).padLeft( 40, '0' )
		
		return sha1Hex
	}
}
