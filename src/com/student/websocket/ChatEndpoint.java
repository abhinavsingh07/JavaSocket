package com.student.websocket;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.student.dao.MessageDao;
import com.student.dao.UserDao;
import com.student.model.Message;

@ServerEndpoint(value = "/chat/{username}", encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
public class ChatEndpoint {
	private Session session;
	private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();

	/** calls when new client connected to this cat endpoint */
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username, @PathParam("userid") String userid)
			throws IOException {
		System.out.println("connected user" + username);
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
		this.session = session;
		chatEndpoints.add(this);
		users.put(session.getId(), username);
		Message message = new Message();
		message.setFrom(username);
		message.setDate(inputFormat.format(new Date()));
		message.setContent("online");
		message.setConnectedUsers(users);
		message.setIsPublic("Y");
		message.setConnectionstatus("active");
		try {
			broadcast(message);
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** calls when server receives message */
	@OnMessage
	public void onMessage(Session session, String receivedMsg) throws IOException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
		Gson gson = new Gson();
		Message msgObj = gson.fromJson(receivedMsg, Message.class);
		System.out.println("msg from client" + msgObj);
		msgObj.setFrom(users.get(session.getId()));
		msgObj.setTo(msgObj.getTo());
		msgObj.setDate(inputFormat.format(new Date()));
		msgObj.setContent(msgObj.getContent());
		msgObj.setIsPublic(msgObj.getIsPublic());
		try {
			broadcast(msgObj);
			sendMsgToDB(msgObj);
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// When socket is close by client
	@OnClose
	public void onClose(Session session) throws IOException {
		System.out.println("disconnect user" + session.getId());
		chatEndpoints.remove(this);
		Message message = new Message();
		message.setFrom(users.get(session.getId()));
		message.setContent("offline");
		// remove active user also
		users.remove(session.getId());
		message.setConnectedUsers(users);
		message.setConnectionstatus("inactive");
		try {
			broadcast(message);
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
	}

	private static void broadcast(Message message) throws IOException, EncodeException {

		chatEndpoints.forEach(endpoint -> {
			synchronized (endpoint) {
				try {
					// send message to all user for particular user we handled on client side.
					endpoint.session.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}

	static MessageDao mdao = new MessageDao();
	static Map<String, Integer> userMap = new HashMap<>();

	private static String sendMsgToDB(Message msg) {
		try {
			userMap = new UserDao().getAllUsersMap();
			msg.setFrom(userMap.get(msg.getFrom()).toString());
			if (msg.getTo() != null) {
				msg.setTo(userMap.get(msg.getTo()).toString());
			}
			mdao.addMessage(msg);
		} catch (Exception e) {
			System.out.println("exception " + e);
		}

		return null;
	}
}
