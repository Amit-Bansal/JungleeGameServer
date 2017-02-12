package com.junglee.networkservice;

public interface ConnectionManager {
	
	    public void start();
	    public void handleConnection(ClientConnection connection) ;
	    public void handleDisconnected() ;
		
		public void registerHandler(ConnectionInterface handler);
		
}
