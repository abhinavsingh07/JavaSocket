package com.student.model;

import java.util.Date;
import java.util.Map;

public class Message {

	private int messageId;
	private String from;
	private String to;
	private String content;
	private String date;
	private String isPublic;
	private Map<String,String> connectedUsers;
	private String connectionstatus;
	
	public Message() {}

	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public Map<String, String> getConnectedUsers() {
		return connectedUsers;
	}
	public void setConnectedUsers(Map<String, String> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}
	
	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", from=" + from + ", to=" + to + ", content=" + content + ", date="
				+ date + ", isPublic=" + isPublic + ", connectedUsers=" + connectedUsers + "]";
	}

	public String getConnectionstatus() {
		return connectionstatus;
	}

	public void setConnectionstatus(String connectionstatus) {
		this.connectionstatus = connectionstatus;
	}

	
}
