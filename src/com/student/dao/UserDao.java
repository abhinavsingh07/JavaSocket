package com.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mysql.cj.protocol.Resultset;
import com.student.model.Message;
import com.student.model.User;
import com.student.util.ConnectionJDBC;

public class UserDao {
	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		String sql = "select * from user order by userid asc";

		try {
			conn = ConnectionJDBC.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(userProcessSummaryRow(rs));
			}
		} catch (Exception e) {
			 e.printStackTrace();
			throw new RuntimeException();

		} finally {
			ConnectionJDBC.close(conn);
		}
		return list;

	}

	public User getUserById(int userid) {
		Connection conn = null;
		String sql = "select * from user where userid=?";

		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return new User(rs.getInt("userid"), rs.getString("username"), rs.getString("fName"),
						rs.getString("lName"), rs.getString("gender"), rs.getInt("phoneNo"), rs.getString("password"),
						rs.getString("dob"), rs.getString("country"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return new User();
	}

	public Map<String, Integer> getAllUsersMap() {
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		String sql = "select * from user";
		Map<String, Integer> map = new HashMap<>();
		try {
			conn = ConnectionJDBC.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString("username"), rs.getInt("userId"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return map;
	}

	public String addUser(User user) {
		Connection conn = null;
		String sql = "insert into user(username,fName,lName,gender,phoneNo,password,country,dob) "
				+ "values(?,?,?,?,?,?,?,?)";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getfName());
			stmt.setString(3, user.getlName());
			stmt.setString(4, user.getGender());
			stmt.setInt(5, user.getPhoneNo());
			stmt.setString(6, user.getPassword());
			stmt.setString(7, user.getCountry());
			stmt.setString(8, user.getDob());
			int i = stmt.executeUpdate();
			System.out.println(i + " user record inserted");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return "User added successfully";
	}

	public String updateUser(User user, int userid) {
		Connection conn = null;
		String sql = "update user set username=?,fname=?,lname=?,gender=?,phoneno=?,"
				+ "password=?,country=?,dob=? where userid=?";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getfName());
			stmt.setString(3, user.getlName());
			stmt.setString(4, user.getGender());
			stmt.setInt(5, user.getPhoneNo());
			stmt.setString(6, user.getPassword());
			stmt.setString(7, user.getCountry());
			stmt.setString(8, user.getDob());
			stmt.setInt(9, userid);
			int i = stmt.executeUpdate();
			System.out.println(i + " user record updated");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return "User updated successfully";
	}

	public String deleteUser(int userid) {
		Connection conn = null;
		String sql = "delete from user where userid=?";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			int i = stmt.executeUpdate();
			System.out.println(i + " user record deleted");
			return "User deleted successfully";
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
	}

	public User userLogin(User user) {
		Connection conn = null;
		User us = null;
		String sql = "select * from user where lower(username)=lower(?) and password=?";
		try {
			conn = ConnectionJDBC.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			ResultSet rs = stmt.executeQuery();
			// user present
			while (rs.next()) {
				us = new User();
				us.setUserId(rs.getInt(1));
				us.setUsername(rs.getString(2));
				return us;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionJDBC.close(conn);
		}
		return us;
	}

	protected User userProcessSummaryRow(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("userId"));
		user.setUsername(rs.getString("username"));
		user.setfName(rs.getString("fName"));
		user.setlName(rs.getString("lName"));
		user.setGender(rs.getString("gender"));
		user.setPhoneNo(rs.getInt("phoneNo"));
		user.setCountry(rs.getString("country"));
		return user;
	}

}
