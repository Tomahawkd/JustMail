package de.lukweb.justmail.sql;

import de.lukweb.justmail.console.JustLogger;
import de.lukweb.justmail.sql.databases.DBConnector;
import de.lukweb.justmail.sql.databases.DBConnectorFactory;

import java.sql.SQLException;

public class DB {

	private static DBConnector sql;
	private static final int latestDbVersion = 1;
	private static int dbVersion;

	static {
		sql = DBConnectorFactory.getFactory().initConnector();
		sql.setupTables();
		readVersion();
	}

	private static void readVersion() {

		try {
			dbVersion = sql.readVersion();

			if (dbVersion < 0) {
				sql.queryUpdate("INSERT INTO version (db_version) VALUES (?)", latestDbVersion);
				dbVersion = latestDbVersion;
			} else if (dbVersion < latestDbVersion) {
				// todo migrations
			}

		} catch (SQLException e) {
			JustLogger.logger().warning("Cannot read database version: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public static DBConnector getSql() {
		return sql;
	}

}
