package de.lukweb.justmail.smtp.commands;

import de.lukweb.justmail.smtp.SmtpSession;
import de.lukweb.justmail.smtp.commands.objects.SmtpCommand;

public class QuitC extends SmtpCommand {

	public QuitC() {
		super("QUIT");
	}

	@Override
	public void execute(String[] arguments, SmtpSession session) {
		session.close();
	}
}
