package com.junglee.networkservice;



public abstract class ConnectionInterface {
	protected ConnectionManager manager;
	public abstract void handleConnection(ClientConnection coconnectionntext);
	public abstract void handleDisconnected();
}

