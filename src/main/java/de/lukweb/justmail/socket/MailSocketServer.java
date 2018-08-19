package de.lukweb.justmail.socket;

import java.net.Socket;

public class MailSocketServer extends SocketServer {

	public MailSocketServer(int port) {
		super(port);
	}

	@Override
	protected Runnable onSocketAccepted(Socket socket) {
		return new MailSocketHandler(socket);
	}

	@Override
	protected void onServerStop() {
		MailSocketHandler.stopAll();
	}
}
