package com.junglee.networkservice;

public interface ClientConnection {
	public void sendMessage(String msg) ;
	public void receiveMessage() ;
    public void handleConnectionClose(); 

}
