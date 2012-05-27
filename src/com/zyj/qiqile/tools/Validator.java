package com.zyj.qiqile.tools;

import com.zyj.qiqile.constant.Constants;

public class Validator {
	public static final String REG_EMAIL = "^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)";
	public static final String REG_MD5_32 = "[a-zA-Z0-9]{32}";
	public static final String REG_PASSWORD = "[a-zA-Z0-9]{5,20}";

	public static final boolean isEmail(String email) {
		boolean result = false;
		if (email != null) {
			result = email.matches(REG_EMAIL);
		}
		return result;
	}

	public static final boolean isNick(String nick) {
		boolean result = false;
		if (nick != null) {
			result = nick.length() >= 2 && nick.length() <= 10;
		}
		return result;
	}

	public static final boolean isPassword(String password) {
		boolean result = false;
		if (password != null) {
			result = password.matches(REG_PASSWORD);
		}
		return result;
	}

	public static final boolean isMD5(String password) {
		boolean result = false;
		if (password != null) {
			result = password.matches(REG_MD5_32);
		}
		return result;
	}

	public static final boolean isActivityComment(String comment) {
		return comment != null && comment.length() > 0
				&& comment.length() <= Constants.ACTIVITY_COMMENT_MAXSIZE;
	}
}
