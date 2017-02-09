package com.junglee.gameserver.app;

import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.player.PlayerManager;
import com.junglee.gameserver.session.SessionManager;
import com.junglee.gameserver.table.TableManager;


public class App {

	public static void main(String[] args) {
				
		AppContext.loadApplicationContext("applicationContext.xml");
	}
	
    public App() {
    	gameManager = new GameManager();
    	playerManager = new PlayerManager();
    	tableManager = new TableManager();
    	sessionManager = new SessionManager();
    }
	
    private static final App INSTANCE = new App();

    public static App getInstance() {
        return INSTANCE;
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
    
	private final GameManager gameManager;
    private final PlayerManager playerManager;
    private final TableManager tableManager;
    private SessionManager sessionManager;
}
