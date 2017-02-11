package com.junglee.gameserver.application;

import com.junglee.dbservice.service.DatabaseService;
import com.junglee.gameserver.event.EventDispatcher;
import com.junglee.gameserver.event.EventType;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.player.PlayerManager;
import com.junglee.gameserver.session.ConnectionHandler;
import com.junglee.gameserver.session.SessionManager;
import com.junglee.gameserver.table.TableManager;
import com.junglee.gameserver.task.TaskScheduler;


public class App {
	
    private static App instance = null;
	private static Object mutex = new Object();
	
	public static App getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new App();
				}
			}
		}
		return instance;
	}


    private App() {
		AppContext.loadApplicationContext("applicationContext.xml");	
    }
    
    public void startApp() {
    	gameManager = new GameManager();
    	playerManager = new PlayerManager();
    	tableManager = new TableManager();
    	sessionManager = new SessionManager();
    	eventDispather = new EventDispatcher();
    	
    	eventDispather.register(EventType.ClientDisconnected, gameManager);
    	eventDispather.register(EventType.ClientDisconnected, playerManager);
    	eventDispather.register(EventType.ClientDisconnected, tableManager);
    	
    	taskScheduler = (TaskScheduler)AppContext.getBean("schedulerService");
    	connectionHandler = new ConnectionHandler(AppContext.getBean("connectionManager"));
    	dbService = (DatabaseService)AppContext.getBean("databaseService");
    	
    	connectionHandler.startServer();
    	
    	System.out.println("Application started");
    }
	

    public GameManager getGameManager() {
        return this.gameManager;
    }
    
    public PlayerManager getPlayerManager() {
		return playerManager;
	}
    
    public TableManager getTableManager() {
		return tableManager;
	}
    
    public SessionManager getSessionManager() {
		return sessionManager;
	}
    
	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public DatabaseService getDbService() {
		return dbService;
	}

	public EventDispatcher getEventDispather() {
		return eventDispather;
	}

	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}


	private  GameManager gameManager;
    private  PlayerManager playerManager;
    private  TableManager tableManager;
    private  SessionManager sessionManager;
    private  EventDispatcher eventDispather;
    private  ConnectionHandler connectionHandler;
    private  DatabaseService dbService;
    private  TaskScheduler taskScheduler;
}
