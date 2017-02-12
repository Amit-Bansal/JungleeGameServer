package com.junglee.gameserver.application;

public class Application {

	public static void main(String[] args) {
		
		AppContext.loadApplicationContext("applicationContext.xml");
		App app = (App)AppContext.getBean("app");
		app.startApp();
	}
}
