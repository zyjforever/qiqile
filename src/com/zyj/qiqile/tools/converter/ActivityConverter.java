package com.zyj.qiqile.tools.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.TimeUtils;

import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityConverter {
	public static final String TAG = "ActivityConverter";

	public static Map<String, String> activityBOTOMap(ActivityBO activityBO) {
		Map<String, String> result = null;
		if (activityBO != null) {
			result = new HashMap<String, String>();
			if (activityBO.getName() != null) {
				result.put(ActivityBO.NAME, activityBO.getName());
			}
			if (activityBO.getContext() != null) {
				result.put(ActivityBO.CONTEXT, activityBO.getContext());
			}
			if (activityBO.getCity() != null) {
				result.put(ActivityBO.CITY, activityBO.getCity());
			}
			if (activityBO.getStartTime() != null) {
				result.put(ActivityBO.STARTTIME,
						TimeHelper.getDateString(activityBO.getStartTime()));
			}
			if (activityBO.getEndTime() != null) {
				result.put(ActivityBO.ENDTIME,
						TimeHelper.getDateString(activityBO.getEndTime()));
			}
			if (activityBO.getLocation() != null) {
				result.put(ActivityBO.LOCATION, activityBO.getLocation());
			}
			if (activityBO.getLatitude() != null) {
				result.put(ActivityBO.LATITUDE, activityBO.getLatitude()
						.toString());
			}
			if (activityBO.getLocation() != null) {
				result.put(ActivityBO.LONGITUDE, activityBO.getLongitude()
						.toString());
			}
			if (activityBO.getPeopleLimit() != null) {
				result.put(ActivityBO.PEOPLELIMIT, activityBO.getPeopleLimit()
						.toString());
			}
			if (activityBO.getPicUrl() != null) {
				result.put(ActivityBO.PICURL, activityBO.getPicUrl());
			}
			if (activityBO.getPicName() != null) {
				result.put(ActivityBO.PICNAME, activityBO.getPicName());
			}
			if (activityBO.getTime() != null) {
				result.put(ActivityBO.TIME,
						TimeHelper.getDateString(activityBO.getTime()));
			}
		}
		return result;
	}

	public static List<ActivityBO> jsonTOActivityBOList(JSONArray jsonArray) {
		List<ActivityBO> activityBOList = null;
		if (jsonArray != null) {
			activityBOList = new ArrayList<ActivityBO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					activityBOList.add(jsonTOActivityBO(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					Log.e(TAG, "", e);
					return null;
				}
			}
		}
		return activityBOList;
	}

	/** json格式没有进行检查的，如果解析出错，直接返回null */
	public static ActivityBO jsonTOActivityBO(JSONObject jsonObject) {
		ActivityBO activityBO = null;
		if (jsonObject != null) {
			try {
				activityBO = new ActivityBO();
				if (!jsonObject.isNull(ActivityBO.ID)) {
					activityBO.setId(jsonObject.getString(ActivityBO.ID));
				}
				if (!jsonObject.isNull(ActivityBO.NAME)) {
					activityBO.setName(jsonObject.getString(ActivityBO.NAME));
				}
				if (!jsonObject.isNull(ActivityBO.CONTEXT)) {
					activityBO.setContext(jsonObject
							.getString(ActivityBO.CONTEXT));
				}
				if (!jsonObject.isNull(ActivityBO.CITY)) {
					activityBO.setCity(jsonObject.getString(ActivityBO.CITY));
				}

				if (!jsonObject.isNull(ActivityBO.USERID)) {
					activityBO.setUserId(jsonObject
							.getString(ActivityBO.USERID));
				}
				if (!jsonObject.isNull(ActivityBO.STARTTIME)) {
					activityBO.setStartTime(TimeHelper
							.getDateFromString(jsonObject
									.getString(ActivityBO.STARTTIME)));
				}
				if (!jsonObject.isNull(ActivityBO.ENDTIME)) {
					activityBO.setEndTime(TimeHelper
							.getDateFromString(jsonObject
									.getString(ActivityBO.ENDTIME)));
				}
				if (!jsonObject.isNull(ActivityBO.LOCATION)) {
					activityBO.setLocation(jsonObject
							.getString(ActivityBO.LOCATION));
				}
				if (!jsonObject.isNull(ActivityBO.LATITUDE)) {
					activityBO.setLatitude(jsonObject
							.getDouble(ActivityBO.LATITUDE));
				}
				if (!jsonObject.isNull(ActivityBO.LONGITUDE)) {
					activityBO.setLongitude(jsonObject
							.getDouble(ActivityBO.LONGITUDE));
				}
				if (!jsonObject.isNull(ActivityBO.PICNAME)) {
					activityBO.setPicName(jsonObject
							.getString(ActivityBO.PICNAME));
				}
				if (!jsonObject.isNull(ActivityBO.PICURL)) {
					activityBO.setPicUrl(jsonObject
							.getString(ActivityBO.PICURL));
				}
				if (!jsonObject.isNull(ActivityBO.PEOPLELIMIT)) {
					activityBO.setPeopleLimit(jsonObject
							.getInt(ActivityBO.PEOPLELIMIT));
				}
				if (!jsonObject.isNull(ActivityBO.USERNAME)) {
					activityBO.setUserName(jsonObject
							.getString(ActivityBO.USERNAME));
				}
				if (!jsonObject.isNull(ActivityBO.USERPICNAME)) {
					activityBO.setUserPicName(jsonObject
							.getString(ActivityBO.USERPICNAME));
				}
				if (!jsonObject.isNull(ActivityBO.USERPICURL)) {
					activityBO.setUserPicUrl(jsonObject
							.getString(ActivityBO.USERPICURL));
				}
				if (!jsonObject.isNull(ActivityBO.JCOUNT)) {
					activityBO.setJcount(jsonObject.getInt(ActivityBO.JCOUNT));
				}
				if (!jsonObject.isNull(ActivityBO.CCOUNT)) {
					activityBO.setCcount(jsonObject.getInt(ActivityBO.CCOUNT));
				}
			} catch (JSONException e) {
				Log.e(TAG, "", e);
				return activityBO;
			}
		}
		return activityBO;
	}
}
