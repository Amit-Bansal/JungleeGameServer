package com.junglee.networkservice;

public abstract class ClientInterface {
	protected ConnectionManager manager;
	public abstract void handleConnection(ConnectionContext context);
}
