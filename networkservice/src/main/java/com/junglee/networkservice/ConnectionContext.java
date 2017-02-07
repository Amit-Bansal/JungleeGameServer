package com.junglee.networkservice;

import java.net.Socket;

public class ConnectionContext {
	
	public ConnectionContext(String data) {
		
	}
	
	public void sendMessage(String msg) {
		
	}
	
	public String readMessage() {
		String msg = null;
		try{
		      msg = socket.getInputStream().toString();
		}
		catch (Exception ex){
		}
		return msg;
	}
	
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	private Socket socket;
}
