package com.junglee.gameserver.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.gameserver.event.EventDispatcher;
import com.junglee.gameserver.event.EventType;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.player.PlayerManager;
import com.junglee.gameserver.session.ConnectionHandler;
import com.junglee.gameserver.table.TableManager;
import com.junglee.networkservice.ConnectionManager;

@Component
public class App {
	
	@Autowired
	EventDispatcher eventDispatcher;
	
	@Autowired
	GameManager gameManager;
	
	@Autowired
	PlayerManager playerManager;
	
	@Autowired
	TableManager tableManager;
	
	@Autowired
    private ConnectionHandler connectionHandler;
	
	@Autowired
	ConnectionManager connectionManager;
	
    public void startApp() {
    	   
    	eventDispatcher.register(EventType.ClientDisconnected, gameManager);
    	eventDispatcher.register(EventType.ClientDisconnected, playerManager);
    	eventDispatcher.register(EventType.ClientDisconnected, tableManager);
   
    	connectionHandler.startServer();
    	
    	System.out.println("Application started");
    }
}
