package com.junglee.networkservice.chatserver;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value="/gameserver/{table-id}",
                decoders=MessageDecoder.class,
                encoders=MessageEncoder.class)
public class ChatClinets {

    private static HashMap<String, List<ChatClinets>> clients = new HashMap<String, List<ChatClinets>>();
    private static HashMap<String, List<ChatMessage>> messages = new HashMap<String, List<ChatMessage>>();
    Session session;

    @OnError
    public void onError(Session session, Throwable t, @PathParam("table-id") String tableid) {

    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("table-id") String tableid) {
        this.session = session;
        List<ChatClinets> chatClients = clients.get(tableid);
        if (chatClients == null) {
        	chatClients = new CopyOnWriteArrayList<ChatClinets>();
            clients.put(tableid, chatClients);
        }
        chatClients.add(this);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("table-id") String tableid) throws Exception {
        List<ChatClinets> chatClients = clients.get(tableid);
        if (chatClients == null) {
            throw new Exception("Expected a valid table.");
        }
        chatClients.remove(this);
    }

    @OnMessage
    public void onMessage(ChatMessage message, @PathParam("table-id") String tableid) {
        List<ChatMessage> messagesList = messages.get(tableid);
        if (messagesList == null) {
            messagesList = new CopyOnWriteArrayList<ChatMessage>();
            messages.put(tableid, messagesList);
        }
        messagesList.add(message);
        for (ChatClinets client : clients.get(tableid)) {
            try {
            	client.session.getBasicRemote().sendObject(message);
            } 
            catch (Exception e) {
            }
    }
    
    }
}
