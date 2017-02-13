package com.junglee.eventdispatcher;


public class Event {

    private final Object source;
    private final EventType type;
    private String message;

    public Event(Object source, EventType type) {
        this.source = source;
        this.type = type;
    }

    public Object getSource() {
        return source;
    }

    public EventType getType() {
        return type;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}