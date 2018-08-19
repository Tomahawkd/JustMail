package de.lukweb.justmail.sql.objects;

import de.lukweb.justmail.crypto.CryptoUtils;
import de.lukweb.justmail.sql.interfaces.Unquie;

import java.util.Base64;

/**
 * Everything stored in this class is hashed with SHA512
 */
public class Password implements Unquie {

	private User user;
	private byte[] password;
	private byte[] base64;

	public Password(byte[] password, byte[] base64) {
		this.password = password;
		this.base64 = base64;
	}

	void setUser(User user) {
		this.user = user;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		String passwordString = new String(password);
		this.password = CryptoUtils.generateSHA512Password(password);
		password = passwordString.toCharArray();
		char[] email = ("\0" + user.getFullEmail() + "\0").toCharArray();
		char[] base64 = new char[email.length + password.length];
		System.arraycopy(email, 0, base64, 0, email.length);
		System.arraycopy(password, 0, base64, email.length, password.length);
		this.base64 = Base64.getEncoder().encode(CryptoUtils.generateSHA512Password(base64));

	}

	public byte[] getBase64() {
		return this.base64;
	}

	@Override
	public int getId() {
		return user.getId();
	}

	@Override
	public void setId(int id) {

	}
}
