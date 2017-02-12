package com.junglee.gameserver.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

	Executor executor;
	private HashMap<EventType, List<EventListener>> eventListenerMap = new HashMap<EventType, List<EventListener>>();

    private final BlockingQueue<Event> eventsQueue = new LinkedBlockingQueue<Event>();

    private final Thread dispatcherThread;

    public EventDispatcher() {
    	
    	executor = Executors.newFixedThreadPool(10);
    	
    	Runnable runnable = new Runnable(){
    		public void run(){
    			dispatch();
    		}
    	};
        this.dispatcherThread = new Thread(runnable);
        this.dispatcherThread.setDaemon(true);
        this.dispatcherThread.start();
    }

    public void register(EventType type, EventListener listener) {
    	List<EventListener> listners = eventListenerMap.get(type);
    	if(listners== null){
    		listners = new ArrayList<EventListener>();
    	}
		listners.add(listener);
		eventListenerMap.put(type, listners);
    }

    public void unregister(EventType type, EventListener listener) {
    	eventListenerMap.get(type).remove(listener);
    }

    public void submit(Event event) {
        eventsQueue.add(event);
    }

    private void dispatchEvent(final EventListener l, final Event event)
    {
		Runnable runnable = new Runnable() {
			public void run(){
				l.executeEvent(event);
			}
		};
		executor.execute(runnable);	
    }
    
    private void dispatch() {
        while (true) {
            try {
                final Event event = eventsQueue.take();
                List<EventListener> listeners = eventListenerMap.get(event.getType());
                for (EventListener listner: listeners)
                	dispatchEvent(listner, event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}