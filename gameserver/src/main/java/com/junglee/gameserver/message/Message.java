package com.junglee.gameserver.message;
public abstract class Message {
	
    public abstract void serialize();
    public static Message deserialize(String msgStr){
    	Message msg = null; //write message converter here
    	return msg;
    }
    

    public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}

	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	private MessageType type;
	private Integer sessionId;
}

