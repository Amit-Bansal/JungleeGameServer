package com.junglee.gameserver.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import com.junglee.eventdispatcher.EventDispatcher;
import com.junglee.eventdispatcher.EventType;
import com.junglee.gameserver.cache.RedisClient;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.player.PlayerManager;
import com.junglee.gameserver.session.SessionManager;
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
    private ConnectionManager connectionManager;
	
	@Autowired
    private SessionManager sessionManager;
	
	@Autowired
	private RedisClient redisClient;
	
    public void startApp() {
    	
    	redisClient.write("events/city/rome", "Italy");
    	System.out.println(redisClient.read("events/city/rome"));
 
    	    	
    	//eventDispatcher.register(EventType.ClientConnected, gameManager);
    	//eventDispatcher.register(EventType.ClientConnected, playerManager);
    	//eventDispatcher.register(EventType.ClientConnected, tableManager);
    	eventDispatcher.register(EventType.ClientConnected, sessionManager);
    	eventDispatcher.register(EventType.MessageReceived, sessionManager);
    	
    	   
    	eventDispatcher.register(EventType.ClientDisconnected, gameManager);
    	eventDispatcher.register(EventType.ClientDisconnected, playerManager);
    	eventDispatcher.register(EventType.ClientDisconnected, tableManager);
    	eventDispatcher.register(EventType.ClientDisconnected, sessionManager);
   
    	connectionManager.start();
    	
    	System.out.println("Application started");
    }
}
