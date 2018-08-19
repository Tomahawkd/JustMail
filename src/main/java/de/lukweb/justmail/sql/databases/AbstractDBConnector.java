package de.lukweb.justmail.sql.databases;

import de.lukweb.justmail.config.DBConfig;

import java.sql.*;

abstract class AbstractDBConnector implements DBConnector {

	protected Connection connection;

	AbstractDBConnector(DBConfig config) {
		connect(config.getDriver(), config.getUrl(), config.getUsername(), config.getPassword());
	}

	protected abstract void connect(String driver, String url, String username, String password);

	public boolean isConnected() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public int readVersion() throws SQLException {
		ResultSet rs = querySelect("SELECT * FROM version");
		if (rs.next()) {
			return rs.getInt("db_version");
		}
		return -1;
	}

	public void queryUpdate(String query, Object... arguments) {
		queryUpdateWithKeys(query, arguments);
	}

	public ResultSet queryUpdateWithKeys(String query, Object... arguments) {
		try {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < arguments.length; i++) statement.setObject(i + 1, arguments[i]);
			statement.executeUpdate();
			return statement.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet querySelect(String query, Object... arguments) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for (int i = 0; i < arguments.length; i++) statement.setObject(i + 1, arguments[i]);
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
