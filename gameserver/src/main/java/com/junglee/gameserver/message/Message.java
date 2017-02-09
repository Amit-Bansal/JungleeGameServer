package com.junglee.gameserver.message;
public class Message {
	
    public String serialize(){
    	return null;
    }
    
    public static Message deserialize(String msgStr){
    	return new Message();
    }
    

    public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	
	
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	private int sessionId;

	private MessageType type;
}

