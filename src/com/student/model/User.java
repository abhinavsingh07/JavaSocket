package com.student.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@XmlRootElement 
public class User {
	
	private int userId;
	private String username;
	private String fName;
	private String lName;
	private String gender;
	private int phoneNo;
	private String password;
	private String dob;//
	private String country;
	private int userRoleid;//

	
	public User() {}
	public User(int userId, String username, String fName, String lName, String gender, int phoneNo, String password,
			String dob, String country) {
		super();
		this.userId = userId;
		this.username = username;
		this.fName = fName;
		this.lName = lName;
		this.gender = gender;
		this.phoneNo = phoneNo;
		this.password = password;
		this.dob = dob;
		this.country = country;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserRoleid() {
		return userRoleid;
	}

	public void setUserRoleid(int userRoleid) {
		this.userRoleid = userRoleid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
