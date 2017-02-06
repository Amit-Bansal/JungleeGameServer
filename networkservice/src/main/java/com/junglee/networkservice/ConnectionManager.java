package com.junglee.networkservice;

public interface ConnectionManager {
	
	    public void start();
	    public void handleConnection() ;
		
		public void registerHandler(ClientInterface handler);
		
}
