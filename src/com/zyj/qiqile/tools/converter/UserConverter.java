package com.zyj.qiqile.tools.converter;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.TimeHelper;

public class UserConverter {

	/** 只对用户登录时返回的数据格式有效 */
	public static UserBO jsonTOUserBO(JSONObject jsonObject)
			throws JSONException, UnsupportedEncodingException {
		UserBO userBO = null;
		if (jsonObject != null
				&& !jsonObject.isNull(ServerConstants.DATA)
				&& !jsonObject.getJSONObject(ServerConstants.DATA).isNull(
						ServerConstants.PROFILE)) {
			userBO = new UserBO();
			JSONObject userJSON = jsonObject
					.getJSONObject(ServerConstants.DATA).getJSONObject(
							ServerConstants.PROFILE);
			if (!userJSON.isNull(UserBO.ID)) {
				userBO.setId(userJSON.getString(UserBO.ID));
			}
			if (!userJSON.isNull(UserBO.NICK)) {
				userBO.setNick(userJSON.getString(UserBO.NICK));
			}
			if (!userJSON.isNull(UserBO.EMAIL)) {
				userBO.setEmail(userJSON.getString(UserBO.EMAIL));
			}
			if (!userJSON.isNull(UserBO.BIRTHDAY)) {
				userBO.setBirthday(new Date(TimeHelper.getSqlDateFromString(
						userJSON.getString(UserBO.BIRTHDAY)).getTime()));
			}
			if (!userJSON.isNull(UserBO.SEX)) {
				userBO.setSex(userJSON.getInt(UserBO.SEX));
			}
			if (!userJSON.isNull(UserBO.SIGNATURE)) {
				userBO.setSignature(userJSON.getString(UserBO.SIGNATURE));
			}
			if (!userJSON.isNull(UserBO.PICURL)) {
				userBO.setPicUrl(userJSON.getString(UserBO.PICURL));
			}
			if (!userJSON.isNull(UserBO.PICNAME)) {
				userBO.setPicName(userJSON.getString(UserBO.PICNAME));
			}
			if (!userJSON.isNull(UserBO.CITY)) {
				userBO.setCity(userJSON.getString(UserBO.CITY));
			}
			if (!userJSON.isNull(UserBO.PASSWORD)) {
				userBO.setPassword(userJSON.getString(UserBO.PASSWORD));
			}
		}
		return userBO;
	}

	public static Map<String, String> userBOTOMap(UserBO userBO) {
		Map<String, String> params = null;
		if (userBO != null) {
			params = new HashMap<String, String>();
			if (userBO.getId() != null) {
				params.put(UserBO.ID, userBO.getId());
			}
			if (userBO.getNick() != null) {
				params.put(UserBO.NICK, userBO.getNick());
			}
			if (userBO.getBirthday() != null) {
				params.put(UserBO.BIRTHDAY, userBO.getBirthday().toString());
			}
			if (userBO.getSex() != null) {
				params.put(UserBO.SEX, userBO.getSex().toString());
			}
			if (userBO.getSignature() != null) {
				params.put(UserBO.SIGNATURE, userBO.getSignature());
			}
			if (userBO.getPicUrl() != null) {
				params.put(UserBO.PICURL, userBO.getPicUrl());
			}
			if (userBO.getCity() != null) {
				params.put(UserBO.CITY, userBO.getCity());
			}
			if (userBO.getPicName() != null) {
				params.put(UserBO.PICNAME, userBO.getPicName());
			}
		}
		return params;
	}
}
