package com.zyj.qiqile.domain.bo;

import java.sql.Date;

public class UserBO {

	public static final String ID = "id";
	public static final String NICK = "nick";
	public static final String ACCOUNT = "account";
	public static final String EMAIL = "email";
	public static final String BIRTHDAY = "birthday";
	public static final String SEX = "sex";
	public static final String SIGNATURE = "signature";
	public static final String PICURL = "picUrl";
	public static final String CITY = "city";
	public static final String PASSWORD = "password";
	public static final String PICNAME="picName";

	private String id;
	private String nick;
	private String account;
	private String email;
	private Date birthday;
	private Integer sex;// 1:男 0:女 -1其他
	private String signature;
	private String picUrl;
	private String city;
	private String password;
	private String picName;

	public String getPassword() {
		return password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getSexString() {
		String result = null;
		if (sex != null) {
			switch (sex) {
			case 1:
				result = "帅哥";
				break;
			case 0:
				result = "美女";
				break;
			case -1:
				result = "其他";
				break;
			}
		}
		return result;
	}

	public String getAgeString() {
		String result = "";
		if (birthday != null) {
			int year = birthday.getYear()+1900;
			if (year >= 2000)
				result = "00";
			else
				result = String.valueOf((year - 1900) / 10 * 10);
		}
		return result + "后";
	}
}
