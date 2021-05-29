package com.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.student.model.Message;
import com.student.model.User;
import com.student.util.ConnectionJDBC;

public class MessageDao {
	public String addMessage(Message message) {
		Connection conn = null;
		String sql = "insert into `message`(`from`,`to`,`content`,`date`,`ispublic`) values(?,?,?,?,?);";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(message.getFrom()));
			stmt.setInt(2, Integer.parseInt(message.getTo() == null ? "0" : message.getTo()));
			stmt.setString(3, message.getContent());
			stmt.setObject(4, dateFormat.format(date));
			stmt.setString(5, message.getIsPublic());
			int i = stmt.executeUpdate();
			System.out.println(i + " records inserted to message table");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return "Message added successfully";
	}

	public List<Message> getAllmessages(int userid) {
		Connection conn = null;
		User us = null;
		List<Message> msgList = new ArrayList<Message>();
		String sql = "select m.messageId,(select username from user where userid=m.from) as `from`,\r\n"
				+ "(select username from user where userid=m.to) as `to`,m.content,m.isPublic,"
				+ "date_format(m.date,\"%d-%c-%Y %h:%i:%s %p\") as date \r\n"
				+ "from message m,user u where m.from=u.userid  and  (m.from in (?) or m.to in (0,?)) order by date desc;";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			stmt.setInt(2, userid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				msgList.add(messageProcessSummaryRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return msgList;
	}

	public String deleteMessage(int msgid) {
		Connection conn = null;
		String sql = "delete from message where messageid=?";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, msgid);
			int i = stmt.executeUpdate();
			System.out.println(i + " message record deleted");
			return "Message deleted successfully";
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
	}

	protected Message messageProcessSummaryRow(ResultSet rs) {
		Message msg = new Message();
		try {

			msg.setMessageId(rs.getInt("messageid"));
			msg.setFrom(rs.getString("from"));
			msg.setTo(rs.getString("to") == null ? "" : rs.getString("to"));
			msg.setContent(rs.getString("content"));
			msg.setDate(rs.getString("date"));
			msg.setIsPublic(rs.getString("ispublic"));
			// System.out.println(msg);
		} catch (Exception e) {
			System.out.println("messageProcessSummaryRow" + e);
		}

		return msg;
	}
}
