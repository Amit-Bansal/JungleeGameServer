package com.junglee.gameserver.message;
public interface Message {
    public void serialize();
    public Message deserialize();
}

