package de.lukweb.justmail.sql.databases;

import de.lukweb.justmail.config.DBConfig;

public class DBConnectorFactory {

	private static DBConnectorFactory factory;
	private static DBConfig config;

	public static DBConnectorFactory getFactory() {
		if (factory == null) factory = new DBConnectorFactory();
		return factory;
	}

	public static void setConfig(DBConfig config) {
		DBConnectorFactory.config = config;
	}

	public DBConnector initConnector() {
		switch (config.getName().toLowerCase()) {
			case "mysql": return new Mysql(config);
			case "sqlite": return new SqlLite(config);
			default: throw new UnsupportedOperationException("Database " +config.getName() + " is not Implement yet");
		}
	}
}
