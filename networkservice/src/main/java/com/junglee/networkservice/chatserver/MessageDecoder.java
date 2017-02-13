package com.junglee.networkservice.chatserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<ChatMessage> {

    @Override
    public ChatMessage decode(String msg) throws DecodeException {
        ChatMessage message = null;

        if(willDecode(msg)){
            try {
                ObjectMapper mapper = new ObjectMapper();
                message = mapper.readValue(msg, ChatMessage.class);
            } 
            catch(Exception e){
                e.printStackTrace();
            }
        }

        return message;
    }

    @Override
    public boolean willDecode(String msg) {
        try {
            Json.createReader((new StringReader(msg)));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
