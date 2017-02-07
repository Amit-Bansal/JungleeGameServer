package com.junglee.networkservice;

public abstract class ClientInterface {
	
	protected ClientConnection client;
	public abstract void handleMessage(String msg);
}
