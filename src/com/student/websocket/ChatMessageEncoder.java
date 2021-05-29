package com.student.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.student.model.Message;

public class ChatMessageEncoder implements Encoder.Text<Message> {
 
    private static Gson gson = new Gson();
 
    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }
 
    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }
 
    @Override
    public void destroy() {
        // Close resources
    }
}
