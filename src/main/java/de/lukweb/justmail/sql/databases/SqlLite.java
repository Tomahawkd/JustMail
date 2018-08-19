package de.lukweb.justmail.sql.databases;

import de.lukweb.justmail.config.DBConfig;

import java.sql.*;

public class SqlLite extends AbstractDBConnector {

	SqlLite(DBConfig config) {
		super(config);
	}

	protected void connect(String driver, String url, String username, String password) {
		connect(driver, url);
	}

	private void connect(String driver, String url) {
		try {
			if (connection != null && !connection.isClosed()) return;
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setupTables() {
		queryUpdate("CREATE TABLE IF NOT EXISTS \"mails\" ( " +
				"  id INTEGER PRIMARY KEY, " +
				"  owner INTEGER, " + // The mail adress of the user the mail belongs to
				"  \"to\" TEXT, " +
				"  \"from\" TEXT, " +
				"  sent INTEGER, " + // Boolean indicating whether the server received or sent this mail
				"  junkLevel INTEGER, " + // Junk Level from 0 to 10
				"  imapDirectory INTEGER, " +
				"  date INTEGER, " + // Timestamp
				"  content TEXT, " +
				"  read INTEGER, " +
				" CONSTRAINT mails_imap_directory_mailboxes_id FOREIGN KEY (imapDirectory) REFERENCES mailboxes (id) ON DELETE CASCADE ON UPDATE CASCADE, " +
				" CONSTRAINT mails_owner_users_id FOREIGN KEY (owner) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE" +
				");");
		queryUpdate("CREATE TABLE IF NOT EXISTS `domains` ( " +
				" `id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
				" `domain` TEXT, " +
				" `enabled` INTEGER DEFAULT 1 " +
				");");
		queryUpdate("CREATE TABLE IF NOT EXISTS \"users\" ( " +
				" `id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
				" `username` TEXT, " +
				" `domain` INTEGER, " + // Reference to id @ domains
				" `fullEmail` INTEGER, " +
				" `created` INTEGER, " +
				" CONSTRAINT users_domain_domains_id FOREIGN KEY (domain) REFERENCES domains (id) ON DELETE CASCADE ON UPDATE CASCADE " +
				")");
		queryUpdate("CREATE TABLE IF NOT EXISTS passwords ( " +
				" userid INT PRIMARY KEY,  " +
				" password BLOB,  " +
				" base64 BLOB, " +
				" CONSTRAINT passwords_userid_users_id FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE  " +
				");");
		queryUpdate("CREATE TABLE IF NOT EXISTS `mailboxes` ( " +
				" `id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
				" `name` TEXT, " +
				" `owner` INTEGER, " +
				" CONSTRAINT mailboxes_owner_users_id FOREIGN KEY (owner) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE" +
				")");
		queryUpdate("CREATE TABLE IF NOT EXISTS `version` (" +
				" `db_version` INTEGER PRIMARY KEY " +
				")");
	}

}
