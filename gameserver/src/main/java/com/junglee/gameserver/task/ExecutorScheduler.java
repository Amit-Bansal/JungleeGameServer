package com.junglee.gameserver.task;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorScheduler implements TaskScheduler {
	Executor executor;
	
	private static ExecutorScheduler instance = null;
	private static Object mutex = new Object();

	public static ExecutorScheduler getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new ExecutorScheduler();
				}
			}
		}
		return instance;
	}
	
	
	public ExecutorScheduler(){
		executor = Executors.newFixedThreadPool(10);
	}
	

	public void processTask(final Task gameTask){
		Runnable runnable = new Runnable() {
			public void run(){
				gameTask.process();
			}
		};
		executor.execute(runnable);
	}
}
