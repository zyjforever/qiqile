package com.zyj.qiqile.tools.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityCommentConverter {
	public static final String TAG = "ActivityCommentConverter";

	public static Map<String, String> activityCommentBOTOMap(
			ActivityCommentBO activityCommentBO) {
		Map<String, String> result = null;
		if (activityCommentBO != null) {
			result = new HashMap<String, String>();
			if (activityCommentBO.getActivityId() != null) {
				result.put(ActivityCommentBO.ACTIVITY_ID,
						activityCommentBO.getActivityId());
			}
			if (activityCommentBO.getContext() != null) {
				result.put(ActivityCommentBO.CONTEXT,
						activityCommentBO.getContext());
			}
			if (activityCommentBO.getUserId() != null) {
				result.put(ActivityCommentBO.USER_ID,
						activityCommentBO.getUserId());
			}
			if (activityCommentBO.getTime() != null) {
				result.put(ActivityCommentBO.TIME,
						TimeHelper.getDateString(activityCommentBO.getTime()));
			}
			if (activityCommentBO.getAttribute() != null) {
				result.put(ActivityCommentBO.ATTRIBUTE,
						activityCommentBO.getAttribute());
			}
		}
		return result;
	}

	public static List<ActivityCommentBO> jsonTOActivityCommentBOList(
			JSONArray jsonArray) {
		List<ActivityCommentBO> activityCommentBOList = null;
		if (jsonArray != null) {
			activityCommentBOList = new ArrayList<ActivityCommentBO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					activityCommentBOList.add(jsonTOActivityCommentBO(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					Log.e(TAG, "", e);
					return null;
				}
			}
		}
		return activityCommentBOList;
	}

	/** json格式没有进行检查的，如果解析出错，直接返回null */
	public static ActivityCommentBO jsonTOActivityCommentBO(
			JSONObject jsonObject) {
		ActivityCommentBO activityCommentBO = null;
		if (jsonObject != null) {
			try {
				activityCommentBO = new ActivityCommentBO();
				if (!jsonObject.isNull(ActivityCommentBO.ID)) {
					activityCommentBO.setId(jsonObject
							.getString(ActivityCommentBO.ID));
				}
				if (!jsonObject.isNull(ActivityCommentBO.ACTIVITY_ID)) {
					activityCommentBO.setActivityId(jsonObject
							.getString(ActivityCommentBO.ACTIVITY_ID));
				}
				if (!jsonObject.isNull(ActivityCommentBO.CONTEXT)) {
					activityCommentBO.setContext(jsonObject
							.getString(ActivityCommentBO.CONTEXT));
				}
				if (!jsonObject.isNull(ActivityCommentBO.USER_ID)) {
					activityCommentBO.setUserId(jsonObject
							.getString(ActivityCommentBO.USER_ID));
				}
				if (!jsonObject.isNull(ActivityCommentBO.ATTRIBUTE)) {
					activityCommentBO.setAttribute(jsonObject
							.getString(ActivityCommentBO.ATTRIBUTE));
				}
				if (!jsonObject.isNull(ActivityCommentBO.TIME)) {
					activityCommentBO.setTime(TimeHelper
							.getDateFromString(jsonObject
									.getString(ActivityCommentBO.TIME)));
				}
				if (!jsonObject.isNull(ActivityCommentBO.USERNAME)) {
					activityCommentBO.setUserName(jsonObject
							.getString(ActivityCommentBO.USERNAME));
				}
				if (!jsonObject.isNull(ActivityCommentBO.USERPICNAME)) {
					activityCommentBO.setUserPicName(jsonObject
							.getString(ActivityCommentBO.USERPICNAME));
				}
				if (!jsonObject.isNull(ActivityCommentBO.USERPICURL)) {
					activityCommentBO.setUserPicUrl(jsonObject
							.getString(ActivityCommentBO.USERPICURL));
				}
			} catch (JSONException e) {
				Log.e(TAG, "", e);
				return activityCommentBO;
			}
		}
		return activityCommentBO;
	}
}
