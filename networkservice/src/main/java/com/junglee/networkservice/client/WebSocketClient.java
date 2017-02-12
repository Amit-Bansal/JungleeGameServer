package com.junglee.networkservice.client;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;

public class WebSocketClient {

	public static void main(String[] args) {
	
		try {

			String dest = "ws://localhost:8080/gameServer";
			TableClientSocket socket = new TableClientSocket();
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(socket, new URI(dest));

			socket.getLatch().await();
			//socket.sendMessage("hello");
			Thread.sleep(10000);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
