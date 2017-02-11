package com.junglee.networkservice;

public interface ConnectionManager {
	
	    public void start();
	    public void handleConnection(String data) ;
	    public void handleDisconnected() ;
		
		public void registerHandler(ConnectionInterface handler);
		
}
