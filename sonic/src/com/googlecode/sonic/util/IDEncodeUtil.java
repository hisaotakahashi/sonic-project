/*
 * Copyright 2012 the Sonic Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.sonic.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 
 * @author hisao takahashi
 * @since 1.0
 */
// TODO 未実装。
public class IDEncodeUtil {

	private static final String CAHRSET = "UTF-8";

	// private static final String CAHRSET = "Shift_JIS";
	// private static final String CAHRSET = "iso-8859-1";

	public static void main(String[] args) throws InvalidKeyException,
			IllegalBlockSizeException, NoSuchAlgorithmException,
			UnsupportedEncodingException, BadPaddingException,
			NoSuchPaddingException {
		IDEncodeUtil idEncodeUtil = new IDEncodeUtil();

		String key = "AsdfsgW21pcMzsSD";
		String text = "page_1";

		byte[] encrypted = idEncodeUtil.encrypt(key, text);
		String xx = new String(encrypted, CAHRSET);
		System.out.println(encrypted);
		System.out.println(xx);
		// System.out
		// .println(new String(idEncodeUtil.encrypt(key, text), CAHRSET));
		// System.out.println(idEncodeUtil.encrypt(key, text).toString());
		// System.out.println(idEncodeUtil.decrypt(key, encrypted));

		System.out.println(byteToHexString(encrypted));

		String test = "test";
		byte[] sss = test.getBytes(CAHRSET);
		System.out.println(sss);
		String xx2 = new String(sss, CAHRSET);
		System.out.println(xx2);

		doDES();
	}

	/**
	 * @see http://www.techscore.com/tech/Java/JavaSE/JCE/3/
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void doDES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] cleartext = "Techscore-J2SE-JCE".getBytes();

		Cipher cipher = Cipher.getInstance("DES");

		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		SecretKey key = keyGen.generateKey();

		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] etext = cipher.doFinal(cleartext);

		System.out.println("暗号化した結果 : " + new String(etext));

	}

	/**
	 * @see http://www.itmedia.co.jp/enterprise/articles/0407/01/news017.html
	 * @param key
	 * @param text
	 * @return
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.NoSuchPaddingException
	 */
	public static byte[] encrypt(String key, String text)
			throws javax.crypto.IllegalBlockSizeException,
			java.security.InvalidKeyException,
			java.security.NoSuchAlgorithmException,
			java.io.UnsupportedEncodingException,
			javax.crypto.BadPaddingException,
			javax.crypto.NoSuchPaddingException {

		javax.crypto.spec.SecretKeySpec sksSpec = new javax.crypto.spec.SecretKeySpec(
				key.getBytes(), "Blowfish");
		javax.crypto.Cipher cipher = javax.crypto.Cipher
				.getInstance("Blowfish");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);
		byte[] encrypted = cipher.doFinal(text.getBytes(CAHRSET));
		return encrypted;
	}

	/**
	 * @see http://www.itmedia.co.jp/enterprise/articles/0407/01/news017.html
	 * @param key
	 * @param encrypted
	 * @return
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.NoSuchPaddingException
	 */
	public static String decrypt(String key, byte[] encrypted)
			throws javax.crypto.IllegalBlockSizeException,
			java.security.InvalidKeyException,
			java.security.NoSuchAlgorithmException,
			java.io.UnsupportedEncodingException,
			javax.crypto.BadPaddingException,
			javax.crypto.NoSuchPaddingException {
		javax.crypto.spec.SecretKeySpec sksSpec = new javax.crypto.spec.SecretKeySpec(
				key.getBytes(), "Blowfish");
		javax.crypto.Cipher cipher = javax.crypto.Cipher
				.getInstance("Blowfish");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sksSpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted);
	}

	private static String byteToHexString(final byte[] bytes) {
		final StringBuffer buffer = new StringBuffer();
		for (int byteIndex = 0; byteIndex < bytes.length; byteIndex++) {
			if (byteIndex != 0) {
				// 2バイト目以降には半角空白を区切り文字として付与。
				buffer.append(' ');
			}

			buffer.append(byteToHexString(bytes[byteIndex]));
		}
		return buffer.toString();
	}

	public static String byteToHexString(final byte argByte) {
		int wrkValue = argByte;
		if (wrkValue < 0) {
			// 負の値の場合には正の値に変換します。
			wrkValue += 0x100;
		}

		String result = Integer.toHexString(wrkValue);
		if (result.length() < 2) {
			// 1文字の場合には 0 の文字を加えて 2文字になるようにします。
			result = "0" + result;
		}
		return result;
	}
}
