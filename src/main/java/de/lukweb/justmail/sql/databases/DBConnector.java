package de.lukweb.justmail.sql.databases;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBConnector {

	boolean isConnected();

	void setupTables();

	int readVersion() throws SQLException;

	void queryUpdate(String query, Object... arguments);

	ResultSet queryUpdateWithKeys(String query, Object... arguments);

	ResultSet querySelect(String query, Object... arguments);

	void close();
}
