package de.lukweb.justmail.config;

public class DBConfig {

	private String name;
	private String driver;
	private String url;
	private String username;
	private String password;

	DBConfig(String name, String driver, String url, String username, String password) {
		this.name = name;
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
