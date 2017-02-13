package com.junglee.networkservice;


public interface ConnectionManager {
	
	    public void start();
	    public void handleConnection(ClientConnection connection) ;
	    public void handleDisconnected(ClientConnection connection);
	    public void handleMessage(ClientConnection connection, String message);
}
