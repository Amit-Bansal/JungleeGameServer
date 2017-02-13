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
	
	
	private MessageType type;
}

