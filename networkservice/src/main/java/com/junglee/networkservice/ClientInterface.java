package com.junglee.networkservice;

public abstract class ClientInterface {
	
	protected ClientConnection client;
	public abstract void handleMessage(ClientConnection client, String msg);
	public abstract void handleConnectionClose(ClientConnection client);
}
