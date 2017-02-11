package com.junglee.networkservice;

import java.util.ArrayList;
import java.util.List;

public class ClientEventDispatcher {

	private static ClientEventDispatcher instance = null;
	private static Object mutex = new Object();
	private ClientEventDispatcher() {}
	
	public static ClientEventDispatcher getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new ClientEventDispatcher();
				}
			}
		}
		return instance;
	}

	public void handleMessage(ClientConnection client, String msg){
		for (ClientInterface handler : handlers){ 
			handler.handleMessage(client, msg);
		}
	}
	
	
	public void handleConnectionClose(ClientConnection client){
		for (ClientInterface handler : handlers){ 
			handler.handleConnectionClose(client);
		}
	}
	
	public void registerHandler(ClientInterface handler) {
		handlers.add(handler);
	}
	
	private List<ClientInterface> handlers = new ArrayList<ClientInterface>();
}

