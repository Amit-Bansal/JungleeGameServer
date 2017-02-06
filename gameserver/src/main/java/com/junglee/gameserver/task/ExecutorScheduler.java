package com.junglee.gameserver.task;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorScheduler implements TaskScheduler {
	Executor executor;
	
	public ExecutorScheduler(){
		executor = Executors.newFixedThreadPool(10);
	}
	
	public void testMethod(){
		Runnable runnable = new Runnable() {
			public void run(){
				try{
					Thread.sleep(1000);
					System.out.println("Inside testMethod thread");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		executor.execute(runnable);
		System.out.println("Inside testMethod, afer thread creation");
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
